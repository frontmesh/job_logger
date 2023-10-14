package com.frontmatic.sendwrite.ui.components.domain

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.frontmatic.sendwrite.local.model.Job
import com.frontmatic.sendwrite.ui.components.EmptyContent

@Composable
fun JobListContent(
    jobs: List<Job>?,
    onItemClick: (Job) -> Unit,
    emptyContentMessage: String,
    emptyContentIcon: ImageVector,
    listCellBackground: Color
) {
    if (jobs != null && jobs.isEmpty()) {
        EmptyContent(
            icon = emptyContentIcon,
            message = emptyContentMessage
        )
    }
    if (!jobs.isNullOrEmpty()){

        Column {
            LazyColumn() {
                items(
                    items = jobs!!,
                    key = { item -> item.id.toString() }
                ) {
                    ListCell(
                        name = it.title,
                        imageUrl = it.imageUrl,
//                        trailingText = it..size,
                        listCellBackground = listCellBackground,
                        onClick = {
                            onItemClick(it)
                        }
                    )
                }
            }
        }
    }
}