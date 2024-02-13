package com.frontmatic.sendwrite.ui.screen.settings.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatsSection(
    viewModel: SettingsChildViewModel
) {
    val statsUiState by viewModel.statsUiState.collectAsState()
    val selectedMonth by viewModel.selectedMonth.collectAsState()
    val monthName = getMonthName(selectedMonth)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(
                onClick = { viewModel.updateSelectedMonth(selectedMonth + 1) },
                enabled = selectedMonth < 3
            ) {
                Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = null)
            }
             Text(
                text = monthName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                onClick = { viewModel.updateSelectedMonth(selectedMonth - 1) },
                enabled = selectedMonth > 0
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
            val monthNameLower = monthName.lowercase()
            Column(
                modifier = Modifier
                    .animateContentSize()
                    .padding(start = 16.dp)
            ) {
                Row {
                    Text(jobsSaved.toString(), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "jobs saved in $monthNameLower")
                }
                Row {
                    Text(text = jobsApplied.toString(), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "jobs applied in $monthNameLower")
                }
                Row {
                    Text(text = interviewsScheduled.toString(), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "interviews scheduled in $monthNameLower")
                }
                Row {
                    Text(text = assignmentsScheduled.toString(), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "Assignments scheduled in $monthNameLower")
                }
                Row {
                    Text(text = offersReceived.toString(), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "Assignments scheduled in $monthNameLower")
                }
                Row {
                    Text(text = otherEventsScheduled.toString(), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "misc. events scheduled in $monthNameLower")
                }
            }
        }
    }

}