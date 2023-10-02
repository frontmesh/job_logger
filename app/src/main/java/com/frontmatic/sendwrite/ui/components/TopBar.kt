package com.frontmatic.sendwrite.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Archive
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.frontmatic.sendwrite.R
import com.frontmatic.sendwrite.ui.screen.destinations.Destination
import com.frontmatic.sendwrite.ui.screen.destinations.JobDetailsScreenDestination
import com.frontmatic.sendwrite.ui.screen.job.JobDetailsViewModel

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
                JobDetailsScreenDestination -> {
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
            destination.topBarActions(navBackStackEntry, navController)
        }
    )
}

@Composable
fun Destination.topBarTitle(navBackStackEntry: NavBackStackEntry?): String {
    return when (this) {
        JobDetailsScreenDestination -> {
            val job = navBackStackEntry?.let {
                hiltViewModel<JobDetailsViewModel>(navBackStackEntry).job.collectAsState(null).value
            }
            job?.title ?: "Job Details"
        }
        else -> "Job Logger"
    }
}

@Composable
fun Destination.topBarActions(navBackStackEntry: NavBackStackEntry?, navController: NavHostController) {
    when (this) {
        JobDetailsScreenDestination -> {
            val jobModel = navBackStackEntry?.let {
                hiltViewModel<JobDetailsViewModel>(navBackStackEntry)
            }

            val job = jobModel?.job?.collectAsState(null)?.value

            var dropDowMenuExpanded by remember { mutableStateOf(false) }

            IconButton(onClick = {
                dropDowMenuExpanded = !dropDowMenuExpanded
            }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Toolbar Menu Icon"
                )
            }

            val context = LocalContext.current
            DropdownMenu(
                expanded = dropDowMenuExpanded,
                onDismissRequest = { dropDowMenuExpanded = false }
            ) {
                DropdownMenuItem(text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Archive,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Archive",
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                    }
                }, onClick = {
                    Toast.makeText(
                        context,
                        "Archive Clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                })
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
                    jobModel?.deleteJob()
                    navController.popBackStack()
                    Toast.makeText(
                        context,
                        "Delete Clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                })

            }

        }
        else ->  {}
    }
}