package com.frontmatic.sendwrite.ui.screen.archive

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SendAndArchive
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.sendwrite.ui.components.domain.JobListContent
import com.frontmatic.sendwrite.ui.components.domain.JobListTitle
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun ArchiveScreen(
    viewModel: ArchiveViewModel = hiltViewModel()
) {
    val jobs by viewModel.archivedJobs.collectAsState()

    Column {
        JobListTitle(title = "Archived Jobs")

        JobListContent(
            jobs = jobs,
            onItemClick = {},
            emptyContentMessage = "No archived jobs. Your closed job applications will be showed here.",
            emptyContentIcon = Icons.Filled.SendAndArchive
        )
    }
}