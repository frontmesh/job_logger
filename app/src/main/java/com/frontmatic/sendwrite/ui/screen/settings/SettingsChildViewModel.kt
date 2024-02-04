package com.frontmatic.sendwrite.ui.screen.settings

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.sendwrite.data.AppTheme
import com.frontmatic.sendwrite.local.model.JobEventType
import com.frontmatic.sendwrite.local.source.DataStoreSource
import com.frontmatic.sendwrite.local.source.JobEventReadDataSource
import com.frontmatic.sendwrite.local.source.JobReadDataSource
import com.frontmatic.sendwrite.repository.NotificationRepositoryImpl
import com.frontmatic.sendwrite.ui.screen.navArgs
import com.frontmatic.sendwrite.worker.DailyTipNotificationWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StatsUiState (
    val jobsSaved: Int = 0,
    val jobsApplied: Int = 0,
    val interviewsScheduled: Int = 0,
    val assignmentsScheduled: Int = 0,
    val otherEventsScheduled: Int = 0,
)

@HiltViewModel
class SettingsChildViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dataStoreSource: DataStoreSource,
    jobReadDataSource: JobReadDataSource,
    jobEventReadDataSource: JobEventReadDataSource,
    private val notificationRepository: NotificationRepositoryImpl,
): ViewModel() {
    val navArgs: SettingsChildScreenNavArgs = savedStateHandle.navArgs()
    val appTheme = dataStoreSource.appThemeFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = AppTheme.getDefaultTheme()
    )

    private val _selectedMonth = MutableStateFlow(0)
    val selectedMonth: StateFlow<Int> get() = _selectedMonth

    val statsUiState = combine(
        jobReadDataSource.getJobsSavedCountForMonthOffset(selectedMonth.value),
        jobEventReadDataSource.getTypeCountForMonthOffset(selectedMonth.value, JobEventType.APPLIED),
        jobEventReadDataSource.getTypeCountForMonthOffset(selectedMonth.value, JobEventType.INTERVIEW),
        jobEventReadDataSource.getTypeCountForMonthOffset(selectedMonth.value, JobEventType.ASSIGNMENT),
        jobEventReadDataSource.getTypeCountForMonthOffset(selectedMonth.value, JobEventType.OTHER),
    ) {  jobsSaved, jobsApplied, interviewsScheduled, assignmentsScheduled, otherEventsScheduled ->
            StatsUiState(jobsSaved, jobsApplied, interviewsScheduled, assignmentsScheduled, otherEventsScheduled)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = StatsUiState()
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
//        Log.d("SettingsChildViewModel", "testDailyTipsNotification: ")
        notificationRepository.showDailyTipNotification()
    }




}