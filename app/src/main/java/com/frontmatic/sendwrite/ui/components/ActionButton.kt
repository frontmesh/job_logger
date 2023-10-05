package com.frontmatic.sendwrite.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.EventBusy
import androidx.compose.material.icons.outlined.EventNote
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.frontmatic.sendwrite.ui.components.multifab.FabIcon
import com.frontmatic.sendwrite.ui.components.multifab.FabOption
import com.frontmatic.sendwrite.ui.components.multifab.MultiFabItem
import com.frontmatic.sendwrite.ui.components.multifab.MultiFloatingActionButton
import com.frontmatic.sendwrite.ui.screen.destinations.Destination
import com.frontmatic.sendwrite.ui.screen.destinations.HomeScreenDestination
import com.frontmatic.sendwrite.ui.screen.destinations.JobDetailsScreenDestination
import com.frontmatic.sendwrite.ui.screen.job.JobDetailsViewModel

@Composable
fun ActionButton(
    destination: Destination,
    navBackStackEntry: NavBackStackEntry?
) {

    when (destination) {
        is HomeScreenDestination -> {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Outlined.Add,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }
        is JobDetailsScreenDestination -> {
            val jobDetailsViewModel = navBackStackEntry?.let {
                hiltViewModel<JobDetailsViewModel>(navBackStackEntry)
            }

            MultiFloatingActionButton(
                fabIcon = FabIcon(
                    imageVector = Icons.Outlined.Event,
                    iconRotate = 25f,
                ),
                fabOption = FabOption(
                    iconTint = Color.White,
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
                    if (item.id == 1) {
                        jobDetailsViewModel?.toggleAddNewEventDialog(true)
                    } else {
                    }
                }
            )


//            FloatingActionButton(
//                containerColor = MaterialTheme.colorScheme.primary,
//                onClick = { /*TODO*/ }
//            ) {
//                Icon(
//                    modifier = Modifier,
//                    imageVector = Icons.Outlined.Event,
//                    tint = Color.White,
//                    contentDescription = null
//                )
//            }
        }
        else -> {}
    }
}