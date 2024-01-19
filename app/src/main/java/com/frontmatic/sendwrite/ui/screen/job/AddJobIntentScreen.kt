package com.frontmatic.sendwrite.ui.screen.job

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.MainActivity
import com.frontmatic.sendwrite.R
import com.frontmatic.sendwrite.local.mapper.toJob
import com.frontmatic.sendwrite.ui.screen.job.components.AddJobContent
import com.kedia.ogparser.OpenGraphResult
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Destination(
    route = "add-job",
)
@Composable
fun AddJobIntentScreen(
    intentJob: OpenGraphResult?,
    viewModel: AddJobViewModel = hiltViewModel(),
) {
    val job by remember { mutableStateOf(intentJob) }
    val topBarTitle = job?.title?.takeIf {
        it.isNotBlank() }?.let { stringResource(R.string.add_a_job_var, it)
    } ?: stringResource(R.string.add_a_job)
    val datePickerState = rememberDatePickerState()
    val context = LocalContext.current as Activity

    val intent = Intent(context, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

    AddJobContent(
        job = job,
        topBarTitle = topBarTitle,
        onJobSave = {
            viewModel.saveJob(job?.toJob(), datePickerState.selectedDateMillis)
            context.startActivity(
                intent
            )
            context.finish()
        },
        onCancel = {
            context.startActivity(intent)
            context.finish()
        },
        datePickerState = datePickerState
    )
}
