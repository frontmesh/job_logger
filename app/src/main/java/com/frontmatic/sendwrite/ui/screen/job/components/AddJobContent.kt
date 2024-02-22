package com.frontmatic.sendwrite.ui.screen.job.components

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.R
import com.frontmatic.sendwrite.local.model.Priority
import com.frontmatic.sendwrite.ui.components.OptionRow
import com.frontmatic.sendwrite.ui.theme.MediumRadius
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
import com.frontmatic.sendwrite.ui.theme.Shape
import com.kedia.ogparser.OpenGraphResult
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJobContent(
    modifier: Modifier = Modifier,
    job: OpenGraphResult?,
    topBarTitle: String,
    datePickerState: DatePickerState = rememberDatePickerState(),
    onJobSave : () -> Unit,
    onCancel: () -> Unit = {},
    priority: Priority? = null,
    onPriorityChange: (Priority) -> Unit = {},
    content : @Composable () -> Unit = {}
) {
    var applied by rememberSaveable { mutableStateOf(false) }
    val primaryContainerColor = MaterialTheme.colorScheme.primaryContainer

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = topBarTitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.imePadding()
            ) {
                Row(
                    modifier = Modifier
                        .drawBehind {
                            val borderSize = 1.dp.toPx()
                            drawLine(
                                color = primaryContainerColor,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = borderSize
                            )
                        }
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(onClick = onCancel) {
                        Text(
                            text = "Cancel",
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Button(
                        modifier = Modifier
                            .padding(end = 12.dp),
                        enabled = (job?.url?.isNotEmpty() == true && !applied) || (applied && datePickerState.selectedDateMillis != null),
                        onClick = onJobSave
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Save",
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }
    ) {

        Surface(modifier = modifier
            .fillMaxSize()
            .padding(it)) {

            Column(
                modifier = modifier
                    .padding(PAGE_PADDING)
                    .verticalScroll(rememberScrollState())
            ) {

                content()

                AnimatedVisibility(
                    visible = job != null,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {

                    if (job != null) {
                        JobCard(
                            title = job.title ?: "",
                            description = job.description ?: "",
                            imageUrl = job.image,
                            url = job.url!!,
                            isWorkshopStarred = false,
                            starPainterResource = Icons.Outlined.Star
                        )
                    }

                }

                AnimatedVisibility(
                    visible = job != null,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {

                    PriorityBar(
                        modifier = Modifier.padding(top = 10.dp),
                        selected = priority,
                        onPriorityChange
                    )
                }

                AnimatedVisibility(
                    visible = job != null,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {

                    OptionRow(
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                        label = "Application Submitted",
                        enabled = applied,
                        onToggle = { value -> applied = value }
                    )

                }

                if (applied) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false,
                        modifier = Modifier.padding(0.dp),
                        dateValidator = { timestamp ->
                            // Disable all the days after today
                            timestamp < Instant.now().toEpochMilli()
                        },
                        title = {
                            Text(
                                text = stringResource(R.string.date_hint),
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                            )
                        },
//                        headline = {
//                            Text(
//                                modifier = Modifier.padding(8.dp),
//                                text = datePickerState.displayMode.toString(),
//                                fontSize = 14.sp,
//                            )
//                        },
                    )
                }

            }
        }
    }
}


@Composable
fun PriorityBar(
    modifier: Modifier = Modifier,
    selected: Priority? = null,
    onClick: (Priority) -> Unit = {}
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(size = MediumRadius)
                )
                .clip(RoundedCornerShape(size = MediumRadius))
                .padding(horizontal = 16.dp, vertical = 8.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                text = stringResource(R.string.set_priority),
                modifier = Modifier
                    .padding(end = 6.dp)
                    .weight(1f)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Priority.values().forEach { priority ->
                    Box(
                        modifier = Modifier
                            .background(
                                shape = Shape.medium,
                                color = priority.color,
                            )
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = if (priority == selected) 1f else .0f),
                                shape = Shape.medium,
                            )
                            .height(18.dp)
                            .width(36.dp)
                            .clip(Shape.medium)
                            .clickable {
                                onClick(priority)
                            }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PriorityBarPreview() {
    PriorityBar()
}


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun AddJobContentPreview() {
    AddJobContent(
        job = OpenGraphResult(
            title = "Docklab hiring Tech Lead - HydroTwin in Rotterdam, South Holland, Netherlands | LinkedIn",
            image = "https://nl.linkedin.com/jobs/view/tech-lead-hydrotwin-at-docklab-3689393398",
            description = "Posted 2 weeks ago. Docklab is looking for a Tech Lead - HydroTwin in Rotterdam, South Holland, Netherlands. Apply…See this and similar jobs on LinkedIn.",
        ),
        onJobSave = {},
        topBarTitle = "Add a job"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AddJobContentDarkPreview() {
    AddJobContent(
        job = OpenGraphResult(
            title = "Docklab hiring Tech Lead - HydroTwin in Rotterdam, South Holland, Netherlands | LinkedIn",
            image = "https://nl.linkedin.com/jobs/view/tech-lead-hydrotwin-at-docklab-3689393398",
            description = "Posted 2 weeks ago. Docklab is looking for a Tech Lead - HydroTwin in Rotterdam, South Holland, Netherlands. Apply…See this and similar jobs on LinkedIn.",
        ),
        onJobSave = {},
        topBarTitle = "Add a job"
    )
}