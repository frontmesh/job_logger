package com.frontmatic.sendwrite.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.frontmatic.sendwrite.R
import com.frontmatic.sendwrite.ui.screen.destinations.ArchiveScreenDestination
import com.frontmatic.sendwrite.ui.screen.destinations.Destination
import com.frontmatic.sendwrite.ui.screen.destinations.EventListScreenDestination
import com.frontmatic.sendwrite.ui.screen.destinations.JobDetailsScreenDestination
import com.frontmatic.sendwrite.ui.screen.destinations.SettingsChildScreenDestination
import com.frontmatic.sendwrite.ui.screen.destinations.SettingsScreenDestination
import com.frontmatic.sendwrite.ui.screen.events.EventListViewModel
import com.frontmatic.sendwrite.ui.screen.events.EventPeriod
import com.frontmatic.sendwrite.ui.screen.job.JobDetailsViewModel
import com.frontmatic.sendwrite.ui.screen.settings.SettingsChildViewModel
import com.frontmatic.sendwrite.ui.screen.settings.data.ChildPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    destination: Destination,
    navBackStackEntry: NavBackStackEntry?,
    navController: NavHostController
) {

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = destination.topBarTitle(navBackStackEntry),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 6.dp, end = 12.dp)
                )
            }
        },

        navigationIcon = {
            when (destination) {
                JobDetailsScreenDestination,
                EventListScreenDestination,
                SettingsChildScreenDestination -> {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = stringResource(R.string.go_back),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                else -> {}
            }
        },

        actions = {
            destination.TopBarActions(navBackStackEntry)
        }
    )
}

@Composable
fun Destination.topBarTitle(navBackStackEntry: NavBackStackEntry?): String {
    return when (this) {
        JobDetailsScreenDestination -> {
            val state = navBackStackEntry?.let {
                hiltViewModel<JobDetailsViewModel>(navBackStackEntry).state.collectAsState(null).value
            }
            state?.job?.title ?: "Job Details"
        }

        EventListScreenDestination -> {
            val period = navBackStackEntry?.let {
                hiltViewModel<EventListViewModel>(navBackStackEntry).period
            }

            when (period) {
                EventPeriod.TODAY -> "Today's Events"
                EventPeriod.WEEK -> "This Week's Events"
                else -> "Events"
            }
        }

        ArchiveScreenDestination -> "Archive"
        SettingsScreenDestination -> "Settings"
        SettingsChildScreenDestination -> {
            val childPage = navBackStackEntry?.let {
                hiltViewModel<SettingsChildViewModel>(navBackStackEntry).navArgs.page
            }

            when (childPage) {
                ChildPage.THEMES -> "Themes"
                ChildPage.NOTIFICATIONS -> "Notifications"
                ChildPage.PRIVACY -> "Privacy Policy"
                else -> "Settings"
            }
        }

        else -> stringResource(id = R.string.app_name)
    }
}

@Composable
fun Destination.TopBarActions(navBackStackEntry: NavBackStackEntry?) {
    when (this) {
        JobDetailsScreenDestination -> {
            val jobDetailsViewModel = navBackStackEntry?.let {
                hiltViewModel<JobDetailsViewModel>(navBackStackEntry)
            }

//            val state = jobDetailsViewModel?.state?.collectAsState(JobDetailState())
//            val context = LocalContext.current

            var dropDowMenuExpanded by remember { mutableStateOf(false) }

            IconButton(onClick = {
                dropDowMenuExpanded = !dropDowMenuExpanded
            }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Toolbar Menu Icon"
                )
            }

            DropdownMenu(
                expanded = dropDowMenuExpanded,
                onDismissRequest = { dropDowMenuExpanded = false }
            ) {
                DropdownMenuItem(text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Delete",
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                    }
                }, onClick = {
                    jobDetailsViewModel?.toggleDeleteJobApplicationDialog(true)
                })

            }

        }
        else ->  {}
    }
}