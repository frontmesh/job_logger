package com.frontmatic.sendwrite.ui.components.anim

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
fun Pulsating(
    pulseFraction: Float = .95f,
    content: @Composable () -> Unit
) {

    val infiniteTransition = rememberInfiniteTransition(label = "")

    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = pulseFraction,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    Box(
        modifier = Modifier
            .alpha(alpha)
    ) {
        content()
    }
}