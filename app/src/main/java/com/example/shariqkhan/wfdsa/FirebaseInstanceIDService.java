package com.example.shariqkhan.wfdsa;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by Codiansoft on 1/3/2018.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("myFirebaseId ", "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        SharedPreferences prefs = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        String token = prefs.getString("myToken", "");


        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String refreshedToken) {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("Token", refreshedToken)
                .build();


Request request = new Request.Builder()
        .url("hhhhhh")
        .post(body)
        .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            Log.e("Exception", e.getMessage());
            e.printStackTrace();
        }
    }

}
