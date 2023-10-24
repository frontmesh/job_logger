package com.frontmatic.sendwrite.ui.screen.job.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Note
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.local.model.JobEventType
import com.frontmatic.sendwrite.ui.pickers.DatePickerField
import com.frontmatic.sendwrite.ui.pickers.TimePickerField
import com.frontmatic.sendwrite.ui.screen.job.AddEventAlertAction
import com.frontmatic.sendwrite.ui.screen.job.JobDetailState
import com.frontmatic.sendwrite.util.JobEventUtility.getAvailableJobEventType
import com.frontmatic.sendwrite.util.JobEventUtility.getIconForJobEvent
import java.time.Instant
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventAlertDialog(
    state: JobDetailState,
    onEventAction: (AddEventAlertAction) -> Unit,
    onDismissed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val calendar = remember {
        Calendar.getInstance()
    }

    val options = enumValues<JobEventType>().toList()
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionType: JobEventType? by remember {
        mutableStateOf(
            state.newEventType
        )
    }

    LaunchedEffect(state.job?.events?.size) {
        if (state.job?.events?.size == 0) {
            selectedOptionType = JobEventType.APPLIED
            onEventAction(AddEventAlertAction.setNewEventType(selectedOptionType))
        }
    }

    AlertDialog(
        modifier = modifier,
        title = { Text("Add a Job Event") },
        text = {
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
                               leadingIcon = {
                                   Icon(
                                       imageVector = getIconForJobEvent(selectionOption),
                                       contentDescription = null,
                                   )
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
                        leadingIcon = {
                            Icon(imageVector = Icons.Rounded.Note, contentDescription = "Job Event Title")
                        }
                    )

                   TextField(
                       value = state.newEventDescription ?: "",
                       minLines = 2,
                       singleLine = false,
                       onValueChange = {
                           onEventAction(AddEventAlertAction.setNewEventDescription(it))
                       },
                       placeholder = {
                           Text("Description")
                       },
                       leadingIcon = {
                           Icon(imageVector = Icons.Rounded.Notes, contentDescription = "Job Event Description")
                       }
                   )
               }

               val isAppliedEvent = state.job?.events?.isNotEmpty() == true

               DatePickerField(
                   text = "Choose a${ if(isAppliedEvent) " due" else ""} date",
                   onSelect = { dateValue ->
                       dateValue?.let {
                         calendar.timeInMillis = it
                       }
                       onEventAction(AddEventAlertAction.setNewEventDate(calendar.timeInMillis))
                   },
                   dateValidator = { timestamp ->
                       if (!isAppliedEvent) {
                           timestamp < Instant.now().toEpochMilli()
                       } else {
                           timestamp > Instant.now().toEpochMilli()
                       }
                   }
               )
               if (state.newEventType != JobEventType.APPLIED) {
                   TimePickerField(
                       text = "Pick a time",
                   ) { time ->
                       time?.let {
                           calendar.set(Calendar.HOUR_OF_DAY, it.hour)
                           calendar.set(Calendar.MINUTE, it.minute)
                           onEventAction(AddEventAlertAction.setNewEventDate(calendar.timeInMillis))
                       }

                   }
               }
           }
        },
        onDismissRequest = { onDismissed() },
        confirmButton = {
            val isEnabled = (!state.newEventTitle.isNullOrEmpty()
//                    && !state.newEventDescription.isNullOrEmpty()
                    && state.newEventType != JobEventType.APPLIED) ||
                    (state.newEventType == JobEventType.APPLIED && state.newEventDate != null)

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


