package com.frontmatic.sendwrite.ui.components.timeline

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.rounded.AppRegistration
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.data.AppTheme
import com.frontmatic.sendwrite.ui.screen.job.TimeChipContent
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
import com.frontmatic.sendwrite.ui.theme.SendWriteTheme
import com.frontmatic.sendwrite.ui.theme.Shape

enum class TimelineNodePosition {
    FIRST,
    MIDDLE,
    LAST
}

data class StrokeParameters(
    val color: Color,
    val width: Dp
)

data class CircleParameters(
    val radius: Dp,
    val backgroundColor: Color,
    val stroke: StrokeParameters? = null,
    var icon: ImageVector? = null,
    val iconColor: Color = Color.White
)

data class LineParameters(
    val strokeWidth: Dp,
    val brush: Brush
)

object LineParametersDefaults {

    private val defaultStrokeWidth = 5.dp

    fun linearGradient(
        strokeWidth: Dp = defaultStrokeWidth,
        startColor: Color,
        endColor: Color,
        startY: Float = 0.0f,
        endY: Float = Float.POSITIVE_INFINITY
    ): LineParameters {
        val brush = Brush.verticalGradient(
            colors = listOf(startColor, endColor),
            startY = startY,
            endY = endY
        )
        return LineParameters(strokeWidth, brush)
    }

    fun solidColor(
        strokeWidth: Dp = defaultStrokeWidth,
        color: Color
    ): LineParameters {
        val brush = SolidColor(
            value = color
        )
        return LineParameters(strokeWidth, brush)
    }
}


object CircleParametersDefaults {

    private val defaultCircleRadius = 16.dp
    
    private val defaultIconSize = 10.dp

    fun circleParameters(
        radius: Dp = defaultCircleRadius,
        backgroundColor: Color = Color.Cyan,
        icon: ImageVector? = null,
        iconColor : Color = Color.White,
        stroke: StrokeParameters? = StrokeParameters(
            color = Color.White,
            width = 3.dp
        )
    ) = CircleParameters(
        radius,
        backgroundColor,
        icon = icon,
        stroke = stroke,
        iconColor = iconColor
    )
}


@Composable
fun TimelineNode(
    modifier: Modifier = Modifier,
    position: TimelineNodePosition,
    icon: ImageVector? = null,
    iconColor: Color = MaterialTheme.colorScheme.primary,
    circleParameters: CircleParameters,
    lineParameters: LineParameters? = null,
    contentStartOffset: Dp = 16.dp,
    spacerBetweenNodes: Dp = 24.dp,
    content: @Composable BoxScope.(modifier: Modifier) -> Unit,
) {
    val iconPainter = icon?.let {
        rememberVectorPainter(image = it)
    }

    val scale = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
            scale.animateTo(1f, animationSpec = tween(300, easing = FastOutSlowInEasing))
    }

        Box(
            modifier = modifier
                .wrapContentSize()
                .drawBehind {
                    val circleRadiusInPx = circleParameters.radius.toPx()

                    lineParameters?.let {
                        if (position != TimelineNodePosition.LAST) {
                            drawLine(
                                brush = it.brush,
                                start = Offset(x = circleRadiusInPx, y = (circleRadiusInPx * 2) * scale.value),
                                end = Offset(x = circleRadiusInPx, y = this.size.height * scale.value),
                                strokeWidth = it.strokeWidth.toPx()
                            )
                        }
                    }

                    drawCircle(
                        color = circleParameters.backgroundColor,
                        radius = circleRadiusInPx * scale.value,
                        center = Offset(circleRadiusInPx, circleRadiusInPx)
                    )

                    circleParameters.stroke?.let { stroke ->
                        val strokeWidthInPx = stroke.width.toPx()
                        drawCircle(
                            color = stroke.color,
                            radius = (circleRadiusInPx - strokeWidthInPx / 2f) * scale.value,
                            center = Offset(circleRadiusInPx, circleRadiusInPx),
                            style = Stroke(width = strokeWidthInPx)
                        )
                    }

                    iconPainter?.let { painter ->
                        this.withTransform(
                            transformBlock = {
                                translate(
                                    left = circleRadiusInPx - painter.intrinsicSize.width / 2f,
                                    top = circleRadiusInPx - painter.intrinsicSize.height / 2f
                                )
                                scale(scale.value, scale.value, Offset(circleRadiusInPx, circleRadiusInPx))
                            },
                            drawBlock = {
                                this.drawIntoCanvas {
                                    with(painter) {
                                        draw(
                                            intrinsicSize,
                                            colorFilter = ColorFilter.tint(
                                                color = iconColor,
                                                blendMode = BlendMode.SrcIn
                                            )
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
        ) {
            content(
                Modifier
                    .padding(
                        start = circleParameters.radius * 2 + contentStartOffset,
                        bottom = if (position != TimelineNodePosition.LAST) {
                            spacerBetweenNodes
                        } else {
                            0.dp
                        }
                    )
                    .graphicsLayer {
                        scaleX = scale.value
                        scaleY = scale.value
                    }
            )
    }
}

@Composable
fun MessageBubble(
    modifier: Modifier = Modifier,
    containerColor: Color =  MaterialTheme.colorScheme.primaryContainer,
    content : @Composable () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = Shape.medium,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun TimelinePreview() {
    SendWriteTheme(appTheme = AppTheme.LIGHT_ORANGE) {

        val circleParameters = CircleParametersDefaults.circleParameters(
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            stroke = StrokeParameters(
                color = MaterialTheme.colorScheme.secondaryContainer,
                width = 3.dp
            )
        )
        val lineParameters = LineParametersDefaults.solidColor(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PAGE_PADDING)
        ) {
            TimelineNode(
                position = TimelineNodePosition.FIRST,
                icon = Icons.Rounded.AppRegistration,
                circleParameters = circleParameters,
                lineParameters = lineParameters,
            ) { modifier ->
                MessageBubble(modifier) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Applied",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentSize(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            TimeChipContent(text = "14 days ago", selected = true)
                        }
                    }
                }
            }
            TimelineNode(
                position = TimelineNodePosition.MIDDLE,
                icon = Icons.Filled.People,
                circleParameters = circleParameters,
                lineParameters = lineParameters,
            ) { modifier ->
                MessageBubble(modifier) {
                    Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
                        Text(
                            text = "Interview",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            TimelineNode(
                position = TimelineNodePosition.LAST,
                icon = Icons.Rounded.AttachMoney,
                circleParameters = circleParameters,
                lineParameters = lineParameters,
            ) { modifier ->
                MessageBubble(modifier) {
                    Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
                        Text(
                            text = "Offer",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}