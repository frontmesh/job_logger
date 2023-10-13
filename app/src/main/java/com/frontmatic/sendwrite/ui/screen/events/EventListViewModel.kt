package com.frontmatic.sendwrite.ui.screen.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.sendwrite.local.source.JobEventLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    val jobEventLocalDataSource: JobEventLocalDataSource
): ViewModel() {

    val eventList = jobEventLocalDataSource
        .getTodayEvents().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}