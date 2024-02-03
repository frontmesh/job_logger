package com.frontmatic.sendwrite.local.model

import androidx.room.Relation
import javax.annotation.Nullable

data class JobAndEvents(
    val id : Long,
    val title: String,

    @Nullable
    val description: String?,

    @Nullable
    val imageUrl: String?,

    val archived: Boolean,
    val priority: Priority?,
    val url: String,

    @Relation(
        parentColumn = "id",
        entityColumn = "job_id"
    )
    val events: List<JobEvent> = emptyList(),
)