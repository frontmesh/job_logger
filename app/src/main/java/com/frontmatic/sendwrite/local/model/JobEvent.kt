package com.frontmatic.sendwrite.local.model

import android.text.format.DateUtils
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date

@Entity(
    tableName = "job_events",
    foreignKeys = [
        ForeignKey(
            entity = Job::class,
            parentColumns = ["id"],
            childColumns = ["job_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index("job_id")
    ]
)
data class JobEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo(name = "job_id")
    val jobId: Long,
    val name: String? = null,
    val description: String? = null,
    val date: Date? = null,
    val url : String?,
    val type: JobEventType,
) {
    // format date as DD-MM-YYYY

    val formatDate: String?
        get() = date?.let {
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            sdf.format(it)
        }

    val dateToRelativeTime: String
        get() = date?.let {
            DateUtils.getRelativeTimeSpanString(
                it.time,
                System.currentTimeMillis(),
                0L,
                DateUtils.FORMAT_ABBREV_ALL
            ).toString()
        } ?: ""
}


enum class JobEventType {
    APPLIED,
    INTERVIEW,
    ASSIGNMENT,
    OFFER,
    OTHER
}