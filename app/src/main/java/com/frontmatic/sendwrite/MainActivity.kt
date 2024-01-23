package com.frontmatic.sendwrite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.aptabase.Aptabase
import com.frontmatic.sendwrite.data.AppTheme
import com.frontmatic.sendwrite.ui.components.ActionButton
import com.frontmatic.sendwrite.ui.components.BottomNavBar
import com.frontmatic.sendwrite.ui.components.CustomSnackbarHost
import com.frontmatic.sendwrite.ui.components.RootScaffold
import com.frontmatic.sendwrite.ui.components.TopBar
import com.frontmatic.sendwrite.ui.components.shouldShowFloatingActionButton
import com.frontmatic.sendwrite.ui.components.shouldShowScaffoldElements
import com.frontmatic.sendwrite.ui.screen.NavGraphs
import com.frontmatic.sendwrite.ui.screen.appCurrentDestinationAsState
import com.frontmatic.sendwrite.ui.screen.destinations.OnboardingScreenDestination
import com.frontmatic.sendwrite.ui.theme.SendWriteTheme
import com.frontmatic.sendwrite.util.LocalSnackbar
import com.frontmatic.sendwrite.util.LocalSnackbarContext
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        // Tracking
        Aptabase.instance.initialize(this, "A-EU-9748382829")

        setContent {
            val engine = rememberAnimatedNavHostEngine()
            val isOnboardingCompleted by viewModel.isOnboardingCompleted.collectAsState(true)
            val navController = engine.rememberNavController()
            val startRoute = if (isOnboardingCompleted) NavGraphs.root.startRoute else OnboardingScreenDestination
            val hostState = remember { SnackbarHostState() }

            val appTheme = viewModel.appTheme.collectAsState(initial = AppTheme.SYSTEM)

            CompositionLocalProvider(
                LocalSnackbar provides LocalSnackbarContext(hostState)
            ) {
                SendWriteTheme(
                    appTheme = appTheme.value
                ) {

                    RootScaffold(
                        navController = navController,
                        startRoute = startRoute,
                        snackbarHost = {
                            CustomSnackbarHost(
                                hostState = hostState,
                            )
                        },
                        topBar = { destination, navBackStackEntry ->
                            if (destination.shouldShowScaffoldElements) {
                                TopBar(
                                    destination = destination,
                                    navBackStackEntry = navBackStackEntry,
                                    navController = navController
                                )
                            }
                        },
                        bottomBar = { destination ->
                            if (destination.shouldShowScaffoldElements) {
                                BottomNavBar(navController = navController)
                            }
                        },
                        floatingActionButton = { destination,
                                                 navBackStackEntry ->
                            if (destination.shouldShowFloatingActionButton) {
                                ActionButton(
                                    destination = destination,
                                    navBackStackEntry = navBackStackEntry,
                                )
                            }
                        }
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background // background color for the whole app
                        ) {
                            DestinationsNavHost(
                                navController = navController,
                                engine = engine,
                                navGraph = NavGraphs.root,
                                startRoute = startRoute
                            )

                            ShowOnbardingFlowIfNotCompleted(
                                isOnboardingCompleted = isOnboardingCompleted,
                                navController = navController
                            )
                        }
                    }


                }
            }
        }
    }
}


@Composable
fun ShowOnbardingFlowIfNotCompleted(
    isOnboardingCompleted: Boolean,
    navController: NavController
) {

    val currentDestination by navController.appCurrentDestinationAsState()

    if (!isOnboardingCompleted && currentDestination != OnboardingScreenDestination) {
        navController.navigate(OnboardingScreenDestination) {
            launchSingleTop = true
        }
    }
}