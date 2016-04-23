package org.spaceapps.aircheck.client;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.spaceapps.aircheck.client.sensor.BluetoothConnector;
import org.spaceapps.aircheck.client.sensor.BluetoothProtocol;
import org.spaceapps.aircheck.model.LocationDto;
import org.spaceapps.aircheck.model.SampleDataDto;
import org.spaceapps.aircheck.model.SampleDto;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

public class MainActivity extends AppCompatActivity {

    public static final String DATA_URL = "http://169.50.19.146:8080/api/samples";
    public static final String ENDPOINT = "http://169.50.19.146:8080/api/";

    private ImageView btn_start;
    private TextView tv_coordinates;
    private TextView tv_accuracy;
    private TextView tv_altitude;
    private TextView tv_timestamp;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private Thread bluetoothThread;
    private InputStream bluetoothStream = null;
    private BluetoothConnector.BluetoothSocketWrapper bluetoothSocket = null;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = (ImageView) findViewById(R.id.btn_start);
        tv_coordinates = (TextView) findViewById(R.id.tv_coordinates);
        tv_accuracy = (TextView) findViewById(R.id.tv_accuracy);
        tv_altitude = (TextView) findViewById(R.id.tv_altitude);
        tv_timestamp = (TextView) findViewById(R.id.tv_timestamp);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                displayLocationAndTime();
                //remove comment tags to show Toast notification whenever LocationManager gets new valid coordinates
                //Toast.makeText(getBaseContext(), "update", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayLocationAndTime();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        if (!ba.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
        }

        bluetoothThread = new Thread(new Runnable() {
            @Override
            public void run() {
                BluetoothProtocol p = new BluetoothProtocol();
                try {
                    while (!Thread.interrupted()) {
                        connectBT();
                        if (!checkBTConnectivity()) {
                            Thread.sleep(1000);
                        } else {
                            int value = 0;
                            try {
                                value = bluetoothStream.read();
                                if (value < 0) {
                                    disconnectBT();
                                } else {
                                    final String data = p.consume(value);
                                    if (data != null) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                SampleDto lastSample = createSampleFromReceivedData(data);
                                                sendDataToServer(lastSample);
                                            }
                                        });
                                        //showToast(data);
                                    }
                                }
                            } catch (IOException ignored) {
                                disconnectBT();
                            }
                        }
                    }
                } catch (InterruptedException ignored) {
                }
            }
        });
        bluetoothThread.start();
    }

    //DATA in this form: [temp, humidity, co, co2]
    private SampleDto createSampleFromReceivedData(String data) {
        SampleDataDto sampleData = new SampleDataDto();
        LocationDto location = new LocationDto();
        SampleDto sample = new SampleDto();

        data = data.substring(1, data.length() - 1); //remove brackets
        String[] values = data.split(",");
        double temperature = Double.parseDouble(values[0]);
        double humidity = Double.parseDouble(values[1]);
        double co = Double.parseDouble(values[2]);
        double co2 = Double.parseDouble(values[3]);

        sampleData.setTemperature(temperature);
        sampleData.setHumidity(humidity);
        sampleData.setCo(co);
        sampleData.setCo2(co2);

        Location lastKnownLocation = getLastKnownLocation();
        location.setLatitude(lastKnownLocation.getLatitude());
        location.setLongitude(lastKnownLocation.getLongitude());
        location.setAccuracy(lastKnownLocation.getAccuracy());
        location.setAltitude(lastKnownLocation.getAltitude());

        Date timestamp = getCurrentTimestampFromLocationData(lastKnownLocation);
        sample.setDeviceId("001");
        sample.setDate(timestamp);
        sample.setLocation(location);
        sample.setData(sampleData);

        return sample;
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            bluetoothThread.interrupt();
            bluetoothThread.join();
        } catch (InterruptedException ignored) {
        }
        disconnectBT();
    }

    private void showToast(final String message) {
        final Context context = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendDataToServer(SampleDto sample) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd' 'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        ServerAPI api = retrofit.create(ServerAPI.class);

        Call<SampleDto> sampleCall = api.postSample(sample);
        sampleCall.enqueue(new Callback<SampleDto>() {
            @Override
            public void onResponse(Call<SampleDto> call, Response<SampleDto> response) {
                Toast.makeText(MainActivity.this, "Transmission succeeded", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<SampleDto> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Transmission failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void connectBT() {
        if (bluetoothStream != null) return;
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        if (!ba.isEnabled()) return;
        Set<BluetoothDevice> pairedDevices;
        pairedDevices = ba.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            System.err.println(device.getName());
            if (device.getName().equals("sensor1") || device.getName().equals("sensor2")) {
                try {
                    BluetoothConnector connector = new BluetoothConnector(device, true, ba, null);
                    bluetoothSocket = connector.connect();
                    ba.cancelDiscovery();
                    bluetoothStream = bluetoothSocket.getInputStream();
                } catch (IOException ignored) {
                    bluetoothSocket = null;
                    bluetoothStream = null;
                }
                break;
            }
        }
    }

    private void disconnectBT() {
        if (bluetoothStream != null) {
            try {
                bluetoothStream.close();
            } catch (IOException ignored) {
            }
            bluetoothStream = null;
        }
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket.close();
            } catch (IOException ignored) {
            }
            bluetoothSocket = null;
        }
    }

    private boolean checkBTConnectivity() {
        return bluetoothStream != null;
    }

    private void displayLocationAndTime() {
        Location lastKnownLocation = getLastKnownLocation();
        if (lastKnownLocation == null)
            return;
        updateLocationDisplay(lastKnownLocation);
        updateTimestampDisplay(lastKnownLocation);
    }

    private boolean updateLocationDisplay(Location location) {
        if (location == null)
            return false;
        tv_coordinates.setText("Lon: " + location.getLongitude() + "  Lat: " + location.getLatitude());
        tv_accuracy.setText("Acc: " + location.getAccuracy());
        tv_altitude.setText("Alt: " + location.getAltitude());
        return true;
    }

    private boolean updateTimestampDisplay(Location location) {
        if (location == null)
            return false;

        Date date = getCurrentTimestampFromLocationData(location);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(date);

        tv_timestamp.setText(timestamp);
        return true;
    }

    private Location getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }

        //try getting valid GPS Data first
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //if no GPS data available -> use network data (less accurate)
        if (lastKnownLocation == null) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        return lastKnownLocation;
    }

    private Date getCurrentTimestampFromLocationData(Location location) {

        long tempTime = location.getTime();
        Date date = new Date(tempTime);
        return date;
    }
}