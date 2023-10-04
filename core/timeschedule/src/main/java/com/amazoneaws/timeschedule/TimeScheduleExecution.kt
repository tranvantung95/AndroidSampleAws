package com.amazoneaws.timeschedule

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.core.content.ContextCompat.startActivity
import com.amazonaws.model.MatchesModel
import java.util.Calendar

fun startTimeScheduleForMatch(context: Context, matchesModel: MatchesModel) {
    var alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val triggerAlarm = {
        val intent = AlarmReceiver.getBroadcastIntent(context, matchesModel)
        val pendingIntent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            } else {
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            }


        // TODO: HardCode: start alarm after 5s for easily testing instead of real date data from api
        val calendar: Calendar = Calendar.getInstance().apply {
            add(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 5)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        when {
            // If permission is granted, proceed with scheduling exact alarms.
            alarmManager.canScheduleExactAlarms() -> {
                triggerAlarm.invoke()
            }
            else -> {
                // Ask users to go to exact alarm page in system settings.
                context.startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
            }
        }
    } else {
        triggerAlarm.invoke()
    }
}