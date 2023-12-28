package com.frontmatic.sendwrite.ui.screen.job.components

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.ui.components.OptionRow
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
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
                        modifier = Modifier.padding(8.dp)
                    )
                },
            )
        },
        bottomBar = {
            NavigationBar {
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

                if (job?.url?.isNotEmpty() == true) {
                    JobCard(
                        title = job.title ?: "",
                        description = job.description ?: "",
                        imageUrl = job.image ?: "",
                        isWorkshopStarred = false,
                        starPainterResource = Icons.Outlined.Star
                    )

                    OptionRow(
                        modifier = Modifier.padding(top = 16.dp),
                        label = "Applied",
                        enabled = applied,
                        onToggle = { value -> applied = value }
                    )

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
                                    text = "Pick a date you have applied to this job",
                                    modifier = Modifier.padding(24.dp),
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