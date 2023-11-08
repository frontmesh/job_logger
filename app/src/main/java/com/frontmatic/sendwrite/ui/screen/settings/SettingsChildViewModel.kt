package com.frontmatic.sendwrite.ui.screen.settings

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.sendwrite.data.AppTheme
import com.frontmatic.sendwrite.local.source.DataStoreSource
import com.frontmatic.sendwrite.repository.NotificationRepositoryImpl
import com.frontmatic.sendwrite.ui.screen.navArgs
import com.frontmatic.sendwrite.worker.DailyTipNotificationWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsChildViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dataStoreSource: DataStoreSource,
    private val notificationRepository: NotificationRepositoryImpl,
): ViewModel() {
    val navArgs: SettingsChildScreenNavArgs = savedStateHandle.navArgs()

    val appTheme = dataStoreSource.appThemeFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = AppTheme.LIGHT_GREEN
    )

    fun updateAppTheme(appTheme: AppTheme) {
        viewModelScope.launch {
            dataStoreSource.updateAppTheme(appTheme = appTheme)
        }
    }

    val isJobEventNotificationEnabled = dataStoreSource.isJobEventNotificationEnabled().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = false
    )

    fun updateIsJobEventNotificationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            dataStoreSource.updateIsJobEventNotificationEnabled(enabled = enabled)
        }
    }

    val isDailyTipsNotificationEnabled = dataStoreSource.isDailyTipsNotificationEnabled().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = false
    )

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateIsDailyTipsNotificationEnabled(enabled: Boolean, context: Context) {
        viewModelScope.launch {
            dataStoreSource.updateIsDailyTipsNotificationEnabled(enabled = enabled)
            updateDailyTipsNotificationWorker(enabled, context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDailyTipsNotificationWorker(isEnabled: Boolean, context: Context) {
        if (isEnabled) {
            DailyTipNotificationWorker.enqueue(context)
        } else {
            DailyTipNotificationWorker.cancel(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun testDailyTipsNotification() {
        Log.d("SettingsChildViewModel", "testDailyTipsNotification: ")
        notificationRepository.showDailyTipNotification()
    }

}