package com.frontmatic.sendwrite.ui.components.domain

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.ext.fadingEdge
import com.frontmatic.sendwrite.local.model.JobAndEvents
import com.frontmatic.sendwrite.local.model.JobEventType
import com.frontmatic.sendwrite.ui.components.EmptyContent
import com.frontmatic.sendwrite.ui.theme.ICON_SMALL

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JobListContent(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    jobs: List<JobAndEvents>?,
    onItemClick: (JobAndEvents) -> Unit,
    emptyContentMessage: String,
    emptyContentIcon: ImageVector,
    listCellBackground: Color,
) {
    if (jobs != null && jobs.isEmpty()) {
        EmptyContent(
            icon = emptyContentIcon,
            message = emptyContentMessage
        )
    }
    if (!jobs.isNullOrEmpty()){
        Column {
            LazyColumn(
                state = lazyListState,
                modifier = modifier
                    .fadingEdge(
                        lazyListState = lazyListState
                    )
                    .background(Color.Transparent)
                    .fillMaxSize()
            ) {
                items(
                    items = jobs,
                    key = { item -> item.id.toString() }
                ) {job ->

                    ListCell(
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = tween(300)
                        ),
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
                                        fontWeight = FontWeight.SemiBold,
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
                                    tint = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
                                    modifier = Modifier.size(ICON_SMALL)
                                )
                                Text(
                                    text = it.dateToRelativeTime,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f)
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}