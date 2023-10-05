package com.frontmatic.sendwrite.local.model

import androidx.room.Relation

data class JobAndEvents(
    val id : Long,
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String,
    val archived: Boolean,

    @Relation(
        parentColumn = "id",
        entityColumn = "job_id"
    )
    val events: List<JobEvent> = emptyList(),
)