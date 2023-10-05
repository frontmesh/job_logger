package com.frontmatic.sendwrite.ui.screen.job.components

import android.os.Build
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.local.model.JobEventType
import com.frontmatic.sendwrite.ui.screen.job.JobDetailState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventAlertDialog(
    state: JobDetailState,
    onDismissed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        modifier = modifier,
        title = { Text("Add a Job Event") },
        text = {
            val options = enumValues<JobEventType>().toList()
            var expanded by remember { mutableStateOf(false) }
            var selectedOptionType by remember { mutableStateOf(options[0]) }
            var dueDate by rememberSaveable { mutableStateOf(null) }

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
                       value = selectedOptionType.name.lowercase().replaceFirstChar { it.uppercase() },
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
                           DropdownMenuItem(
                               text = { Text(selectionOption.name.lowercase().replaceFirstChar { it.uppercase() }) },
                               onClick = {
                                   selectedOptionType = selectionOption
                                   expanded = false
                               },
                               contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                               enabled = (state.job?.events?.isNotEmpty() != true) && selectionOption == JobEventType.APPLIED
                           )
                       }
                   }
               }

               if (selectedOptionType != JobEventType.APPLIED) {
                    TextField(
                        value = state.newEventTitle,
                        onValueChange = {},
                        placeholder = {
                            Text("Title")
                        },
                    )

                   TextField(
                       value = state.newEventDescription,
                       onValueChange = {},
                       placeholder = {
                           Text("Description")
                       },
                   )
               }

               DatePickerField()
           }
        },
        onDismissRequest = { onDismissed() },
        confirmButton = {
            Button(
                onClick = { /*TODO*/ },
                enabled = state.newEventTitle.isNotEmpty()
                        && state.newEventDescription.isNotEmpty()
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
fun DatePickerField() {
    val datePickerState = rememberDatePickerState()
    val interactionSource = remember { MutableInteractionSource() }
    var openDialog by remember { mutableStateOf(false) }

    val pressedState = interactionSource.interactions.collectAsState(
        initial = PressInteraction.Cancel(PressInteraction.Press(Offset.Zero)))

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