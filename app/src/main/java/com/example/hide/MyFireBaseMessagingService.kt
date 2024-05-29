package com.example.hide

import android.annotation.SuppressLint
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        //Log.d(TAG, "From: " + remoteMessage.getFrom());

        // if (remoteMessage.getData().size() > 0) {
        // Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        // }

        // if (remoteMessage.getNotification() != null) {
        //Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        // }
        // }

        super.onMessageReceived(message)
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}