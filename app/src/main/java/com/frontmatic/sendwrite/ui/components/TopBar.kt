package com.frontmatic.sendwrite.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry

import com.frontmatic.sendwrite.ui.screen.destinations.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    destination: Destination,
    navBackStackEntry: NavBackStackEntry?
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = destination.topBarTitle(navBackStackEntry),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    )
}

@Composable
fun Destination.topBarTitle(navBackStackEntry: NavBackStackEntry?): String {
    return when (this) {
        else -> "Job Logger"
    }
}