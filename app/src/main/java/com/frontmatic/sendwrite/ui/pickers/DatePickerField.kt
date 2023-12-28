package com.frontmatic.sendwrite.ui.pickers

import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    onSelect: (Long?) -> Unit,
    text: String,
    dateValidator: (Long) -> Boolean = { true },
) {
    val datePickerState = rememberDatePickerState()
//    val interactionSource = remember { MutableInteractionSource() }
    var openDialog by remember { mutableStateOf(false) }

//    val pressedState = interactionSource.interactions.collectAsState(
//        initial = PressInteraction.Cancel(
//            PressInteraction.Press(Offset.Zero)
//        )
//    )

//    if (pressedState.value is PressInteraction.Release) {
//        openDialog = true
//        interactionSource.tryEmit(PressInteraction.Cancel(PressInteraction.Press(Offset.Zero)))
//    }

    val formattedDate = remember {
        derivedStateOf {
            datePickerState.selectedDateMillis?.let {
                Date(it).toFormattedString()
            }
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
                        onSelect(datePickerState.selectedDateMillis)
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
            DatePicker(
                state = datePickerState,
                dateValidator = dateValidator,
            )
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
        Icon(imageVector = Icons.Filled.DateRange, contentDescription = null)
        Text(text = formattedDate.value ?: text, )
    }


//    TextField(
//        value = formattedDate.value,
//        onValueChange = {},
//        label = { Text("Date") },
//        readOnly = true,
//        leadingIcon = {
//          Icon(imageVector = Icons.Filled.DateRange, contentDescription = null)
//        },
//        interactionSource = interactionSource,
//    )
}

fun Date.toFormattedString(): String {
    val sdf = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
    return sdf.format(this)
}