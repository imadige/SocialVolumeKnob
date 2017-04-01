package org.ligi.socialvolumeknob;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private final String TAG = "SocialVolumeKnob";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            final Map<String, String> data = remoteMessage.getData();
            Log.d(TAG, "Message data payload: " + data);
            final String volume = data.get("volume");
            if (volume != null) {
                EventBus.getDefault().post(new VolumeEvent(Double.parseDouble(volume)));
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

    }
}
