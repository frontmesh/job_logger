package com.frontmatic.sendwrite.local.model

import android.text.format.DateUtils
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
            val pattern = when (type) {
                JobEventType.APPLIED -> "dd-MM-yyyy"
                JobEventType.INTERVIEW -> "dd-MM-yyyy HH:mm"
                JobEventType.ASSIGNMENT -> "dd-MM-yyyy HH:mm"
                JobEventType.OFFER -> "dd-MM-yyyy"
                JobEventType.OTHER -> "dd-MM-yyyy"
            }

            val sdf = SimpleDateFormat(pattern, Locale.getDefault())
            sdf.format(it)
        }

    val dateToRelativeTime: String
        get() = date?.let {
            val currentMillis = System.currentTimeMillis()
            val timeDifference = currentMillis - it.time

            val days = timeDifference / DateUtils.DAY_IN_MILLIS
            val hours = timeDifference / DateUtils.HOUR_IN_MILLIS
            val minutes = timeDifference / DateUtils.MINUTE_IN_MILLIS
            val seconds = timeDifference / DateUtils.SECOND_IN_MILLIS
            val weeks = timeDifference / DateUtils.WEEK_IN_MILLIS

            when {
                weeks > 1L -> "$weeks weeks ago"
                weeks == 1L -> "A week ago"
                days > 1 -> "$days days ago"
                days == 1L -> "Yesterday"
                hours > 1 -> "$hours hours ago"
                hours == 1L -> "An hour ago"
                minutes > 1 -> "$minutes minutes ago"
                minutes == 1L -> "A minute ago"
                seconds > 1 -> "$seconds seconds ago"
                seconds == 1L -> "A second ago"
                else -> "Just now"
            }

        } ?: ""

    val typeToString: String
        get() = type.name.lowercase().replaceFirstChar { it.uppercase() }
}


enum class JobEventType {
    APPLIED,
    INTERVIEW,
    ASSIGNMENT,
    OFFER,
    OTHER
}