package com.frontmatic.sendwrite.ui.screen.job

import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.sendwrite.local.mapper.toJob
import com.frontmatic.sendwrite.local.model.Job
import com.frontmatic.sendwrite.local.source.JobEventLocalDataSource
import com.frontmatic.sendwrite.local.source.JobLocalDataSource
import com.frontmatic.sendwrite.ui.screen.JobSortType
import com.frontmatic.sendwrite.ui.screen.navArgs
import com.pushpal.jetlime.data.JetLimeItemsModel
import com.pushpal.jetlime.data.config.JetLimeItemConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobDetailsViewModel @Inject() constructor(
    savedStateHandle: SavedStateHandle,
    private val jobLocalDataSource: JobLocalDataSource,
    private val jobEventLocalDataSource: JobEventLocalDataSource,
): ViewModel() {

    private val navArgs: JobDetailsScreenNavArgs = savedStateHandle.navArgs()

    private val _job = jobLocalDataSource.getByIdWithEvents(navArgs.id).stateIn(viewModelScope,  SharingStarted.Lazily, null)

    private val _jobEvents = jobEventLocalDataSource.getByJobId(navArgs.id).stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _state = MutableStateFlow(JobDetailState())

    val state = combine(_state, _job, _jobEvents) { state, job, jobEvents ->
         state.copy(
            job = job,
            jobEvents = jobEvents ?: emptyList()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), JobDetailState())

    val jobTimeLine = MutableStateFlow(
        JetLimeItemsModel(
            list = mutableListOf()
        )
    )
    .stateIn(viewModelScope, SharingStarted.Lazily, JetLimeItemsModel(list = mutableListOf()))

    fun deleteJob() {
        CoroutineScope(Dispatchers.IO).launch {
            _job.collect() {
                jobLocalDataSource.deleteJob(it!!.toJob())
            }
        }
    }

    fun archiveJob(job: Job) {
        CoroutineScope(Dispatchers.IO).launch {
            _job.collect() {
                jobLocalDataSource.archiveJob(it!!.toJob())
            }
        }
    }

    fun toggleAddNewEventDialog(value: Boolean) {
        _state.value = _state.value.copy(showAddNewEventDialog = value)
    }
}