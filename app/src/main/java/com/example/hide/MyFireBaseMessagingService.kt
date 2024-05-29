package com.example.hide
import android.os.Looper
import android.os.Handler
import android.widget.Toast
import android.annotation.SuppressLint
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Looper.prepare()
        Handler().post {
            Toast.makeText(baseContext,remoteMessage.notification?.title,Toast.LENGTH_LONG).show()
        }
        Looper.loop()

    }

}