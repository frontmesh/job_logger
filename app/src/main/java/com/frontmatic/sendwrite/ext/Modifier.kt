package com.frontmatic.sendwrite.ext

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer


fun Modifier.fadingEdge(
    lazyListState: LazyListState,
): Modifier = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()

        if (lazyListState.canScrollBackward) {
            drawRect(
                brush = Brush.verticalGradient(0f to Color.Transparent, 0.1f to Color.White),
                blendMode = BlendMode.DstIn
            )
        }
        if (lazyListState.canScrollForward) {
            drawRect(
                brush = Brush.verticalGradient(0.9f to Color.White, 1f to Color.Transparent),
                blendMode = BlendMode.DstIn
            )
        }
    }