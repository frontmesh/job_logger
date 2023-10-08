package com.frontmatic.sendwrite.ui.screen.job

import com.frontmatic.sendwrite.local.model.Job
import com.frontmatic.sendwrite.local.model.JobAndEvents
import com.frontmatic.sendwrite.local.model.JobEvent
import com.frontmatic.sendwrite.local.model.JobEventType
import com.frontmatic.sendwrite.ui.screen.JobSortType

data class JobDetailState(
    var newEventTitle: String? = null,
    var newEventDescription: String? = null,
    var newEventDate: Long? = null,
    var newEventType: JobEventType? = null,

    val job: JobAndEvents? = null,

    val showAddNewEventDialog: Boolean = false,
    val showRemoveEventDialog: Boolean = false,
    val showCloseJobApplicationDialog: Boolean = false,

    val snackBarState : SnackBarState = SnackBarState(""),
)

data class SnackBarState(
    val message: String,
    val showSnackbar: Boolean = false,
    val actionLabel: String? = null,
    val action: (() -> Unit)? = null,
)