package com.frontmatic.sendwrite.ui.screen.job

import com.frontmatic.sendwrite.local.model.Job
import com.frontmatic.sendwrite.local.model.JobAndEvents
import com.frontmatic.sendwrite.local.model.JobEvent
import com.frontmatic.sendwrite.ui.screen.JobSortType

data class JobDetailState(
    val newEventTitle: String = "",
    val newEventDescription: String = "",
    val job: JobAndEvents? = null,
    val jobEvents: List<JobEvent> = emptyList(),
    val showAddNewEventDialog: Boolean = false,
)
