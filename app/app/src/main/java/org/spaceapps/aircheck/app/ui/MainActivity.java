package org.spaceapps.aircheck.app.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.spaceapps.aircheck.app.R;
import org.spaceapps.aircheck.app.gcm.RegistrationIntentService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RegistrationIntentService.startService(this);
    }
}
