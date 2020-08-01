package com.gmail.hyd.laexcellence;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


import com.gmail.hyd.laexcellence.Helpers.MyNotificationManager;
import com.gmail.hyd.laexcellence.Pages.NotificationActivity;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by delaroy on 10/8/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public final static String TOKEN_BROADCAST = "Akshara_broadcast";



    @Override
    public void onNewToken(String token) {
        Log.e(TAG, "Refreshed token-----: " + token);
        //Toast.makeText(getApplicationContext(),"Refreshed token: " + token,Toast.LENGTH_LONG).show();

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        getApplicationContext().sendBroadcast(new Intent(TOKEN_BROADCAST));
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token){
        SharedPrefManager.get_mInstance(getApplicationContext()).saveDeviceToken(token);
        Log.e("sdchjsdbc",token+"s dkvj jv");
        Log.e("sdchjsdbc",SharedPrefManager.get_mInstance(getApplicationContext()).getDeviceToken()+"s dkvj jv");
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    //this method will display the notification
    //We are passing the JSONObject that is received from
    //firebase cloud messaging
    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");

            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
            Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);

            //if there is no image
            if (imageUrl.equals("null")) {
                //displaying small notification
                mNotificationManager.showSmallNotification(title, message, intent);
            } else {
                //if there is an image
                //displaying a big notification
                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

}
