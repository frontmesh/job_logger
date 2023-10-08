package com.frontmatic.sendwrite.ui.screen.job.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.local.model.JobEvent
import com.frontmatic.sendwrite.local.model.JobEventType
import com.frontmatic.sendwrite.ui.screen.job.AddEventAlertAction
import com.frontmatic.sendwrite.ui.screen.job.JobDetailState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getAvailableJobEventType(eventType: JobEventType, events: List<JobEvent>): Boolean {
    return when (eventType) {
        JobEventType.APPLIED -> events?.isNotEmpty() != true
        JobEventType.INTERVIEW,
        JobEventType.ASSIGNMENT,
        JobEventType.OTHER -> events?.any { it.type == JobEventType.APPLIED } == true
        JobEventType.OFFER -> events?.any { it.type == JobEventType.INTERVIEW } == true
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventAlertDialog(
    state: JobDetailState,
    onEventAction: (AddEventAlertAction) -> Unit,
    onDismissed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        modifier = modifier,
        title = { Text("Add a Job Event") },
        text = {
            val options = enumValues<JobEventType>().toList()
            var expanded by remember { mutableStateOf(false) }
            var selectedOptionType: JobEventType? by remember {
                mutableStateOf(
                    null
                )
            }

           Column(
               verticalArrangement = Arrangement.spacedBy(8.dp)
           ) {
               ExposedDropdownMenuBox(
                   expanded = expanded,
                   onExpandedChange = { expanded = !expanded },
               ) {
                   TextField(
                       // The `menuAnchor` modifier must be passed to the text field for correctness.
                       modifier = Modifier.menuAnchor(),
                       readOnly = true,
                       value = selectedOptionType?.let { it.name.lowercase().replaceFirstChar { it.uppercase() } } ?: "",
                       onValueChange = {},
                       label = { Text("Event Type") },
                       trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                       colors = ExposedDropdownMenuDefaults.textFieldColors(),
                   )
                   ExposedDropdownMenu(
                       expanded = expanded,
                       onDismissRequest = { expanded = false },
                   ) {
                       options.forEach { selectionOption ->
                           val isOptionEnabled = getAvailableJobEventType(selectionOption, state.job?.events ?: emptyList())
                           DropdownMenuItem(
                               text = { Text(selectionOption.name.lowercase().replaceFirstChar { it.uppercase() }) },
                               onClick = {
                                   selectedOptionType = selectionOption
                                   onEventAction(AddEventAlertAction.setNewEventType(selectedOptionType))
                                   expanded = false
                               },
                               contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                               enabled = isOptionEnabled
                           )
                       }
                   }
               }

               if (selectedOptionType != JobEventType.APPLIED) {
                    TextField(
                        value = state.newEventTitle ?: "",
                        onValueChange = {
                            onEventAction(AddEventAlertAction.setNewEventTitle(it))
                        },
                        placeholder = {
                            Text("Title")
                        },
                    )

                   TextField(
                       value = state.newEventDescription ?: "",
                       onValueChange = {
                           onEventAction(AddEventAlertAction.setNewEventDescription(it))
                       },
                       placeholder = {
                           Text("Description")
                       },
                   )
               }

               DatePickerField(
                     onSelect = {
                        onEventAction(AddEventAlertAction.setNewEventDate(it))
                     }
               )
           }
        },
        onDismissRequest = { onDismissed() },
        confirmButton = {
            val isEnabled = (!state.newEventTitle.isNullOrEmpty()
//                    && !state.newEventDescription.isNullOrEmpty()
                    && state.newEventType != JobEventType.APPLIED) ||
                    (state.newEventType == JobEventType.APPLIED && state.newEventDate != null)

            Log.d("AddEventAlertDialog", "isEnabled: $isEnabled")
            Log.d("AddEventAlertDialog", "state.newEventTitle: ${state.newEventTitle}")
            Log.d("AddEventAlertDialog", "state.newEventDescription: ${state.newEventDescription}")
            Log.d("AddEventAlertDialog", "state.newEventType: ${state.newEventType}")
            Log.d("AddEventAlertDialog", "state.newEventDate: ${state.newEventDate}")

            Button(
                onClick = {
                    onEventAction(AddEventAlertAction.SaveEvent)
                    onDismissed()
                },
                enabled = isEnabled
            ) {
                Text("Add")
            }
        },

        dismissButton = {
            Button(
                onClick = { onDismissed() },
            ) {
                Text("Cancel")
            }
        }

    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(onSelect: (Long?) -> Unit) {
    val datePickerState = rememberDatePickerState()
    val interactionSource = remember { MutableInteractionSource() }
    var openDialog by remember { mutableStateOf(false) }

    val pressedState = interactionSource.interactions.collectAsState(
        initial = PressInteraction.Cancel(
            PressInteraction.Press(Offset.Zero)
        )
    )

    if (pressedState.value is PressInteraction.Release) {
        openDialog = true
        interactionSource.tryEmit(PressInteraction.Cancel(PressInteraction.Press(Offset.Zero)))
    }

    val formattedDate = remember {
        derivedStateOf {
            datePickerState.selectedDateMillis?.let {
                Date(it).toFormattedString()
            } ?: ""
        }
    }


    if (openDialog) {
        val confirmEnabled = remember { derivedStateOf { datePickerState.selectedDateMillis != null } }
        DatePickerDialog(
            onDismissRequest = {
                openDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        onSelect(datePickerState.selectedDateMillis ?: null)
//                        snackScope.launch {
//                            snackState.showSnackbar(
//                                "Selected date timestamp: ${datePickerState.selectedDateMillis}"
//                            )
//                        }
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    TextField(
        value = formattedDate.value,
        onValueChange = {},
        label = { Text("Date") },
        readOnly = true,
        trailingIcon = { Icons.Default.DateRange },
        interactionSource = interactionSource,
    )
}

fun Date.toFormattedString(): String {
    val sdf = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
    return sdf.format(this)
}