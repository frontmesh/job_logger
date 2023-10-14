package com.frontmatic.sendwrite.ui.screen.job

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Approval
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.EventBusy
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.local.model.JobEventType
import com.frontmatic.sendwrite.ui.components.EmptyContent
import com.frontmatic.sendwrite.ui.screen.NavGraphs
import com.frontmatic.sendwrite.ui.screen.destinations.ArchiveScreenDestination
import com.frontmatic.sendwrite.ui.screen.job.components.AddEventAlertDialog
import com.frontmatic.sendwrite.ui.screen.job.components.MessageAlertDialog
import com.frontmatic.sendwrite.ui.theme.MediumRadius
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
import com.frontmatic.sendwrite.util.JobEventUtility.getIconAndDataForJobEventType
import com.frontmatic.sendwrite.util.LocalSnackbar
import com.pushpal.jetlime.data.JetLimeItemsModel
import com.pushpal.jetlime.data.config.IconAnimation
import com.pushpal.jetlime.data.config.IconType
import com.pushpal.jetlime.data.config.JetLimeItemConfig
import com.pushpal.jetlime.data.config.JetLimeViewConfig
import com.pushpal.jetlime.data.config.LineType
import com.pushpal.jetlime.ui.JetLimeView
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@Destination(
    navArgsDelegate = JobDetailsScreenNavArgs::class,
)
@Composable
fun JobDetailsScreen(
    navigator: DestinationsNavigator,
    viewModel: JobDetailsViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsState(JobDetailState())
    val snack = LocalSnackbar.current
    val job = state.job
    val colorScheme = MaterialTheme.colorScheme
    val jetLimeListState = rememberLazyListState()

    LaunchedEffect(state.snackBarState) {
        if (state.snackBarState.showSnackbar) {
            snack.showSnackbar(
                message = state.snackBarState!!.message,
            )

            viewModel.resetSnackBar()
        }
    }

    val timelineModel by remember {
        derivedStateOf {
            val jetItemList: MutableList<JetLimeItemsModel.JetLimeItem> = mutableStateListOf()
            state.job?.events?.forEachIndexed { index, it ->
                val (icon, description) = getIconAndDataForJobEventType(it)
                val isLast = state.job?.events?.size?.minus(1) == index

                jetItemList.add(
                    createJetLimeItem(
                        title = formatEventTitle(it.typeToString, it.formatDate, it.name),
                        description = description,
                        icon = icon,
                        colorScheme = colorScheme,
                        isLast = isLast
                    )
                )

            }
            JetLimeItemsModel(jetItemList)
        }
    }


    LaunchedEffect(timelineModel.items.size) {
        delay(1)
        jetLimeListState.scrollToItem(timelineModel.items.size)
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
            message = "Are you sure you want to remove the last event?",
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.removeLastEvent()
                    },
                ) {
                    Text(text = "Remove")
                }
            },
            dismissButton = {
                Box(
                ) {
                    OutlinedButton(
                        onClick = { viewModel.toggleRemoveLastEventDialog(false) },
                    ) {
                        Text("Cancel",)
                    }
                }
            },
            onDismissRequest = {
                viewModel.toggleRemoveLastEventDialog(false)
            },
        )
    }

    if (state.showDeleteJobApplicationDialog) {
        MessageAlertDialog(
            message = "Are you sure you want to delete your job application?",
            confirmButton = { Button(
                onClick = {
                    viewModel?.deleteJob()
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

    Column(
    ) {
        val uriHandler = LocalUriHandler.current

        JobDetailsCard(
            text = job?.description ?: "",
            onClick = {
                job?.url?.let { uriHandler.openUri(it) }
            },
            modifier = Modifier.padding(PAGE_PADDING)
        )

        if (state.job?.events?.isNullOrEmpty() == false) {
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
            JetLimeView(
                jetLimeItemsModel = timelineModel,
                listState = jetLimeListState,
                jetLimeViewConfig = JetLimeViewConfig(
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    lineColor = MaterialTheme.colorScheme.primary,
                    showIcons = true,
                    lineType = LineType.Solid,
                    lineThickness = 16F,
                    iconBorderThickness = 1.dp,
                    iconSize = 22.dp,
                    iconShape = CircleShape,
                ),
            )
        } else if (state.job?.events?.isNullOrEmpty() == true) {
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

fun formatEventTitle(eventType: String, date: String?, title: String?): String {
    return  if (date != null) "$eventType on $date" else "$eventType - $title"
}

fun createJetLimeItem(
    title: String, description:String,
    icon: ImageVector,
    colorScheme: ColorScheme,
    isLast : Boolean = false
): JetLimeItemsModel.JetLimeItem {
    return JetLimeItemsModel.JetLimeItem(
        title = title,
        description = description,
        jetLimeItemConfig = JetLimeItemConfig(
            iconType = IconType.Custom(
                iconImage = icon,
            ),
            iconColor = if (isLast) colorScheme.tertiary else colorScheme.primary,
            iconBackgroundColor = if (isLast) colorScheme.tertiaryContainer
             else colorScheme.primaryContainer,
            titleColor = colorScheme.primary,
            descriptionColor = colorScheme.onSurface,
            iconAnimation = if (isLast) IconAnimation() else null,
        )
    )
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
        color = MaterialTheme.colorScheme.primaryContainer

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
                modifier = Modifier.weight(1f)
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