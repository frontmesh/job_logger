package com.frontmatic.sendwrite.ui.components.domain

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.frontmatic.sendwrite.ext.fadingEdge
import com.frontmatic.sendwrite.local.model.Job
import com.frontmatic.sendwrite.local.model.JobAndEvents
import com.frontmatic.sendwrite.local.model.JobEventType
import com.frontmatic.sendwrite.ui.components.EmptyContent
import com.frontmatic.sendwrite.ui.theme.ICON_SMALL

@Composable
fun JobListContent(
    jobs: List<JobAndEvents>?,
    onItemClick: (JobAndEvents) -> Unit,
    emptyContentMessage: String,
    emptyContentIcon: ImageVector,
    listCellBackground: Color,
    modifier: Modifier = Modifier,
) {
    if (jobs != null && jobs.isEmpty()) {
        EmptyContent(
            icon = emptyContentIcon,
            message = emptyContentMessage
        )
    }
    if (!jobs.isNullOrEmpty()){


        Column() {
            val topFade = Brush.verticalGradient(0f to Color.Transparent, 0.3f to Color.White)
            val topBottomFade = Brush.verticalGradient(0f to Color.Transparent, 0.05f to Color.White, 0.95f to Color.White, 1f to Color.Transparent)

            LazyColumn(
                modifier = modifier
                    .fadingEdge(topBottomFade)
                    .background(Color.Transparent)
                    .fillMaxSize()
            ) {
                items(
                    items = jobs,
                    key = { item -> item.id.toString() }
                ) {job ->
                    ListCell(
                        name = job.title,
                        imageUrl = job.imageUrl,
                        listCellBackground = listCellBackground,
                        onClick = {
                            onItemClick(job)
                        },
                        trailingContent = {
                            Column(
                                verticalArrangement = Arrangement.Top,
                            ) {
                                Row(
//                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Event,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
                                        modifier = Modifier.size(ICON_SMALL)
                                    )
                                    Text(
                                        text = "${job.events.size}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f)
                                    )
                                }
                            }
                        },
                        secondaryContent = {
                            job.events.find { jobEvent ->
                                jobEvent.type == JobEventType.APPLIED
                            }?.let {
                                Icon(
                                    imageVector = Icons.Filled.AccessTime,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                    modifier = Modifier.size(ICON_SMALL)
                                )
                                Text(
                                    text = it.dateToRelativeTime,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}