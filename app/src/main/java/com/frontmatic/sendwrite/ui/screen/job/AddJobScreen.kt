package com.frontmatic.sendwrite.ui.screen.job

import android.app.Activity
import android.os.Build
import android.webkit.URLUtil
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.R
import com.frontmatic.sendwrite.ext.toJob
import com.frontmatic.sendwrite.ext.useDebounce
import com.frontmatic.sendwrite.local.model.Priority
import com.frontmatic.sendwrite.ui.screen.job.components.AddJobContent
import com.kedia.ogparser.OpenGraphResult

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJobScreen(
    viewModel: AddJobViewModel = hiltViewModel(),
) {
    var job: OpenGraphResult? by rememberSaveable { mutableStateOf(null) }
    val topBarTitle = job?.title?.takeIf {
        it.isNotBlank() }?.let { stringResource(R.string.add_a_job_var, it)
    } ?: stringResource(R.string.add_a_job)
    val datePickerState = rememberDatePickerState()
    var prioritySelected: Priority? by remember { mutableStateOf(null) }


    val context = LocalContext.current as Activity

    var urlText by rememberSaveable { mutableStateOf("") }
    var urlHasError by rememberSaveable {
        mutableStateOf(false)
    }


    urlText.useDebounce(onDebounce = {
        urlHasError = URLUtil.isValidUrl(it).not()
        if (urlHasError.not()) {
            viewModel.parseUrl(it)
        }
    })

    AddJobContent(
        job = job,
        topBarTitle = topBarTitle,
        onJobSave = {
            viewModel.saveJob(
                job?.toJob(prioritySelected),
                datePickerState.selectedDateMillis,
            )
            context.finish()
        },
        onCancel = {
            context.finish()
        },
        datePickerState = datePickerState,
        priority = prioritySelected,
        onPriorityChange = {
            prioritySelected = it
        }
    ) {

        JobEditContent(
            url = urlText,
            urlHasError = urlHasError,
            onTextFieldChange = { newUrl ->
                urlHasError = URLUtil.isValidUrl(newUrl).not()
                urlText = newUrl
            }
        )
        when (val jobState = viewModel.openGraphUrlState) {
            is OpenGraphUrlState.Loading -> {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    text = "Loading..."
                )
            }
            is OpenGraphUrlState.Success -> {
                job = jobState.result
            }
            is OpenGraphUrlState.Error -> {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.error,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(all = 8.dp),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onError,
                    text = "Error loading job details"
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            else -> {}
        }
    }
}


@Composable
fun JobEditContent(
    url: String,
    urlHasError: Boolean,
    onTextFieldChange : (String) -> Unit
) {

    val primaryColor = MaterialTheme.colorScheme.surface

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val borderSize = 1.dp.toPx()
                drawLine(
                    color = primaryColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = borderSize
                )
                drawLine(
                    color = primaryColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderSize
                )
            },
        placeholder = { Text("Enter Job Posting URL") },
        leadingIcon = { Icon(imageVector = Icons.Outlined.Link, contentDescription = "") },
        isError = urlHasError,
        value = url,
        onValueChange = onTextFieldChange,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary,
            focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
            errorPlaceholderColor = MaterialTheme.colorScheme.error,
            focusedBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorLeadingIconColor = MaterialTheme.colorScheme.error,
        )
    )

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun JobEditContentPreview() {
    JobEditContent(
        url = "",
        urlHasError = false,
        onTextFieldChange = {

        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun JobEditContentErrorPreview() {
    JobEditContent(
        url = "",
        urlHasError = true,
        onTextFieldChange = {}
    )
}