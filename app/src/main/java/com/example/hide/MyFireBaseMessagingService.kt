package com.example.hide
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Check if the message contains a notification payload.
        if (remoteMessage.notification != null) {
            // Get the notification data.
            val title = remoteMessage.notification!!.title
            val body = remoteMessage.notification!!.body

            // Create a notification channel.
            val channelId = "friend_request_channel"
            val channelName = "Friend Request"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)

            // Build the notification.
            val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.icononotificacion) // Replace with your own notification icon
                .setContentTitle(title)
                .setContentText(body)

            // Send the notification.
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            notificationManager.notify(0, notificationBuilder.build())
        }
    }
}