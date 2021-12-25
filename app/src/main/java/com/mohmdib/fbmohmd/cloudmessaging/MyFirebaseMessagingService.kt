package com.mohmdib.fbmohmd.cloudmessaging

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mohmdib.fbmohmd.TodoListActivity
import com.mohmdib.fbmohmd.R

const val CHANNEL_ID = "notification_channel"
const val CHANNEL_NAME = "com.mohmdib.fbmohmd"

open class MyFirebaseMessagingService : FirebaseMessagingService() {


    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title: String, desc: String): RemoteViews{
        var remoteView = RemoteViews("com.mohmdib.fbmohmd", R.layout.notification_vie)
        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.description, desc)
        return remoteView

    }


    override fun onMessageReceived(remote: RemoteMessage) {
        super.onMessageReceived(remote)
        remote.notification?.let {
            generateNotification(title = it.title!! , desc = it.body!!)
        }
    }

    fun generateNotification(title: String, desc: String){
        var intent = Intent(this, TodoListActivity::class.java)
        var pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000 , 1000, 1000))
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title = title, desc = desc))
        var notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            var notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())


    }

}