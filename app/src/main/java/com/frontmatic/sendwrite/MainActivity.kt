package com.frontmatic.sendwrite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.frontmatic.sendwrite.ui.components.BottomNavBar
import com.frontmatic.sendwrite.ui.components.RootScaffold
import com.frontmatic.sendwrite.ui.components.TopBar
import com.frontmatic.sendwrite.ui.components.shouldShowScaffoldElements
import com.frontmatic.sendwrite.ui.screen.NavGraphs
import com.frontmatic.sendwrite.ui.theme.SendWriteTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val engine = rememberAnimatedNavHostEngine()
            val navController = engine.rememberNavController()
            val startRoute = NavGraphs.root.startRoute

            SendWriteTheme {
                RootScaffold(
                    navController = navController,
                    startRoute = startRoute,
                    topBar = { destination, navBackStackEntry ->
                        if (destination.shouldShowScaffoldElements) {
                            TopBar(destination = destination, navBackStackEntry = navBackStackEntry)
                        }
                    },
                    bottomBar = { destination ->
                        if (destination.shouldShowScaffoldElements) {
                            BottomNavBar(navController = navController)
                        }
                    },

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
