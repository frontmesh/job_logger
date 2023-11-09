package com.frontmatic.sendwrite.ui.screen.archive

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SendAndArchive
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.ui.components.domain.JobListContent
import com.frontmatic.sendwrite.ui.components.domain.JobListTitle
import com.frontmatic.sendwrite.ui.screen.destinations.JobDetailsScreenDestination
import com.frontmatic.sendwrite.ui.screen.styles.DefaultTransitions
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(
    style = DefaultTransitions::class,
)
@Composable
fun ArchiveScreen(
    navigator: DestinationsNavigator,
    viewModel: ArchiveViewModel = hiltViewModel()
) {
    val jobs by viewModel.archivedJobs.collectAsState()

    Column {
        JobListTitle(
            title = "Archived Jobs",
            description = "You have ${jobs?.size ?: 0} archived jobs",
        )

        var emptyContentMessage = """
            No archived jobs. 
            Your closed job applications will be showed here.
        """.trimIndent()

        JobListContent(
            jobs = jobs,
            listCellBackground = MaterialTheme.colorScheme.tertiaryContainer.copy(
                alpha = 0.35f
            ),
            onItemClick = { item ->
                navigator.navigate(
                    JobDetailsScreenDestination(
                        item.id!!
                    )
                )
            },
            emptyContentMessage = emptyContentMessage,
            emptyContentIcon = Icons.Filled.SendAndArchive
        )
    }
}