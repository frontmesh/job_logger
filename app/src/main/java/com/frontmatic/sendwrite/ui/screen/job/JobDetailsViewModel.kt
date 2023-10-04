package com.frontmatic.sendwrite.ui.screen.job

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.sendwrite.local.source.JobLocalDataSource
import com.frontmatic.sendwrite.ui.screen.navArgs
import com.pushpal.jetlime.data.JetLimeItemsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobDetailsViewModel @Inject() constructor(
    savedStateHandle: SavedStateHandle,
    private val jobLocalDataSource: JobLocalDataSource
): ViewModel() {

    private val navArgs: JobDetailsScreenNavArgs = savedStateHandle.navArgs()

    val job = jobLocalDataSource.getJobById(navArgs.id).stateIn(viewModelScope,  SharingStarted.Lazily, null)

    val jobTimeLine = MutableStateFlow(
        JetLimeItemsModel(
            list = mutableListOf()
        )
    )
    .stateIn(viewModelScope, SharingStarted.Lazily, JetLimeItemsModel(list = mutableListOf()))

    fun deleteJob() {
        CoroutineScope(Dispatchers.IO).launch {
            job.collect {
                if (it != null) {
                    jobLocalDataSource.deleteJob(it)
                }
            }
        }
    }

    fun archiveJob() {
        CoroutineScope(Dispatchers.IO).launch {
            job.collect {
                if (it != null) {
                    jobLocalDataSource.archiveJob(it)
                }
            }
        }
    }

}