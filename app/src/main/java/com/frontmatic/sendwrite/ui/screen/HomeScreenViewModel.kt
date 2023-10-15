package com.frontmatic.sendwrite.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.sendwrite.local.model.Job
import com.frontmatic.sendwrite.local.source.JobEventLocalDataSource
import com.frontmatic.sendwrite.local.source.JobLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

enum class JobSortType {
    APPLIED,
    TITLE,
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    jobLocalDataSource: JobLocalDataSource,
    jobEventLocalDataSource: JobEventLocalDataSource,
): ViewModel() {

    private val _jobSortType = MutableStateFlow(JobSortType.APPLIED)

    val jobs: StateFlow<List<Job>?> = _jobSortType.flatMapLatest {
        when (it) {
            JobSortType.APPLIED -> jobLocalDataSource.getAllJobs()
            JobSortType.TITLE -> jobLocalDataSource.getAllJobsSortedByTitle()
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val todayEvents = jobEventLocalDataSource
        .getTodayEvents().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val weeklyEvents = jobEventLocalDataSource
        .getWeeklyEvents().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}