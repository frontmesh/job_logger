package com.frontmatic.sendwrite.repository

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.frontmatic.sendwrite.MainActivity
import com.frontmatic.sendwrite.R
import com.frontmatic.sendwrite.data.jobSearchingTips
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    @ApplicationContext val appContext: Context,
): NotificationRepository {

    companion object {
        const val dailyTipChannelId = "daily_tip_channel_id"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun showDailyTipNotification() {
        createDailyTipNotificationChannel()

        val notification = buildDailyTipNotification()
        val notificationId = System.currentTimeMillis().toInt()

        if (NotificationManagerCompat.from(appContext).areNotificationsEnabled()) {
            if (ActivityCompat.checkSelfPermission(
                    appContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                ActivityCompat.requestPermissions(
                    appContext as MainActivity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )

                return
            }
            NotificationManagerCompat.from(appContext).notify(notificationId, notification)
        }


    }

    override fun showJobEventNotification() {
        TODO("Not yet implemented")
    }

    override fun isNotificationPolicyAccessGranted(): Boolean {
        val notificationManager = appContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        return notificationManager.isNotificationPolicyAccessGranted
    }

    override fun areNotificationsEnabled(): Boolean =
        NotificationManagerCompat.from(appContext)
            .areNotificationsEnabled()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createDailyTipNotificationChannel() {
        val name = appContext.getString(R.string.daily_tip_notification_channel_name)
        val descriptionText = appContext.getString(R.string.daily_tip_notification_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(dailyTipChannelId, name, importance).apply {
            description = descriptionText
        }

        val notificationManager = appContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun buildDailyTipNotification(): Notification {
        val intent = Intent(appContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent
            .getActivity(appContext, 0, intent, FLAG_IMMUTABLE)

        return NotificationCompat.Builder(appContext, dailyTipChannelId)
            .setContentTitle("Daily Job Searching Tip")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText(jobSearchingTips.random())
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

    }
}