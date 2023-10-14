package com.frontmatic.sendwrite.ui.screen.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.ui.components.domain.ListCell
import com.frontmatic.sendwrite.ui.screen.destinations.JobDetailsScreenDestination
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
import com.frontmatic.sendwrite.util.JobEventUtility.getIconAndDataForJobEventType
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination()
@Composable()
fun EventListScreen(
    navigator: DestinationsNavigator,
    viewModel: EventListViewModel = hiltViewModel()
) {
    val events by viewModel.eventList.collectAsState(emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = PAGE_PADDING)
    ) {
        LazyColumn() {
            items(
                items = events,
                key = { event -> event.id!! }
            ) { event ->
                var (icon, name) = getIconAndDataForJobEventType(event)
                ListCell(
                    name = name,
                    icon = icon,
                    trailingText = event.formatDate,
                    onClick = {
                        navigator.navigate(
                            JobDetailsScreenDestination(
                                event.jobId
                            )
                        )
                    }
                )
            }
        }
    }
}