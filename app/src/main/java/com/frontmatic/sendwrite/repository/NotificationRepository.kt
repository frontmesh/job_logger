package com.frontmatic.sendwrite.repository

interface NotificationRepository {

    fun showDailyTipNotification()

    fun showJobEventNotification()

    fun isNotificationPolicyAccessGranted(): Boolean

    fun areNotificationsEnabled(): Boolean
}