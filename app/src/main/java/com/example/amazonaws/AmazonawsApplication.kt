package com.example.amazonaws

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.amazonaws.data.dataModule
import com.amazonaws.domain.useCaseModule
import com.amazonaws.matches.matchesList.matchesViewModelModule
import com.amazonaws.network.apiModule
import com.amazonaws.network.retrofitModule
import com.amazonawsteams.teamList.teamsViewModelModule
import com.amazoneaws.timeschedule.APP_NOTIFICATION_CHANNEL_DES
import com.amazoneaws.timeschedule.APP_NOTIFICATION_CHANNEL_ID
import com.amazoneaws.timeschedule.APP_NOTIFICATION_CHANNEL_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class AmazonawsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                retrofitModule,
                apiModule,
                dataModule,
                useCaseModule,
                teamsViewModelModule,
                matchesViewModelModule
            )
        }
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