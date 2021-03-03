package com.example.surveillance.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.surveillance.R
import com.example.surveillance.data.remote.response.PlateAPI
import com.example.surveillance.data.remote.response.PlateAPIItem
import com.example.surveillance.data.repository.LicensePlateRepository
import com.example.surveillance.flow.licensePlate.TestFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class MyService : Service() {

    private lateinit var fetchedPlates: PlateAPI
    val repository: LicensePlateRepository by inject()
    var detectedPlates = mutableListOf<PlateAPIItem>()
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStart(intent: Intent?, startId: Int) {
        val plate = intent?.extras?.get("KEY").toString()
        val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
        val pinger = Runnable {
            GlobalScope.launch {
                plate.let {
                    fetchedPlates = repository.fetchPlates(it.filterNot { plate -> plate == '-' })
                }
                fetchedPlates.forEach {
                    if (it.detected) {
                        delay(5000)
                        if (!detectedPlates.contains(it)) {
                            setupNotification(it)
                            detectedPlates.add(it)
                        }
                    }
                }
            }
        }
        val beeperHandle: ScheduledFuture<*> =
            scheduler.scheduleAtFixedRate(pinger, 10, 10, TimeUnit.SECONDS)
        scheduler.schedule(Runnable { beeperHandle.cancel(true) }, 60 * 60, TimeUnit.SECONDS)
    }

    private fun setupNotification(plate: PlateAPIItem) {
        var notificationManager: NotificationManager? = null

        val notificationBuilder = NotificationCompat.Builder(this, "17")
        val intent = Intent(this, TestFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

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

        notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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