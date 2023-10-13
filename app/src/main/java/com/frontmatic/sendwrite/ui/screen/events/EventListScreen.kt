package com.frontmatic.sendwrite.ui.screen.events

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination


@Destination()
@Composable()
fun EventListScreen(
    viewModel: EventListViewModel = hiltViewModel()
) {
    val events by viewModel.eventList.collectAsState(emptyList())
    LazyColumn() {
        items(
            items = events,
            key = { event -> event.id!! }
        ) { event ->
            event.name?.let { Text(text = it) }
        }
    }
}