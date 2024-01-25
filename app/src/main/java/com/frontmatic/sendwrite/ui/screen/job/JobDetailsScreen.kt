package com.frontmatic.sendwrite.ui.screen.job

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.ext.fadingEdge
import com.frontmatic.sendwrite.local.model.JobEvent
import com.frontmatic.sendwrite.local.model.JobEventType
import com.frontmatic.sendwrite.ui.components.EmptyContent
import com.frontmatic.sendwrite.ui.components.timeline.CircleParametersDefaults
import com.frontmatic.sendwrite.ui.components.timeline.LineParametersDefaults
import com.frontmatic.sendwrite.ui.components.timeline.MessageBubble
import com.frontmatic.sendwrite.ui.components.timeline.StrokeParameters
import com.frontmatic.sendwrite.ui.components.timeline.TimelineNode
import com.frontmatic.sendwrite.ui.components.timeline.TimelineNodePosition
import com.frontmatic.sendwrite.ui.screen.NavGraphs
import com.frontmatic.sendwrite.ui.screen.destinations.ArchiveScreenDestination
import com.frontmatic.sendwrite.ui.screen.job.components.AddEventAlertDialog
import com.frontmatic.sendwrite.ui.screen.job.components.MessageAlertDialog
import com.frontmatic.sendwrite.ui.screen.styles.DefaultTransitions
import com.frontmatic.sendwrite.ui.theme.AlphaHigh
import com.frontmatic.sendwrite.ui.theme.AlphaMedium
import com.frontmatic.sendwrite.ui.theme.ChipBackground
import com.frontmatic.sendwrite.ui.theme.ChipOnBackground
import com.frontmatic.sendwrite.ui.theme.MediumRadius
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
import com.frontmatic.sendwrite.ui.theme.Shape
import com.frontmatic.sendwrite.util.JobEventUtility.getIconForJobEvent
import com.frontmatic.sendwrite.util.JobEventUtility.getTitleForJobEventItem
import com.frontmatic.sendwrite.util.LocalSnackbar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Destination(
    navArgsDelegate = JobDetailsScreenNavArgs::class,
    style = DefaultTransitions::class,
)
@Composable
fun JobDetailsScreen(
    navigator: DestinationsNavigator,
    viewModel: JobDetailsViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsState(JobDetailState())
    val snack = LocalSnackbar.current
    val job = state.job
    val uriHandler = LocalUriHandler.current
    val lazyListState = rememberLazyListState()

    LaunchedEffect(state.snackBarState) {
        if (state.snackBarState.showSnackbar) {
            snack.showSnackbar(
                message = state.snackBarState.message,
            )
            viewModel.resetSnackBar()
        }
    }

    LaunchedEffect(job?.events?.size) {
        job?.events?.size?.minus(1)?.let {
            if (it >= 0) lazyListState.scrollToItem(it)
        }
    }

    if (state.showAddNewEventDialog) {
        AddEventAlertDialog(
            modifier = Modifier
                .padding(all = PAGE_PADDING)
                .fillMaxWidth(),
            state = state,
            onEventAction = viewModel::onAddEventAlertAction,
            onDismissed = {
                viewModel.toggleAddNewEventDialog(false)
            }
        )
    }

    if (state.showRemoveEventDialog) {
        MessageAlertDialog(
            message = "Are you sure you want to remove this event?",
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.removeEvent()
                    },
                ) {
                    Text(text = "Remove")
                }
            },
            dismissButton = {
                Box {
                    OutlinedButton(
                        onClick = { viewModel.toggleRemoveEventDialog(false) },
                    ) {
                        Text("Cancel")
                    }
                }
            },
            onDismissRequest = {
                viewModel.toggleRemoveEventDialog(false)
            },
        )
    }

    if (state.showDeleteJobApplicationDialog) {
        MessageAlertDialog(
            message = "Are you sure you want to delete your job application?",
            confirmButton = { Button(
                onClick = {
                    viewModel.deleteJob()
                    navigator.popBackStack()
                },
            ) {
                Text("Remove")
            }},
            dismissButton = {
                OutlinedButton(
                    onClick = { viewModel.toggleDeleteJobApplicationDialog(false)  }
                ) {
                    Text("Cancel")
                }
            },
            onDismissRequest = { viewModel.toggleDeleteJobApplicationDialog(false) },
        )

    }

    if (state.showCloseJobApplicationDialog) {
        MessageAlertDialog(
            message = "Are you sure you want to close this job application?",
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.archiveJob()
                        navigator.navigate(ArchiveScreenDestination()) {
                            popUpTo(NavGraphs.root) {
                                saveState = false
                            }
                            restoreState = false
                        }
                    },
                ) {
                    Text("Close")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { viewModel.toggleCloseJobApplicationDialog(false) },
                ) {
                    Text("Cancel")
                }
            },
            onDismissRequest = {
                viewModel.toggleCloseJobApplicationDialog(false)
            },
        )
    }

    Column {
        JobDetailsCard(
            text = job?.description ?: "",
            onClick = {
                job?.url?.let { uriHandler.openUri(it) }
            },
            modifier = Modifier
                .padding(PAGE_PADDING)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                    shape = Shape.medium,
                )
        )

        if (job != null && job.events.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .padding(all = PAGE_PADDING)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "Job Timeline",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }

            state.job?.let {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .padding(all = PAGE_PADDING)
                        .fadingEdge(lazyListState = lazyListState)
                        .background(Color.Transparent)
                        .fillMaxSize()
                ) {
                    items(
                        it.events.size,
                    ) { index ->
                        val item = it.events[index]

                        val isLast =
                            it.events.size.minus(1) == index || it.events.size == 1
                        val isFirst = index == 0 && it.events.size != 1

                        TimelineNode(
                            position = when {
                                isFirst -> TimelineNodePosition.FIRST
                                isLast -> TimelineNodePosition.LAST
                                else -> TimelineNodePosition.MIDDLE
                            },
                            icon = getIconForJobEvent(item.type),
                            iconColor = MaterialTheme.colorScheme.primary,
                            circleParameters = CircleParametersDefaults.circleParameters(
                                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                                stroke = StrokeParameters(
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = if (isLast) 1f else .35f),
                                    width = 3.dp
                                )
                            ),
                            lineParameters =  LineParametersDefaults.solidColor(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = .35f)
                            ),
                        ) { modifier ->
                            MessageBubbleContent(
                                modifier = modifier,
                                item = item,
                                isLast = isLast,
                                onEventRemove = viewModel::handleEventRemoveButton
                            )
                        }
                    }
                }
            }
        } else if (state.job?.events?.isEmpty() == true) {
            EmptyContent(
                icon = Icons.Rounded.Event,
                message = "This job application doesn't have any events logged. \n" +
                        " \n" +
                        if (state.job?.archived != true) {
                            "Log your job application progress on a button below."
                        } else {
                            "This job application is closed."
                       },
            )
        }

    }
}


