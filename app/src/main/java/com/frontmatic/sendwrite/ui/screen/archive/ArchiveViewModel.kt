package com.frontmatic.sendwrite.ui.screen.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.sendwrite.local.source.JobLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject() constructor(
    private val localDataSource: JobLocalDataSource
): ViewModel() {

    val archivedJobs = localDataSource.getAllArchivedJobs()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
}