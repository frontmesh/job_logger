package com.frontmatic.sendwrite.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import com.frontmatic.sendwrite.ui.screen.destinations.Destination
import com.frontmatic.sendwrite.ui.screen.destinations.HomeScreenDestination
import com.frontmatic.sendwrite.ui.screen.destinations.JobDetailsScreenDestination

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
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Outlined.Event,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }
        else -> {}
    }
}