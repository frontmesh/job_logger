package com.frontmatic.sendwrite.ui.screen.events

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.sendwrite.local.source.JobEventLocalDataSource
import com.frontmatic.sendwrite.ui.screen.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    jobEventLocalDataSource: JobEventLocalDataSource
): ViewModel() {
    private val navArgs: EventListScreenNavArgs = savedStateHandle.navArgs()

    val eventList = when(navArgs.period) {
        EventPeriod.TODAY ->
            jobEventLocalDataSource
                .getTodayEvents().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

        EventPeriod.WEEK ->
            jobEventLocalDataSource
                .getWeeklyEvents().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

        else -> jobEventLocalDataSource
            .getTodayEvents().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    val period = navArgs.period
}