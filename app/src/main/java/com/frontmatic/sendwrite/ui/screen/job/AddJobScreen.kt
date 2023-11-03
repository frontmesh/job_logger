package com.frontmatic.sendwrite.ui.screen.job

import android.app.Activity
import android.os.Build
import android.webkit.URLUtil
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.R
import com.frontmatic.sendwrite.local.mapper.toJob
import com.frontmatic.sendwrite.ui.screen.job.components.AddJobContent
import com.kedia.ogparser.OpenGraphResult


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJobScreen(
    viewModel: AddJobViewModel = hiltViewModel(),
) {

    var job: OpenGraphResult? by remember { mutableStateOf(null) }
    val topBarTitle = job?.let { it.title?.takeIf {
        it.isNotBlank() }?.let { stringResource(R.string.add_a_job_var, it)
    } } ?: stringResource(R.string.add_a_job)
    val datePickerState = rememberDatePickerState()

    val context = LocalContext.current as Activity

    var url by remember { mutableStateOf("") }
    var urlHasError by remember {
        mutableStateOf(false)
    }

    AddJobContent(
        job = job,
        topBarTitle = topBarTitle,
        onJobSave = {
            viewModel.saveJob(job?.toJob(), datePickerState.selectedDateMillis)
            context.finish()
        },
        onCancel = {
            context.finish()
        },
        datePickerState = datePickerState
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            text = "Enter a URL of job posting you wish to track"
        )

        TextField(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            label = { Text("Enter Job URL") },
            isError = urlHasError,
            value = url,
            onValueChange = { newUrl ->
                url = newUrl
                urlHasError = URLUtil.isValidUrl(newUrl).not()

                if (urlHasError.not()) {
                    viewModel.parseUrl(newUrl)
                }
            }
        )


        when (val jobState = viewModel.openGraphUrlState) {
            is OpenGraphUrlState.Loading -> {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    text = "Loading..."
                )
            }
            is OpenGraphUrlState.Success -> {
                job = jobState.result
            }
            is OpenGraphUrlState.Error -> {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    text = "Error loading job details"
                )
            }

            else -> {}
        }
    }
}