package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

private const val EXTRA_OBJECT = "object"

class NotificationHelper(
    private val context: Context
) {
    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private fun createNotificationChannel(channelID: String, channelName: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = channelName
            val descriptionText = channelName
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(
        num: Int,
        channelID: String,
        textTitle: String,
        textContent: String
    ): NotificationCompat.Builder {

        val notifyIntent = Intent(context, MainActivity::class.java)
        notifyIntent.putExtra(EXTRA_OBJECT, num)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
    }

    fun cancelNotification(num: Int) {
        notificationManager.cancel(num)
    }

    fun createNotification(num :Int): NotificationCompat.Builder{
        val channelID = "TestApp"
        val channelName = "Test Channel"
        createNotificationChannel(channelID, channelName)
        return buildNotification(num, channelID, channelName, "Notification $num")
    }

}