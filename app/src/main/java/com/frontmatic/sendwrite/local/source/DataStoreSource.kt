package com.frontmatic.sendwrite.local.source

import com.frontmatic.sendwrite.data.AppTheme
import kotlinx.coroutines.flow.Flow

interface DataStoreSource {
    fun appThemeFlow(): Flow<AppTheme>
    suspend fun updateAppTheme(appTheme: AppTheme)

    fun isJobEventNotificationEnabled(): Flow<Boolean>
    suspend fun updateIsJobEventNotificationEnabled(enabled: Boolean)

    fun isDailyTipsNotificationEnabled(): Flow<Boolean>
    suspend fun updateIsDailyTipsNotificationEnabled(enabled: Boolean)

}