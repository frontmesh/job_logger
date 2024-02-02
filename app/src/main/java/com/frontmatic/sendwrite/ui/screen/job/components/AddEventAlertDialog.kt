package com.frontmatic.sendwrite.ui.screen.job.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.local.model.JobEventType
import com.frontmatic.sendwrite.ui.pickers.DatePickerField
import com.frontmatic.sendwrite.ui.pickers.TimePickerField
import com.frontmatic.sendwrite.ui.screen.job.JobEventDetails
import com.frontmatic.sendwrite.ui.screen.job.JobEventUiState
import com.frontmatic.sendwrite.util.JobEventUtility.getIconForJobEvent
import java.sql.Date
import java.time.Instant
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEventAlertDialog(
    uiState: JobEventUiState,
    onEventValueChange: (JobEventDetails) -> Unit,
    onSave: () -> Unit,
    onDismissed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isEdit = uiState.jobEventDetails.id != null


    AlertDialog(
        modifier = modifier.imePadding(),
        title = { Text(if (isEdit) "Edit a Job Event" else "Add a Job Event") },
        text = {
            JobEventInputFrom(
                jobEventDetails = uiState.jobEventDetails,
                onValueChange = onEventValueChange,
            )
        },
        onDismissRequest = { onDismissed() },
        confirmButton = {
            Button(
                onClick = {
                    onSave()
                    onDismissed()
                },
                enabled = uiState.isEntryValid
            ) {
                Text("Add", fontWeight = FontWeight.Bold)
            }
        },

        dismissButton = {
            OutlinedButton(
                onClick = { onDismissed() },
            ) {
                Text("Cancel")
            }
        }

    )
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobEventInputFrom(
    modifier: Modifier = Modifier,
    jobEventDetails: JobEventDetails,
    onValueChange : (JobEventDetails) -> Unit
) {
    val calendar = rememberSaveable {
        Calendar.getInstance()
    }

    val options = enumValues<JobEventType>().toList()
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedOptionType: JobEventType? by rememberSaveable {
        mutableStateOf(jobEventDetails.type)
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
//                    val isOptionEnabled = getAvailableJobEventType(selectionOption, uiState.job?.events ?: emptyList())
                    DropdownMenuItem(
                        text = { Text(selectionOption.name.lowercase().replaceFirstChar { it.uppercase() }) },
                        onClick = {
                            selectedOptionType = selectionOption
                            onValueChange(jobEventDetails.copy(type = selectedOptionType!!))
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = getIconForJobEvent(selectionOption),
                                contentDescription = null,
                            )
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        enabled = true
                    )
                }
            }
        }

        if (selectedOptionType != JobEventType.APPLIED) {
            TextField(
                value = jobEventDetails.name,
                onValueChange = { name ->
                    onValueChange(jobEventDetails.copy(name = name))
                },
                placeholder = {
                    Text("Title")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Note, contentDescription = "Job Event Title")
                }
            )

            TextField(
                value = jobEventDetails.description,
                minLines = 2,
                singleLine = false,
                onValueChange = { description ->
                    onValueChange(jobEventDetails.copy(description = description))
                },
                placeholder = {
                    Text("Description")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Notes, contentDescription = "Job Event Description")
                }
            )
        }

        val isAppliedEvent = selectedOptionType == JobEventType.APPLIED

        DatePickerField(
            placeholder = "Set a date",
            initialSelectedDateMillis = jobEventDetails.date?.time,
            onSelect = { dateValue ->
                dateValue?.let {
                    calendar.timeInMillis = it
                }
                onValueChange(jobEventDetails.copy(date = Date(calendar.timeInMillis)))
            },
            dateValidator = { timestamp ->
                if (isAppliedEvent) {
                    timestamp < Instant.now().toEpochMilli()
                } else {
                    timestamp > Instant.now().toEpochMilli()
                }
            }
        )
        if (jobEventDetails.type!= JobEventType.APPLIED) {

            TimePickerField(
                placeholder = "Set a time",
                initialHour =  jobEventDetails.date?.time?.let {
                    val c = Calendar.getInstance()
                    c.timeInMillis = it
                    c.get(Calendar.HOUR_OF_DAY)
                } ?: 0,
                initialMinute =  jobEventDetails.date?.time?.let {
                    val c = Calendar.getInstance()
                    c.timeInMillis = it
                    c.get(Calendar.MINUTE)
                } ?: 0,
            ) { time ->
                time?.let {
                    calendar.set(Calendar.HOUR_OF_DAY, it.hour)
                    calendar.set(Calendar.MINUTE, it.minute)
                    onValueChange(jobEventDetails.copy(date = Date(calendar.timeInMillis)))
                }

            }
        }
    }
}



