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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
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

fun StatsUiState.toPairs(): Map<String, Int> {
    return mapOf(
        "Jobs Saved" to jobsSaved,
        "Jobs Applied" to jobsApplied,
        "Interviews Scheduled" to interviewsScheduled,
        "Assignments Scheduled" to assignmentsScheduled,
        "Other Events Scheduled" to otherEventsScheduled
    )
}

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

    @OptIn(ExperimentalCoroutinesApi::class)
    val statsUiState = _selectedMonth.flatMapLatest { month ->
        flow {
            val jobsSaved = jobReadDataSource.getJobsSavedCountForMonthOffset(month).first()
            val jobsApplied =
                jobEventReadDataSource.getTypeCountForMonthOffset(month, JobEventType.APPLIED)
                    .first()
            val interviewsScheduled =
                jobEventReadDataSource.getTypeCountForMonthOffset(month, JobEventType.INTERVIEW)
                    .first()
            val assignmentsScheduled =
                jobEventReadDataSource.getTypeCountForMonthOffset(month, JobEventType.ASSIGNMENT)
                    .first()
            val otherEventsScheduled =
                jobEventReadDataSource.getTypeCountForMonthOffset(month, JobEventType.OTHER).first()

            emit(
                StatsUiState(
                    jobsSaved,
                    jobsApplied,
                    interviewsScheduled,
                    assignmentsScheduled,
                    otherEventsScheduled
                )
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StatsUiState()
    )

    fun updateSelectedMonth(month: Int) {
        _selectedMonth.value = month
    }

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