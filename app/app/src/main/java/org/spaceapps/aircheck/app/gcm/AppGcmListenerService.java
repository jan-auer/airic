
package org.spaceapps.aircheck.app.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import org.spaceapps.aircheck.app.R;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.support.v4.app.NotificationCompat.CATEGORY_RECOMMENDATION;
import static android.support.v4.app.NotificationCompat.CATEGORY_TRANSPORT;
import static android.support.v4.app.NotificationCompat.DEFAULT_ALL;
import static android.support.v4.app.NotificationCompat.VISIBILITY_PRIVATE;
import static android.support.v4.app.NotificationCompat.VISIBILITY_PUBLIC;

public class AppGcmListenerService extends GcmListenerService {
    public AppGcmListenerService() {
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String types = data.getString("types");
        Context context = getBaseContext();

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_favorite_black_24dp)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setAutoCancel(true)
                .setVisibility(VISIBILITY_PUBLIC)
                .setCategory(CATEGORY_RECOMMENDATION)
                .build();

        // By default, notifications will be silent. This will activate notification lights,
        // default vibration and default sounds.
        notification.defaults |= DEFAULT_ALL;

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, notification);
    }
}
