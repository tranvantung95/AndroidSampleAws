package com.example.amazonaws

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.amazoneaws.timeschedule.APP_NOTIFICATION_CHANNEL_DES
import com.amazoneaws.timeschedule.APP_NOTIFICATION_CHANNEL_ID
import com.amazoneaws.timeschedule.APP_NOTIFICATION_CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class AmazonawsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                APP_NOTIFICATION_CHANNEL_ID,
                APP_NOTIFICATION_CHANNEL_NAME,
                importance
            ).apply {
                description = APP_NOTIFICATION_CHANNEL_DES
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}