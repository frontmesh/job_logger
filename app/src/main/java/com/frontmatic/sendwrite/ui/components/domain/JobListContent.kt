package com.frontmatic.sendwrite.ui.components.domain

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
                        onClick = {
                            onItemClick(job)
                        },
                        secondaryContent = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 4.dp)

                            ) {
                                job.events.find { jobEvent ->
                                    jobEvent.type == JobEventType.APPLIED
                                }?.let {
                                    Icon(
                                        imageVector = Icons.Filled.AccessTime,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .size(ICON_SMALL)
                                            .padding(end = 4.dp)
                                    )
                                    Text(
                                        text = it.dateToRelativeTime,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    // dot separator
                                    Text(
                                        text = " • ",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Filled.Event,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .size(ICON_SMALL)
                                        .padding(end = 4.dp)
                                )
                                Text(
                                    text = job.events.size.toString() + " events",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}