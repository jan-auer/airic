package org.spaceapps.aircheck.app.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

public class AppInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        RegistrationIntentService.startService(this);
    }

}
