package com.example.surveillance.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.surveillance.R
import com.example.surveillance.data.remote.response.PlateAPIItem
import com.example.surveillance.flow.licensePlate.LicensePlateFragment
import java.util.*

object NotificationUtils {
    fun setupNotification(
        plate: PlateAPIItem,
        context: Context
    ) {
        val notificationBuilder = NotificationCompat.Builder(context, "17")
        val intent = Intent(context, LicensePlateFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val bigStyleText = NotificationCompat.BigTextStyle().bigText(
            "Plate ${plate.plate} you requested was seen" +
                    "${Date().time}"
        )
            .setBigContentTitle("Plate ${plate.plate} was detected!")
            .setSummaryText("Plate has been found")

        notificationBuilder.setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle("Plate ${plate.plate} was detected!")
            .setContentText("Plate found!")
            .setStyle(bigStyleText)
            .priority = Notification.PRIORITY_MAX

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelID = "17"
            val notificationChannel =
                NotificationChannel(channelID, "ChannelNotify", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)

            notificationBuilder.setChannelId(channelID)
        }

        notificationManager.notify(0, notificationBuilder.build())

    }

}