@Composable
fun JobDetailsCard(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(size = MediumRadius)

    Surface(
        modifier = modifier
            .clip(shape)
            .clickable(onClick = onClick),
        shape = shape,
        color = MaterialTheme.colorScheme.surface

    ) {
        Row(
            modifier = Modifier.padding(PAGE_PADDING),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 10,
            )
            Box(
                modifier = Modifier
                    .weight(0.1f)
                    .background(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.background
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Link,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

        }
    }
}

@Composable
fun MessageBubbleContent(
    modifier: Modifier = Modifier,
    item: JobEvent,
    isLast: Boolean,
    onEventRemove : (JobEvent) -> Unit = {},
) {
    MessageBubble(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = if (isLast) .85f else .0f),
                shape = Shape.medium,
            ),
    ) {
        Column(
            modifier = Modifier
                .padding(all=12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = getTitleForJobEventItem(item),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                IconButton(
                    modifier = Modifier.size(20.dp),
                    onClick = { onEventRemove(item) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Event delete icon",
                        tint = if (isLast) MaterialTheme.colorScheme.secondary
                            else MaterialTheme.colorScheme.onSurface.copy(AlphaMedium),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            if (item.description?.isNotEmpty() == true) {
                Row {
                    Text(
                        text = item.description,
                        color = MaterialTheme.colorScheme.onSurface.copy(AlphaHigh),
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            item.formatFullDate?.let {
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    verticalAlignment = Alignment.Top,
                ) {
                    ChipContent(text = it, selected = isLast)
                }
            }
        }
    }
}

@Composable
fun ChipContent(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    icon: Boolean = true,
) {
    val primaryTint = when {
        selected -> ChipOnBackground
        else ->  MaterialTheme.colorScheme.onSurface.copy(alpha = AlphaMedium)
    }

    Surface(
        color = when {
            selected -> ChipBackground
            else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        },
        contentColor = when {
            selected -> ChipBackground
            else -> MaterialTheme.colorScheme.onSurface
        },
        shape = CircleShape,
        modifier = modifier
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 5.dp
            )
        ){
            if (icon) {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    tint = primaryTint,
                    contentDescription = "Due date icon",
                    modifier = Modifier
                        .size(20.dp)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = primaryTint,
            )
        }
    }
}

@Preview
@Composable
fun TimeChipContentPreview() {
    ChipContent(text = "14 days ago", selected = true)
}

@Preview(showBackground = true)
@Composable
fun TimeChipContentUnselectedPreview() {
    ChipContent(text = "26-01-2024", selected = false)
}


@Preview
@Composable
fun MessageBubbleContentPreview() {
    MessageBubbleContent(
        isLast = true,
        item = JobEvent(
            jobId = 1,
            name = "Job title",
            description = "Job description",
            type = JobEventType.INTERVIEW,
            date = Date(1650000000000),
            url = "https://www.some-fake-job-board.com/some-fake-job-id",
        )
    )
}

@Preview
@Composable
fun MessageBubbleContentEmptyPreview() {
    MessageBubbleContent(
        isLast = false,
        item = JobEvent(
            jobId = 1,
            name = "Job title",
            description = null,
            type = JobEventType.OTHER,
            date = null,
            url = "https://www.some-fake-job-board.com/some-fake-job-id",
        )
    )
}
