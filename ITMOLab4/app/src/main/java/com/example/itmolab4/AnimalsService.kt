package com.example.itmolab4

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class AnimalsService : Service() {

    private lateinit var animalsNotificationManager: NotificationManager
    private lateinit var notificationLoadingBuilder: NotificationCompat.Builder

    private val notificationLoadingId = 1
    private val notificationFinishedId = 2


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        animalsNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationLoadingChannel()
        notificationLoadingBuilder = createNotificationLoadingBuilder()

        startForeground(notificationLoadingId, notificationLoadingBuilder.build())
        return START_STICKY
    }

    override fun onDestroy() {
        stopForeground(true)
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        return AnimalBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        stopForeground(true)
        return super.onUnbind(intent)
    }

    private fun createNotificationLoadingBuilder(): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, "Animals")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Downloading animals")
            .setOngoing(true)
            .setContentText("0%")
            .setProgress(100, 0, false)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    fun createNotificationFinished(time: Float, itemCount: Int) {
        val builder = NotificationCompat.Builder(this, "Animals")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Downloading animals finished")
            .setContentText("$itemCount items were downloaded for $time seconds")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        stopForeground(true)

        animalsNotificationManager.notify(notificationFinishedId, builder.build())

        stopSelf()
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationLoadingChannel() {
        val channel = NotificationChannel(
            "Animals",
            "Animals",
            NotificationManager.IMPORTANCE_LOW
        )
        channel.description = "Animals Notifications"
        animalsNotificationManager.createNotificationChannel(channel)
    }

    fun updateNotificationLoading(
        progress: Int,
    ) {
        animalsNotificationManager.notify(
            notificationLoadingId,
            notificationLoadingBuilder
                .setContentText("$progress%")
                .setProgress(100, progress, false)
                .build()
        )
    }

    inner class AnimalBinder: Binder() {
        fun getAnimalsService() = this@AnimalsService
    }
}