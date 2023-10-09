package com.frontmatic.sendwrite.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.WorkOff
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.ui.components.CoilAsyncImage
import com.frontmatic.sendwrite.ui.components.EmptyContent
import com.frontmatic.sendwrite.ui.components.domain.JobListContent
import com.frontmatic.sendwrite.ui.components.domain.JobListTitle
import com.frontmatic.sendwrite.ui.screen.destinations.JobDetailsScreenDestination
import com.frontmatic.sendwrite.ui.theme.AlphaDisabled
import com.frontmatic.sendwrite.ui.theme.AlphaHigh
import com.frontmatic.sendwrite.ui.theme.MediumRadius
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination(
    route = "home",
    start = true
)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val jobs by viewModel.jobs.collectAsState(null)

    Column {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                JobCell(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    taskCount = "0",
                    title = "Due Today",
                    icon = Icons.Filled.DateRange,
                    onClick = { /*TODO*/ },
                )
                JobCell(
                    modifier = Modifier
                        .weight(1f),
                    taskCount = "0",
                    title = "Due This Week",
                    icon = Icons.Filled.DateRange,
                    onClick = { /*TODO*/ },
                )
            }

            JobListTitle(
                title = "All Jobs",
                description = "You have ${jobs?.size ?: 0} saved jobs",
            )
        }

        JobListContent(
            jobs = jobs,
            onItemClick = { item ->
                navigator.navigate(JobDetailsScreenDestination(item.id!!))
            },
            emptyContentMessage = "No Jobs Found. Log your job application on a button below or share a link to this app.",
            emptyContentIcon = Icons.Filled.WorkOff,
        )
    }

}


@Composable
private fun JobCell(
    modifier: Modifier,
    taskCount: String,
    title: String,
    icon: ImageVector,
    iconText: String = "",
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(size = MediumRadius)
    Surface(
        modifier = modifier
            .clip(shape)
            .clickable(onClick = onClick),
        shape = shape,
        color = MaterialTheme.colorScheme.primaryContainer

    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .background(
                            shape = CircleShape,
                            color =
                            MaterialTheme.colorScheme.background
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = AlphaHigh)

                    )

                    if (iconText.isNotBlank()) {
                        Text(
                            text = iconText,
                            fontSize = 8.sp,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                            modifier = Modifier.padding(top = 2.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Text(
                    text = taskCount,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(8.dp))
            Row (modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)){
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Row {
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = MaterialTheme.colorScheme.secondary.copy(alpha = AlphaDisabled))
                )
                Divider(color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}