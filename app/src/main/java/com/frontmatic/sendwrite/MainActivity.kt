package com.frontmatic.sendwrite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.frontmatic.sendwrite.data.AppTheme
import com.frontmatic.sendwrite.ui.components.ActionButton
import com.frontmatic.sendwrite.ui.components.BottomNavBar
import com.frontmatic.sendwrite.ui.components.RootScaffold
import com.frontmatic.sendwrite.ui.components.TopBar
import com.frontmatic.sendwrite.ui.components.shouldShowFloatingActionButton
import com.frontmatic.sendwrite.ui.components.shouldShowScaffoldElements
import com.frontmatic.sendwrite.ui.screen.NavGraphs
import com.frontmatic.sendwrite.ui.theme.SendWriteTheme
import com.frontmatic.sendwrite.util.LocalSnackbar
import com.frontmatic.sendwrite.util.LocalSnackbarContext
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val engine = rememberAnimatedNavHostEngine()
            val navController = engine.rememberNavController()
            val startRoute = NavGraphs.root.startRoute
            val hostState = remember { SnackbarHostState() }

            val appTheme = viewModel.appTheme.collectAsState(initial = AppTheme.SYSTEM)
//            val isDarkTheme = when (appTheme.value) {
//                AppTheme.SYSTEM -> isSystemInDarkTheme()
//                AppTheme.LIGHT -> false
//                AppTheme.DARK -> true
//            }

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
                            SnackbarHost(
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
                                    snackbarHostState = hostState,
                                )
                            }
                        }
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            DestinationsNavHost(
                                navController = navController,
                                engine = engine,
                                navGraph = NavGraphs.root
                            )
                        }
                    }
                }
            }
        }
    }
}
