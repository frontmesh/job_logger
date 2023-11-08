package com.frontmatic.sendwrite.ui.screen.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.ui.components.EmptyContent
import com.frontmatic.sendwrite.ui.components.domain.ListCell
import com.frontmatic.sendwrite.ui.screen.destinations.JobDetailsScreenDestination
import com.frontmatic.sendwrite.ui.screen.styles.DefaultTransitions
import com.frontmatic.sendwrite.ui.theme.PAGE_PADDING
import com.frontmatic.sendwrite.util.JobEventUtility.getIconAndDataForJobEventType
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination(
    navArgsDelegate = EventListScreenNavArgs::class,
    style = DefaultTransitions::class,
)
@Composable()
fun EventListScreen(
    navigator: DestinationsNavigator,
    viewModel: EventListViewModel = hiltViewModel()
) {
    val events by viewModel.eventList.collectAsState(emptyList())
    val period = viewModel.period.toString().lowercase()

    if (events != null && events.isEmpty()) {
        EmptyContent(
            icon = Icons.Rounded.DateRange,
            message = "No events found for ${if (period == "today") period else "following $period"}"
        )
    } else {
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
                    val (icon, name) = getIconAndDataForJobEventType(event)
                    ListCell(
                        name = name,
                        icon = icon,
                        trailingContent = {
                          Column(
                              verticalArrangement = Arrangement.spacedBy(4.dp)
                          ) {
                              event.formatDate?.let {
                                  Row(
                                      verticalAlignment = Alignment.CenterVertically,
                                      horizontalArrangement = Arrangement.spacedBy(4.dp)
                                  ) {
                                      Icon(
                                          imageVector = Icons.Rounded.DateRange,
                                          contentDescription = "Date",
                                          tint = MaterialTheme.colorScheme.primary
                                      )
                                      Text(
                                          text = it,
                                          style = MaterialTheme.typography.bodyMedium,
                                          color = MaterialTheme.colorScheme.tertiary,
//                                          fontWeight = FontWeight.Medium,
                                          maxLines = 1,
                                          overflow = TextOverflow.Ellipsis
                                      )
                                  }
                              }

                              event.formatTime?.let {
                                  Row(
                                      verticalAlignment = Alignment.CenterVertically,
                                      horizontalArrangement = Arrangement.spacedBy(4.dp)
                                  ) {
                                      Icon(
                                          imageVector = Icons.Rounded.AccessTime,
                                          contentDescription = "Time",
                                          tint = MaterialTheme.colorScheme.primary
                                      )
                                      Text(
                                          text = it,
                                          style = MaterialTheme.typography.bodyMedium,
                                          color = MaterialTheme.colorScheme.tertiary,
//                                          fontWeight = FontWeight.Medium,
                                          maxLines = 1,
                                          overflow = TextOverflow.Ellipsis
                                      )
                                  }
                              }
                          }
                        },
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
}