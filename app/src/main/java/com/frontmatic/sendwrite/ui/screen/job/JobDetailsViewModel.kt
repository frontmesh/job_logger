package com.frontmatic.sendwrite.ui.screen.job

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
import java.sql.Date
import javax.inject.Inject



@HiltViewModel
class JobDetailsViewModel @Inject() constructor(
    savedStateHandle: SavedStateHandle,
    private val jobLocalDataSource: JobLocalDataSource,
    private val jobEventLocalDataSource: JobEventLocalDataSource,
): ViewModel() {

    private val navArgs: JobDetailsScreenNavArgs = savedStateHandle.navArgs()
    private val _job = jobLocalDataSource.getByIdWithEvents(navArgs.id).stateIn(viewModelScope,  SharingStarted.Lazily, null)
    private val _state = MutableStateFlow(JobDetailState())
    val state = combine(_state, _job,) { state, job ->
         state.copy(
            job = job,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), JobDetailState())

    fun onAddEventAlertAction(action: AddEventAlertAction): Unit {
        when(action) {
            AddEventAlertAction.CancelEvent -> {
                _state.value = _state.value.copy(
                    newEventTitle = null,
                    newEventDescription = null,
                    newEventDate = null,
                    newEventType = null
                )
            }
            is AddEventAlertAction.setNewEventDate -> {
                _state.value = _state.value.copy(newEventDate = action.date)
            }
            is AddEventAlertAction.setNewEventDescription -> {
                _state.value = _state.value.copy(newEventDescription = action.description)
            }
            is AddEventAlertAction.setNewEventTitle -> {
                _state.value = _state.value.copy(newEventTitle = action.title)
            }
            is AddEventAlertAction.setNewEventType -> {
                _state.value = _state.value.copy(newEventType = action.type)
            }
            is AddEventAlertAction.SaveEvent -> {
                val job = _job.value ?: return
                val newEvent = JobEvent(
                    jobId = job.id,
                    type = _state.value.newEventType ?: JobEventType.APPLIED,
                    date = _state.value.newEventDate?.let {
                        Date(it)
                    },
                    name = _state.value.newEventTitle ?: "",
                    description = _state.value.newEventDescription ?: "",
                    url = null,
                )

                viewModelScope.launch(Dispatchers.IO) {
                    jobEventLocalDataSource.saveJobEvent(newEvent)
                    _state.value = _state.value.copy(
                        snackBarState = SnackBarState(
                            message = "${newEvent.typeToString} event added",
                            showSnackbar = true,
                        ),
                        newEventTitle = null,
                        newEventDescription = null,
                        newEventDate = null,
                        newEventType = null,
                    )
                }
            }
        }
    }

    fun onUpdateEvent(event: JobEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            jobEventLocalDataSource.updateJobEvent(event)
        }
    }

    fun removeEvent() {
        CoroutineScope(Dispatchers.IO).launch {
            val lastEventId = _state.value.eventIdToRemove

            // remove last event
            if (lastEventId != null) {
                jobEventLocalDataSource.deleteById(lastEventId)
            }

            _state.value = _state.value.copy(
                snackBarState = SnackBarState(
                    message = "Event removed",
                    showSnackbar = true,
                )
            )

            toggleRemoveEventDialog(false)
        }
    }

    fun deleteJob() {
        viewModelScope.launch(Dispatchers.IO) {
            val jobToDelete = _job.value ?: return@launch
            jobLocalDataSource.deleteJob(jobToDelete.toJob())
            _state.value = _state.value.copy(
                snackBarState = SnackBarState(
                    message = "${jobToDelete.title} deleted",
                    showSnackbar = true,
                )
            )
        }
    }

    fun archiveJob() {
        viewModelScope.launch(Dispatchers.IO) {
            val jobToArchive = _job.value ?: return@launch
            jobLocalDataSource.archiveJob(jobToArchive.toJob())
            _state.value = _state.value.copy(
                snackBarState = SnackBarState(
                    message = "${jobToArchive.title} archived",
                    showSnackbar = true,
                )
            )
        }
    }

    fun toggleAddNewEventDialog(value: Boolean) {
        _state.value = _state.value.copy(showAddNewEventDialog = value)
    }

    fun toggleRemoveEventDialog(value: Boolean) {
        _state.value = _state.value.copy(showRemoveEventDialog = value)
    }

    fun toggleCloseJobApplicationDialog(value: Boolean) {
        _state.value = _state.value.copy(showCloseJobApplicationDialog = value)
    }

    fun toggleDeleteJobApplicationDialog(value: Boolean) {
        _state.value = _state.value.copy(showDeleteJobApplicationDialog = value)
    }

    fun resetSnackBar() {
        _state.value = _state.value.copy(
            snackBarState = SnackBarState(
                message = "",
                showSnackbar = false,
            )
        )
    }

    fun handleEventRemoveButton(jobEvent: JobEvent) {
        _state.value = _state.value.copy(eventIdToRemove = jobEvent.id)
        toggleRemoveEventDialog(true)
    }
}

sealed class AddEventAlertAction {
    data class setNewEventDate(val date: Long?): AddEventAlertAction()
    data class setNewEventTitle(val title: String?): AddEventAlertAction()
    data class setNewEventDescription(val description: String?): AddEventAlertAction()
    data class setNewEventType(val type: JobEventType?): AddEventAlertAction()
    object CancelEvent: AddEventAlertAction()
    object SaveEvent : AddEventAlertAction()
}