package com.frontmatic.sendwrite.ui.screen.styles

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseInElastic
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavBackStackEntry
import com.frontmatic.sendwrite.ui.screen.appDestination
import com.frontmatic.sendwrite.ui.screen.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.spec.DestinationStyle

object AddJobTransitions : DestinationStyle.Animated {

    private val defailtTranstionDuration = 300

    private val defaultEasing = FastOutSlowInEasing

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(): EnterTransition? {

        return when (initialState.appDestination()) {
            HomeScreenDestination ->
                fadeIn(
//                    initialOffsetY = { 1000 },
                    initialAlpha = 0.0f,
                    animationSpec = tween(
                        durationMillis = defailtTranstionDuration,
                        easing = defaultEasing
                    )
                )
            else -> null
        }
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(): ExitTransition? {

        return when (targetState.appDestination()) {
            HomeScreenDestination ->
                fadeOut(
                    targetAlpha = 0.0f,
                )
//                slideOutVertically(
//                    targetOffsetY = { 1000 },
//                    animationSpec = tween(
//                        durationMillis = defailtTranstionDuration,
//                        easing = defaultEasing
//                    )
//                )
            else -> null
        }
    }
//
//    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {
//
//        return when (initialState.appDestination()) {
//            HomeScreenDestination ->
//                slideInVertically(
//                    initialOffsetY = { -1000 },
//                    animationSpec = tween(
//                        durationMillis = defailtTranstionDuration,
//                        easing = defaultEasing
//                    )
//                )
//            else -> null
//        }
//    }
//
//    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
//
//        return when (targetState.appDestination()) {
//            HomeScreenDestination ->
//                slideOutVertically(
//                    targetOffsetY = { 1000 },
//                    animationSpec = tween(
//                        durationMillis = defailtTranstionDuration,
//                        easing = defaultEasing
//                    )
//                )
//            else -> null
//        }
//    }
}