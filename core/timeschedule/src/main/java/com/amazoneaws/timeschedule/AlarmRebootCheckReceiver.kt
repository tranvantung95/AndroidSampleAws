package com.amazoneaws.timeschedule

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.Calendar

class AlarmRebootCheckReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            // Set the alarm here.
            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val newIntent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(context, 0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val calendar: Calendar = Calendar.getInstance().apply {
                add(Calendar.MINUTE, 1)
                set(Calendar.SECOND, 0)
            }

            alarmMgr.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                1000 * 60 * 1, // repeat sau 1p
                pendingIntent
            )
        }
    }
}