package com.frontmatic.sendwrite.local.model

import android.text.format.DateUtils
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date

@Entity(tableName = "jobs")
data class Job(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String,
    val applied: Date? = null,
    val archived: Boolean,
) {

    // format date as DD-MM-YYYY

    val appliedDate: String?
        get() = applied?.let {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        sdf.format(it)
    }

    val relativeAppliedTime: String
        get() = applied?.let {
            DateUtils.getRelativeTimeSpanString(
                it.time,
                System.currentTimeMillis(),
                0L,
                DateUtils.FORMAT_ABBREV_ALL
            ).toString()
        } ?: "Not applied yet"

}
