package com.frontmatic.sendwrite.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.sendwrite.local.UseCases
import com.frontmatic.sendwrite.local.model.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

enum class JobSortType {
    APPLIED,
    TITLE,
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    useCases: UseCases
): ViewModel() {

    private val _jobSortType = MutableStateFlow(JobSortType.APPLIED)

    val jobs: StateFlow<List<Job>?> = _jobSortType.flatMapLatest {
        when (it) {
            JobSortType.APPLIED -> useCases.getAllJobs()
            JobSortType.TITLE -> useCases.getAllJobsSortedByTitle()
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)
}