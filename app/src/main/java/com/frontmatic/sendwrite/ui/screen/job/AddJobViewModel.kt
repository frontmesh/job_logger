package com.frontmatic.sendwrite.ui.screen.job

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.sendwrite.local.model.Job
import com.frontmatic.sendwrite.local.model.JobEvent
import com.frontmatic.sendwrite.local.model.JobEventType
import com.frontmatic.sendwrite.local.source.JobEventLocalDataSource
import com.frontmatic.sendwrite.local.source.JobLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddJobViewModel @Inject constructor(
    private val jobDataSource: JobLocalDataSource,
    private val jobEventLocalDataSource: JobEventLocalDataSource
): ViewModel() {
    fun saveJob(job: Job?) {
        if (job == null) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            jobDataSource.saveJob(job)
        }
    }

    fun saveJobEventApplied(
        jobId: Long,
    ) {
        val jobEvent = JobEvent(
            jobId = jobId,
            type = JobEventType.APPLIED,
            date = Date(),
            name = "Job Application",
            description = "Applied for job",
            url = null,
        )

        viewModelScope.launch(Dispatchers.IO) {
            jobEventLocalDataSource.saveJobEvent(jobEvent)
        }

    }


}