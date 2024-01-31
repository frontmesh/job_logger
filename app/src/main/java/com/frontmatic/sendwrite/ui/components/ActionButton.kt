package com.frontmatic.sendwrite.ui.components

import android.app.Activity
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddLink
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.aptabase.Aptabase
import com.frontmatic.sendwrite.AddActivity
import com.frontmatic.sendwrite.ui.screen.destinations.Destination
import com.frontmatic.sendwrite.ui.screen.destinations.HomeScreenDestination
import com.frontmatic.sendwrite.ui.screen.destinations.JobDetailsScreenDestination
import com.frontmatic.sendwrite.ui.screen.job.JobDetailState
import com.frontmatic.sendwrite.ui.screen.job.JobDetailsViewModel

@Composable
fun ActionButton(
    destination: Destination,
    navBackStackEntry: NavBackStackEntry?,
) {
    when (destination) {
        is HomeScreenDestination -> {
            val context = LocalContext.current as Activity
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    Aptabase.instance.trackEvent("clickAddJob", mapOf("clicked" to true))
                    context.startActivity(Intent(context, AddActivity::class.java))
                }
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Outlined.AddLink,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null
                )
            }
        }
        is JobDetailsScreenDestination -> {
            val jobDetailsViewModel = navBackStackEntry?.let {
                hiltViewModel<JobDetailsViewModel>(navBackStackEntry)
            }

            val state by jobDetailsViewModel?.state!!.collectAsState(JobDetailState())
            val job = state.job

            if (job != null) {
                if (job.archived) return
            }

            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.secondary,
                onClick = {
                    jobDetailsViewModel?.toggleAddNewEventDialog(true)
                }
            ) {
                Icon(imageVector = Icons.Outlined.EventAvailable, contentDescription = "Add a new job event")
            }
        }
        else -> {}
    }
}