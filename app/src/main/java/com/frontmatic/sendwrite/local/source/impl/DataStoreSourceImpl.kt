package com.frontmatic.sendwrite.local.source.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.frontmatic.sendwrite.data.AppTheme
import com.frontmatic.sendwrite.local.source.DataStoreSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val STORE_NAME = "sendwrite_data_store"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = STORE_NAME)


class DataStoreSourceImpl(context: Context): DataStoreSource {

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val APP_THEME_KEY = stringPreferencesKey("app_theme")
        val JOB_EVENT_NOTIFICATIONS = booleanPreferencesKey("job_event_notifications")
        val DAILY_TIPS_NOTIFICATIONS = booleanPreferencesKey("daily_tips_notifications")
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    }

    private fun preferencesFlow() = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }

    override fun appThemeFlow(): Flow<AppTheme> {
        return preferencesFlow().map { pref ->
            val savedTheme = pref[PreferencesKeys.APP_THEME_KEY]
            if (savedTheme != null) {
                AppTheme.valueOf(savedTheme)
            } else {
                AppTheme.SYSTEM
            }
        }
    }

    override suspend fun updateAppTheme(appTheme: AppTheme) {
        dataStore.edit { pref ->
            pref[PreferencesKeys.APP_THEME_KEY] = appTheme.toString()
        }
    }

    override fun isJobEventNotificationEnabled(): Flow<Boolean> {
        return preferencesFlow()
            .map { preferences ->
                preferences[PreferencesKeys.JOB_EVENT_NOTIFICATIONS] ?: false
            }
    }

    override suspend fun updateIsJobEventNotificationEnabled(enabled: Boolean) {
        dataStore.edit { pref ->
            pref[PreferencesKeys.JOB_EVENT_NOTIFICATIONS] = enabled
        }
    }

    override fun isDailyTipsNotificationEnabled(): Flow<Boolean> {
        return preferencesFlow()
            .map { it[PreferencesKeys.DAILY_TIPS_NOTIFICATIONS] ?: false }
    }

    override suspend fun updateIsDailyTipsNotificationEnabled(isEnabled: Boolean) {
        dataStore.edit { pref ->
            pref[PreferencesKeys.DAILY_TIPS_NOTIFICATIONS] = isEnabled
        }
    }

    override fun isOnboardingCompleted(): Flow<Boolean> {
        return preferencesFlow().map { it[PreferencesKeys.ONBOARDING_COMPLETED] ?: false }
    }



    override suspend fun updateIsOnboardingCompleted(completed: Boolean) {
        dataStore.edit {
            it[PreferencesKeys.ONBOARDING_COMPLETED] = completed
        }
    }
}