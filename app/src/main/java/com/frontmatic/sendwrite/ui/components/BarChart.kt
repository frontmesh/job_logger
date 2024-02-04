package com.frontmatic.sendwrite.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BarChart(
    modifier: Modifier = Modifier,
    data: Map<String, Int>
) {
    val maxValue = data.values.maxOrNull() ?: 0

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
            ,
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start,

        ) {
            
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(50.dp),
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
                    Text(text = (maxValue/2).toString())
                    Spacer(modifier = Modifier.fillMaxHeight(.5f))
                }
            }
            
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
                    .background(Color.Black)
            )

            data.forEach {
                Box(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .width(20.dp)
                        .fillMaxHeight(it.value.toFloat()/maxValue.toFloat())
                        .background(MaterialTheme.colorScheme.primary)
//                        .clickable { }
                )
            }

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.Black)
        )

        Row(
            modifier = Modifier
                .padding(start = 72.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
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

//@Composable
//fun BarChartContent(statsState: StatsUiState) {
//    val maxValue = statsState.run {
//        listOf(jobsSaved, jobsApplied, interviewsScheduled, assignmentsScheduled, otherEventsScheduled).maxOrNull() ?: 1
//    }
//
//    val barChartData = listOf(
//        BarChartData("Jobs Saved", statsState.jobsSaved, MaterialTheme.colorScheme.primary),
//        BarChartData("Jobs Applied", statsState.jobsApplied, MaterialTheme.colorScheme.secondary),
//        BarChartData("Interviews Scheduled", statsState.interviewsScheduled, MaterialTheme.colorScheme.tertiary),
//        BarChartData("Assignments Scheduled", statsState.assignmentsScheduled, MaterialTheme.colorScheme.surfaceTint),
//        BarChartData("Other Events Scheduled", statsState.otherEventsScheduled, MaterialTheme.colorScheme.surface)
//    )
//
//    BarChartBars(barChartData, maxValue)
//}



//@Composable
//fun BarChartBars(barChartData: List<BarChartData>, maxValue: Int) {
//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        val barWidth = size.width / barChartData.size
//        val barSpacing = 8.dp.toPx()
//
//        barChartData.forEachIndexed { index, data ->
//            val barHeight = size.height * (data.value.toFloat() / maxValue)
//            val barRect = Rect(
//                left = index * (barWidth + barSpacing),
//                top = size.height - barHeight,
//                right = (index + 1) * (barWidth + barSpacing) - barSpacing,
//                bottom = size.height
//            )
//
//            DrawRect(data.color, barRect)
//        }
//    }
//}


//@Composable
//private fun DrawScope.DrawRect(color: Color, rect: Rect) {
//    drawRect(color, size = rect.size, topLeft = rect.topLeft)
//}
//
//data class BarChartData(val label: String, val value: Int, val color: Color)

@Preview
@Composable
fun BarChartPreview() {
    Surface(

    ) {
        BarChart(
            modifier = Modifier.padding(50.dp),
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