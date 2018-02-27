package com.example.shariqkhan.wfdsa;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Codiansoft on 1/3/2018.
 */

public class FirebaseMessagingServe extends FirebaseMessagingService {

    String messageToShow;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            Log.d("messageFromServer", remoteMessage.getData().toString());
        }
        if (remoteMessage.getNotification().getBody() != null) {
            Log.e("MessageBody", remoteMessage.getNotification().getBody());
            messageToShow = remoteMessage.getNotification().getBody();
        }


        showNotification(remoteMessage.getData().get("message"));

    }

    private void showNotification(String message) {

    //    Log.e("MessagePrint", message);
       SharedPreferences prefs = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
       boolean isLoggedIn=prefs.getBoolean("isLoggedIn",false);
       if(isLoggedIn)
       {
           Intent i = new Intent(this, MainActivity.class);
           i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           PendingIntent pendingntent =
                   PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

           NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                   .setAutoCancel(true)
                   .setContentTitle("WFDSA")
                   .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                   .setContentText(messageToShow)
                   .setSmallIcon(R.drawable.frontlogo)
                   .setContentIntent(pendingntent);
           Log.e("UriDeFAULT", String.valueOf(Settings.System.DEFAULT_NOTIFICATION_URI));

           NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
           manager.notify(0, builder.build());

       }

    }


}
