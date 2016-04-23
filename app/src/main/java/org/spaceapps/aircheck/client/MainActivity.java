package org.spaceapps.aircheck.client;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ada.nasaproxy.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btn_connect;
    private ImageView btn_start;
    private TextView tv_status;
    private TextView tv_coordinates;
    private TextView tv_accuracy;
    private TextView tv_altitude;
    private TextView tv_timestamp;

    private LocationManager locationManager;
    private LocationListener locationListener;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_connect = (Button) findViewById(R.id.btn_connect);
        btn_start = (ImageView) findViewById(R.id.btn_start);
        tv_status = (TextView) findViewById(R.id.tv_status);
        tv_coordinates = (TextView) findViewById(R.id.tv_coordinates);
        tv_accuracy = (TextView) findViewById(R.id.tv_accuracy);
        tv_altitude = (TextView) findViewById(R.id.tv_altitude);
        tv_timestamp = (TextView) findViewById(R.id.tv_timestamp);

        boolean connected = checkBTConnectivity();
        if (connected)
            tv_status.setText("CONNECTED");
        else
            tv_status.setText("NOT CONNECTED");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                tv_coordinates.setText("Lon: " + location.getLongitude() + "Lat: " + location.getLatitude());
                tv_accuracy.setText("Acc: " + location.getAccuracy());
                tv_altitude.setText("Alt: " + location.getAltitude());

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

                configureButton();
            }
        });

    }

    private boolean checkBTConnectivity() {
        return false;
    }

    private void configureButton() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        //try getting valid GPS Data first
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //if no GPS data available -> use network data (less accurate)
        if (!updateLocationDisplay(lastKnownLocation) && !updateTimeStampDisplay(lastKnownLocation))
        {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            updateLocationDisplay(lastKnownLocation);
            updateTimeStampDisplay(lastKnownLocation);
        }

    }

    private boolean updateLocationDisplay(Location location) {
        if (location == null)
            return false;
        tv_coordinates.setText("Lon: " + location.getLongitude() + "  Lat: " + location.getLatitude());
        tv_accuracy.setText("Acc: " + location.getAccuracy());
        tv_altitude.setText("Alt: " + location.getAltitude());
        return true;
    }

    private boolean updateTimeStampDisplay(Location location) {
        if (location == null)
            return false;

        long tempTime = location.getTime();
        Date d = new Date(tempTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(d);

        tv_timestamp.setText(timestamp);
        return true;
    }
}