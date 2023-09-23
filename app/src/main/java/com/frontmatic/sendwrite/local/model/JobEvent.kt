package com.frontmatic.sendwrite.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "job_events",
    foreignKeys = [
        ForeignKey(
            entity = Job::class,
            parentColumns = ["id"],
            childColumns = ["jobId"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index("jobId")
    ]
)
data class JobEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val jobId: Long,
    val name: String,
    val description: String,
)
