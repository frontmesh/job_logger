package com.frontmatic.sendwrite.ui.screen.job

import androidx.lifecycle.ViewModel
import com.frontmatic.sendwrite.local.model.Job
import com.frontmatic.sendwrite.local.source.JobLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddJobViewModel @Inject constructor(
    private val jobDataSource: JobLocalDataSource,
): ViewModel() {
    fun saveJob(job: Job): kotlinx.coroutines.Job {
        return CoroutineScope(Dispatchers.IO).launch {
            jobDataSource.saveJob(job)
        }
    }
}