package com.frontmatic.sendwrite.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.ui.theme.LargeRadius

@Composable
fun BarChart(
    modifier: Modifier = Modifier,
    data: Map<String, Int>,
    chartHeight : Dp = 250.dp,
    initialVisibility: Boolean = false
) {
    val maxValue = data.values.maxOrNull() ?: 0

    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

    val guideLineTint = MaterialTheme.colorScheme.onBackground

    val horizontalChartArrangement = Arrangement.spacedBy(32.dp, Alignment.Start)

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(LargeRadius)
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, end = 24.dp, bottom = 16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(chartHeight),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = horizontalChartArrangement,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(25.dp)
                        .drawBehind {
                            drawLine(
                                color = guideLineTint,
                                start = Offset(size.width, 0f),
                                end = Offset(size.width, size.height),
                                strokeWidth = 2f,
                                pathEffect = pathEffect
                            )
                        },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(text = maxValue.toString())
                        Spacer(modifier = Modifier.fillMaxHeight())
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(text = (maxValue / 2).toString())
                        Spacer(modifier = Modifier.fillMaxHeight(.5f))
                    }
                }

                data.forEach {
                    AnimatedBar(
                        value = it.value.toFloat(),
                        maxValue = maxValue.toFloat(),
                    )
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(
                            color = guideLineTint,
                            start = Offset(25.dp.toPx(), 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 2f,
                            pathEffect = pathEffect
                        )
                    },
                horizontalArrangement = horizontalChartArrangement
            ) {
                Box(
                    modifier = Modifier.width(25.dp),
                )
                data.values.forEach {
                    Text(
                        modifier = Modifier.width(20.dp),
                        text = it.toString(),
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
    }
}

@Composable
fun AnimatedBar(value: Float, maxValue: Float) {
    Box(
        modifier = Modifier
            .animateContentSize()
            .clip(RoundedCornerShape(LargeRadius))
            .width(20.dp)
            .fillMaxHeight(if (value > 0) value / maxValue else 0f)
            .background(MaterialTheme.colorScheme.tertiary)

    )
}

@Preview
@Composable
fun BarChartPreview() {
    Surface{
        BarChart(
            modifier = Modifier.padding(50.dp),
            initialVisibility = true,
            data = mapOf(
                "Jobs Saved" to 20,
                "Jobs Applied" to 15,
                "Interviews Scheduled" to 12,
                "Assignments Scheduled" to 5,
                "Other Events Scheduled" to 2
            ),
        )
    }
}