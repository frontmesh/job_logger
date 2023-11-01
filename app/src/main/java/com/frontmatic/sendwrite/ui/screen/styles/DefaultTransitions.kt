package com.frontmatic.sendwrite.ui.screen.styles

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry
import com.frontmatic.sendwrite.ui.screen.appDestination
import com.ramcosta.composedestinations.spec.DestinationStyle

object DefaultTransitions: DestinationStyle.Animated {

    private const val defaultTransitionDuration = 300

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(): EnterTransition? {

        return when (initialState.appDestination()) {
            else -> fadeIn(
                initialAlpha = 0.0f,
                animationSpec = tween(
                    durationMillis = defaultTransitionDuration,
                )
            )
        }
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(): ExitTransition? {

        return when (targetState.appDestination()) {
            else ->
                fadeOut(
                    targetAlpha = 0.0f,
                    animationSpec = tween(
                        durationMillis = defaultTransitionDuration,
                    )
                )
        }
    }
}