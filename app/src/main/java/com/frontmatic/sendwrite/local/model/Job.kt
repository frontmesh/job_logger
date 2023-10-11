package com.frontmatic.sendwrite.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jobs")
data class Job(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String,
    val archived: Boolean,
) 
