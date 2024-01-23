package com.frontmatic.sendwrite.ui.components

import android.app.Activity
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddLink
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.EventBusy
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.aptabase.Aptabase
import com.frontmatic.sendwrite.AddActivity
import com.frontmatic.sendwrite.ui.components.multifab.FabIcon
import com.frontmatic.sendwrite.ui.components.multifab.FabOption
import com.frontmatic.sendwrite.ui.components.multifab.MultiFabItem
import com.frontmatic.sendwrite.ui.components.multifab.MultiFloatingActionButton
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


            MultiFloatingActionButton(
                fabIcon = FabIcon(
                    imageVector = Icons.Outlined.Event,
                    iconRotate = 25f,
                ),
                fabOption = FabOption(
                    iconTint = Color.White,
                    backgroundTint = MaterialTheme.colorScheme.tertiary,
                    showLabels = true
                ),
                items = listOf(
                    MultiFabItem(
                        id = 1,
                        label = "Add an event",
                        icon = Icons.Outlined.EventAvailable
                    ),
                    MultiFabItem(
                        id = 2,
                        icon = Icons.Outlined.EventBusy,
                        label = "Remove last event"
                    ),
                    MultiFabItem(
                        id = 3,
                        icon = Icons.Outlined.Archive,
                        label = "Close job application"
                    )
                ),
                onFabItemClicked = { item ->
                    when(item.id) {
                        1 -> jobDetailsViewModel?.toggleAddNewEventDialog(true)
                        2 -> jobDetailsViewModel?.toggleRemoveLastEventDialog(true)
                        3 -> jobDetailsViewModel?.toggleCloseJobApplicationDialog(true)
                    }

                }
            )
        }
        else -> {}
    }
}