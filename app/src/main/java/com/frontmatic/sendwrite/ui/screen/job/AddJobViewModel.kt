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
    fun saveJob(job: Job?, date: Long? = null) {
        if (job == null) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val jobId = jobDataSource.saveJob(job)

            if (date != null) {
                val jobEvent = JobEvent(
                    type = JobEventType.APPLIED,
                    date = Date(date),
                    name = "Job Application",
                    description = "Applied for job",
                    jobId = jobId,
                    url = null,
                )

                jobEventLocalDataSource.saveJobEvent(jobEvent)
            }
        }
    }

}