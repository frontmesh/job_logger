package com.frontmatic.sendwrite.ui.screen.job

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.sendwrite.local.mapper.toJob
import com.frontmatic.sendwrite.local.model.JobEvent
import com.frontmatic.sendwrite.local.model.JobEventType
import com.frontmatic.sendwrite.local.source.JobEventLocalDataSource
import com.frontmatic.sendwrite.local.source.JobLocalDataSource
import com.frontmatic.sendwrite.ui.screen.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
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

    val jobDetail = jobLocalDataSource.getByIdWithEvents(navArgs.id)
        .stateIn(viewModelScope,  SharingStarted.Lazily, null)

    var uiState by mutableStateOf(JobDetailState())

    var jobEventUiState by mutableStateOf(
        value = JobEventUiState(
            jobEventDetails = JobEventDetails(jobId = navArgs.id)
        )
    )

    fun updateJobEventUiState(jobEventDetails: JobEventDetails) {
        jobEventUiState = JobEventUiState(
            jobEventDetails = jobEventDetails,
            isEntryValid = validateInput(
                jobEventDetails
            )
        )
    }

    fun validateInput(uiState: JobEventDetails = jobEventUiState.jobEventDetails): Boolean {
        return with(uiState) {
            (name.isNotBlank() && type != JobEventType.APPLIED) ||
                    (date != null && type == JobEventType.APPLIED)
        }
    }

    fun onUpdateEvent(event: JobEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            jobEventLocalDataSource.updateJobEvent(event)
        }
    }

    fun saveEvent() {
        if (validateInput()) {
            viewModelScope.launch(Dispatchers.IO) {
                jobEventLocalDataSource.saveJobEvent(jobEventUiState.jobEventDetails.toJobEvent())
            }
        }
    }

    fun removeEvent() {
        CoroutineScope(Dispatchers.IO).launch {
            val lastEventId = uiState.eventIdToRemove

            // remove last event
            if (lastEventId != null) {
                jobEventLocalDataSource.deleteById(lastEventId)
            }

            showSnackbarMessage("Event removed")
            toggleRemoveEventDialog(false)
        }
    }

    fun deleteJob() {
        viewModelScope.launch(Dispatchers.IO) {
            val jobToDelete = jobDetail.value ?: return@launch
            jobLocalDataSource.deleteJob(jobToDelete.toJob())
            showSnackbarMessage("${jobToDelete.title} deleted")
        }
    }

    fun archiveJob() {
        viewModelScope.launch(Dispatchers.IO) {
            val jobToArchive = jobDetail.value ?: return@launch
            jobLocalDataSource.archiveJob(jobToArchive.toJob())
            showSnackbarMessage("${jobToArchive.title} archived")
        }
    }

    fun toggleAddNewEventDialog(value: Boolean) {
        uiState = uiState.copy(showAddNewEventDialog = value)
    }

    fun toggleRemoveEventDialog(value: Boolean) {
        uiState = uiState.copy(showRemoveEventDialog = value)
    }

    fun toggleCloseJobApplicationDialog(value: Boolean) {
        uiState = uiState.copy(showCloseJobApplicationDialog = value)
    }

    fun toggleDeleteJobApplicationDialog(value: Boolean) {
        uiState = uiState.copy(showDeleteJobApplicationDialog = value)
    }

    fun resetSnackBar() {
        uiState = uiState.copy(
            snackBarUiState = SnackBarUiState(
                message = "",
                showSnackbar = false,
            )
        )
    }

    fun showSnackbarMessage(msg:String) {
        uiState = uiState.copy(
            snackBarUiState = SnackBarUiState(
                message = msg,
                showSnackbar = true,
            )
        )
    }

    fun handleEventRemoveButton(jobEvent: JobEvent) {
        uiState = uiState.copy(eventIdToRemove = jobEvent.id)
        toggleRemoveEventDialog(true)
    }
}