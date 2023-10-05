package com.frontmatic.sendwrite.ui.screen.job

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.ui.screen.job.components.AddEventAlertDialog
import com.frontmatic.sendwrite.ui.theme.MediumRadius
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
import com.pushpal.jetlime.data.JetLimeItemsModel
import com.pushpal.jetlime.data.config.IconAnimation
import com.pushpal.jetlime.data.config.JetLimeItemConfig
import com.pushpal.jetlime.data.config.JetLimeViewConfig
import com.pushpal.jetlime.data.config.LineType
import com.pushpal.jetlime.ui.JetLimeView
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalAnimationApi::class)
@Destination(
    navArgsDelegate = JobDetailsScreenNavArgs::class,
)
@Composable
fun JobDetailsScreen(
    viewModel: JobDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState(JobDetailState())

    val job = state.job

    val timelineModel = remember {
        JetLimeItemsModel(
            list = mutableListOf()
        )
    }

    if (state.showAddNewEventDialog) {
        AddEventAlertDialog(
            modifier = Modifier
                .padding(all = PAGE_PADDING)
                .fillMaxWidth(),
            state = state,
            onDismissed = {
                viewModel.toggleAddNewEventDialog(false)
            }
        )
    }


//    if (job?.applied != null) {
//        timelineModel.addItem(
//            JetLimeItemsModel.JetLimeItem(
//                title = "Applied on ${job!!.appliedDate}",
//                description = job!!.relativeAppliedTime,
//                jetLimeItemConfig = JetLimeItemConfig(
//                    titleColor = MaterialTheme.colorScheme.primary,
//                    descriptionColor = MaterialTheme.colorScheme.onSurface,
//                )
//            )
//        )
//
//        timelineModel.addItem(
//            JetLimeItemsModel.JetLimeItem(
//                title = "Waiting for application response...",
//                jetLimeItemConfig = JetLimeItemConfig(
//                    iconAnimation = IconAnimation(),
//                    titleColor = MaterialTheme.colorScheme.primary,
//                    descriptionColor = MaterialTheme.colorScheme.onSurface,
//                )
//            )
//        )
//    }

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

        if (state.job?.events?.isNotEmpty() == true) {
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
                jetLimeViewConfig = JetLimeViewConfig(
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    lineType = LineType.Solid,
                    lineColor = MaterialTheme.colorScheme.primary,
                ),
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