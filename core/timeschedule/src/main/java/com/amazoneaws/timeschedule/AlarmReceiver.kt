package com.amazoneaws.timeschedule

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.amazonaws.common.formatTime
import com.amazonaws.model.MatchesModel

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private const val BROADCAST_MATCHES_KEY = "AlarmReceiver.BROADCAST_MATCHES_KEY"
        fun getBroadcastIntent(context: Context, matchesModel: MatchesModel) =
            Intent(context, AlarmReceiver::class.java).apply {
                putDataAsBytes(matchesModel, BROADCAST_MATCHES_KEY)
            }
    }

    override fun onReceive(context: Context, intent: Intent) {
        intent.getDataFromBytes<MatchesModel>(BROADCAST_MATCHES_KEY)?.let {
            WakeLocker.acquire(context)
            sendMyNotification(context, it)
            WakeLocker.release()
        }
    }

    private fun sendMyNotification(context: Context, matchesModel: MatchesModel) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            1,
            getNotification(context, matchesModel)
        )
    }

    private fun getNotification(context: Context, matchesModel: MatchesModel): Notification {
        val builder = NotificationCompat.Builder(context, APP_NOTIFICATION_CHANNEL_ID)
            .setContentText(
                context.resources.getString(
                    R.string.text_match_noti_content,
                    "${matchesModel.date?.let { formatTime(it) }}"
                )
            )
            .setContentTitle(
                context.resources.getString(
                    R.string.text_match_noti_title,
                    "${matchesModel.home}", "${matchesModel.away}"
                )
            )
            .setOngoing(true)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setSmallIcon(R.drawable.ic_sports_soccer_24)
            .setWhen(System.currentTimeMillis())
            .setShowWhen(true).setAutoCancel(true)
        return builder.build()
    }
}
