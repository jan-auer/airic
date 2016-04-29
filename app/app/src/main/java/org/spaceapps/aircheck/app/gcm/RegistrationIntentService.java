package org.spaceapps.aircheck.app.gcm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.spaceapps.aircheck.app.R;

import java.io.IOException;

public class RegistrationIntentService extends IntentService {

    public static void startService(Context context) {
        Intent intent = new Intent(context, RegistrationIntentService.class);
        context.startService(intent);
    }

    public RegistrationIntentService() {
        super("RegistrationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            GcmPubSub pubSub = GcmPubSub.getInstance(this);

            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            pubSub.subscribe(token, getString(R.string.gcm_topic), null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
