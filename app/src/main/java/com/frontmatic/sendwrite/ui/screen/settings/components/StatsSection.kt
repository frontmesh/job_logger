package com.frontmatic.sendwrite.ui.screen.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.frontmatic.sendwrite.ui.screen.settings.StatsUiState

@Composable
fun StatsSection(
    statsUiState: StatsUiState
) {
    Column {
        with(statsUiState) {
            Text(text = "Jobs saved this month: $jobsSaved")
            Text(text = "Jobs applied this month: $jobsApplied")
            Text(text = "Interviews scheduled this month: $interviewsScheduled")
            Text(text = "Assignments scheduled this month: $assignmentsScheduled")
            Text(text = "Misc. events scheduled this month: $otherEventsScheduled")
        }
    }

}