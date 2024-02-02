package com.frontmatic.sendwrite.ui.screen.job

import com.frontmatic.sendwrite.local.model.JobEvent
import com.frontmatic.sendwrite.local.model.JobEventType
import java.util.Date

data class JobDetailState(
    val showAddNewEventDialog: Boolean = false,
    val showRemoveEventDialog: Boolean = false,
    val showCloseJobApplicationDialog: Boolean = false,
    val showDeleteJobApplicationDialog: Boolean = false,

    val eventIdToRemove : Long? = null,

    val snackBarUiState : SnackBarUiState = SnackBarUiState(""),
)

data class SnackBarUiState(
    val message: String,
    val showSnackbar: Boolean = false,
    val actionLabel: String? = null,
    val action: (() -> Unit)? = null,
)

data class JobEventDetails(
    val id : Long? = null,
    val jobId: Long = -1,
    val name: String = "",
    val description: String = "",
    val date: Date? = null,
    val url: String? = null,
    val type: JobEventType = JobEventType.OTHER,
)

data class JobEventUiState(
    val jobEventDetails: JobEventDetails = JobEventDetails(),
    val isEntryValid: Boolean = false,
)

fun JobEvent.toItemUiState(isEntryValid: Boolean = false) : JobEventUiState =
    JobEventUiState(
        jobEventDetails = this.toJobEventDetails(),
        isEntryValid = isEntryValid
    )

fun JobEvent.toJobEventDetails() =
    JobEventDetails(
        id = id!!,
        jobId = jobId,
        name = name ?: "",
        description = description ?: "",
        date = date,
        url = url,
        type = type
    )

fun JobEventDetails.toJobEvent() =
    JobEvent(
        id = id,
        jobId = jobId,
        name = name,
        description = description,
        date = date,
        url = url,
        type = type
    )