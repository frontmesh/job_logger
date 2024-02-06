package com.frontmatic.sendwrite.ui.screen.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.frontmatic.sendwrite.ui.components.BarChart
import com.frontmatic.sendwrite.ui.screen.settings.SettingsChildViewModel
import com.frontmatic.sendwrite.ui.screen.settings.toPairs
import com.frontmatic.sendwrite.util.getMonthName

@Composable
fun StatsSection(
    viewModel: SettingsChildViewModel
) {
    val statsUiState by viewModel.statsUiState.collectAsState()
    val selectedMonth by viewModel.selectedMonth.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(
                onClick = { viewModel.updateSelectedMonth(selectedMonth - 1) },
                enabled = selectedMonth > -2
            ) {
                Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = null)
            }
             Text(
                text = getMonthName(selectedMonth),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                onClick = { viewModel.updateSelectedMonth(selectedMonth + 1) },
                enabled = selectedMonth < 0
            ) {
                Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = null)
            }
        }

        BarChart(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .background(MaterialTheme.colorScheme.background),
            data = statsUiState.toPairs(),
            chartHeight = 200.dp
        )

        with(statsUiState) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
            ) {
                Text(text = "Jobs saved this month: $jobsSaved")
                Text(text = "Jobs applied this month: $jobsApplied")
                Text(text = "Interviews scheduled this month: $interviewsScheduled")
                Text(text = "Assignments scheduled this month: $assignmentsScheduled")
                Text(text = "Misc. events scheduled this month: $otherEventsScheduled")
            }
        }
    }

}