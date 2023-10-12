package com.frontmatic.sendwrite.ui.screen.job

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.MainActivity
import com.frontmatic.sendwrite.R
import com.frontmatic.sendwrite.local.mapper.toJob
import com.frontmatic.sendwrite.ui.components.CoilAsyncImage
import com.frontmatic.sendwrite.ui.components.OptionData
import com.frontmatic.sendwrite.ui.components.OptionRow
import com.frontmatic.sendwrite.ui.theme.MediumRadius
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
import com.kedia.ogparser.OpenGraphResult
import com.ramcosta.composedestinations.annotation.Destination
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Destination(
    route = "add-job",
)
@Composable
fun AddJobIntentScreen(
    intentJob: OpenGraphResult?,
    viewModel: AddJobViewModel = hiltViewModel(),
) {
    val job by remember { mutableStateOf(intentJob) }
    val topBarTitle = job?.title?.takeIf {
        it.isNotBlank() }?.let { stringResource(R.string.add_a_job_var, it)
    } ?: stringResource(R.string.add_a_job)
    val datePickerState = rememberDatePickerState()

    AddJobContent(
        job = job,
        topBarTitle = topBarTitle,
        onJobSave = {
            viewModel.saveJob(job?.toJob(), datePickerState.selectedDateMillis)
        },
        datePickerState = datePickerState
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJobContent(
    modifier: Modifier = Modifier,
    job: OpenGraphResult?,
    topBarTitle: String,
    datePickerState: DatePickerState = rememberDatePickerState(),
    onJobSave : () -> Unit,
) {
    var applied by remember { mutableStateOf(false) }
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
                val context = LocalContext.current as Activity
                OutlinedButton(onClick = {
                    context.startActivity(Intent(context, MainActivity::class.java))
                    context.finish()
                }) {
                    Text(
                        text = "Cancel",
                        fontWeight = FontWeight.Bold,
                    )
                }

                Button(
                    modifier = Modifier
                        .padding(end = 12.dp),
                    enabled = applied && datePickerState.selectedDateMillis != null || !applied,
                    onClick = {
                        onJobSave()
                        context.startActivity(Intent(context, MainActivity::class.java))
                        context.finish()
                    }
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
    ) {
        Surface(modifier = modifier.padding(it)) {
            Column(modifier = modifier
                .padding(PAGE_PADDING)
            ) {
                JobCard(
                    title = job?.title ?: "",
                    description = job?.description ?: "",
                    imageUrl = job?.image ?: "",
                    isWorkshopStarred = false,
                    starPainterResource = Icons.Outlined.Star
                )

                OptionRow(
                    setting = OptionData(
                        "Applied",
                        "applied",
                        false
                    )
                ) { value -> applied = value }

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


@Composable
fun JobCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    imageUrl: String,
    isWorkshopStarred: Boolean = false,
    starPainterResource: ImageVector = Icons.Outlined.Star
) {
    Row(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(5)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {

        CoilAsyncImage(
            imageUrl = imageUrl,
            imageShape = RoundedCornerShape(MediumRadius),
            imageSize = 42.dp
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 16.dp),
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(start = 16.dp),
                fontSize = 14.sp
            )

            if (isWorkshopStarred) {
                Icon(
                    imageVector = starPainterResource,
                    modifier = modifier.size(18.dp),
                    contentDescription = "star",
                    tint = Color(0xFFFFEB3B)
                )
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
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
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

@Preview
@Composable
fun JobCardPreview() {
    JobCard(
        modifier = Modifier
            .padding(vertical = 6.dp, horizontal = 16.dp),
        title = "title",
        description = "description",
        imageUrl = "https://www.mindinventory.com/blog/wp-content/uploads/2018/05/android-jetpack.jpg",
        isWorkshopStarred = false,
        starPainterResource = Icons.Outlined.Star
    )
}