package com.frontmatic.sendwrite.ui.pickers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerField(
    text: String = "Time",
    onSelect: (TimePickerState?) -> Unit) {
    var openDialog by remember { mutableStateOf(false) }
    var hasBeenPicked by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(
        is24Hour = true,
    )

    val formattedTime = remember {
        derivedStateOf {
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
            cal.set(Calendar.MINUTE, timePickerState.minute)
            cal.isLenient = false

            val pattern = if (timePickerState.is24hour) "HH:mm" else "hh:mm a"
            val formatter = SimpleDateFormat(pattern, Locale.getDefault())
            formatter.format(cal.time) ?: text
        }
    }

    if (openDialog) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { openDialog = false },

        ) {
            Surface(
                modifier = Modifier.
                    padding(all = 16.dp)
                    .width(IntrinsicSize.Min)
                    .height(IntrinsicSize.Min)
                    .background(
                        shape = MaterialTheme.shapes.extraLarge,
                        color = MaterialTheme.colorScheme.surface
                    ),
                shape = MaterialTheme.shapes.medium,

            ) {
                Column {
                    Row(
                    ) {
                        TimePicker(
                            state = timePickerState,
                            modifier = Modifier.padding(all = 16.dp),
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { openDialog = false }) {
                            Text(text = "Cancel")
                        }
                        TextButton(onClick = {
                            openDialog = false
                            onSelect(timePickerState)
                            hasBeenPicked = true
                        }) {
                            Text(text = "Ok")
                        }
                    }
                }
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { openDialog = true },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Filled.AccessTime, contentDescription = null)
        Text(text = if (!hasBeenPicked) text else formattedTime.value)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TimePickerFieldPreview() {
    TimePickerField(onSelect = {})
}