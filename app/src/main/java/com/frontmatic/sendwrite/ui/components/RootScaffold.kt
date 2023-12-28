package com.frontmatic.sendwrite.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.frontmatic.sendwrite.ui.screen.appCurrentDestinationAsState
//import com.frontmatic.sendwrite.ui.screen.destinations.AddJobScreenDestination
import com.frontmatic.sendwrite.ui.screen.destinations.Destination
import com.frontmatic.sendwrite.ui.screen.destinations.HomeScreenDestination
import com.frontmatic.sendwrite.ui.screen.destinations.JobDetailsScreenDestination
import com.frontmatic.sendwrite.ui.screen.startAppDestination
import com.ramcosta.composedestinations.spec.Route

@Composable
fun RootScaffold(
    startRoute: Route,
    navController : NavController,
    topBar: @Composable (Destination, NavBackStackEntry?) -> Unit,
    bottomBar: @Composable (Destination) -> Unit,
    floatingActionButton: @Composable (Destination, NavBackStackEntry?) -> Unit ,
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val navBackStackEntry = navController.currentBackStackEntry
    val destination = navController.appCurrentDestinationAsState().value
        ?: startRoute.startAppDestination


    Scaffold(
        topBar = { topBar(destination, navBackStackEntry) },
        bottomBar = { bottomBar(destination) },
        floatingActionButton = {
            floatingActionButton(
                destination,
                navBackStackEntry,
            )
       },
        snackbarHost = snackbarHost,
    )
    {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {
            content(it)
        }
    }
}

val Destination.shouldShowScaffoldElements get() = true
//    when (this) {
////        is AddJobScreenDestination -> false
//        else -> true
//    }

val Destination.shouldShowFloatingActionButton get() =
    when (this) {
        is HomeScreenDestination -> true
        is JobDetailsScreenDestination -> true
        else -> false
    }