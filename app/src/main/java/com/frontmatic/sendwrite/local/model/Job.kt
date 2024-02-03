package com.frontmatic.sendwrite.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nullable

@Entity(tableName = "jobs")
data class Job(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val title: String,

    @Nullable
    val description: String?,

    val url: String,

    @Nullable
    val imageUrl: String?,
    val archived: Boolean,

    val priority: Priority?,

    @ColumnInfo(name = "created_at") var createdAt: Long?,
    @ColumnInfo(name = "modified_at") var modifiedAt: Long?
)


enum class Priority {
    HIGH,
    MEDIUM,
    LOW
}