package com.frontmatic.sendwrite.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Approval
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.ui.graphics.vector.ImageVector
import com.frontmatic.sendwrite.local.model.JobEvent
import com.frontmatic.sendwrite.local.model.JobEventType

object JobEventUtility {
    fun getIconAndDataForJobEventType(jobEvent: JobEvent): Pair<ImageVector, String> {
        val name = jobEvent.name ?: ""
        val description = if (jobEvent.description?.isNotEmpty() == true) "- ${jobEvent.description}" else  ""
        return when (jobEvent.type) {
            JobEventType.APPLIED -> Pair(Icons.Rounded.EditNote, jobEvent.dateToRelativeTime)
            JobEventType.INTERVIEW -> Pair(
                Icons.Rounded.Groups,
                "$name $description"
            )

            JobEventType.ASSIGNMENT -> Pair(
                Icons.Rounded.EditNote,
                "$name $description"
            )

            JobEventType.OFFER -> Pair(
                Icons.Rounded.Approval,
                "$name $description"
            )

            JobEventType.OTHER -> Pair(Icons.Rounded.Notes, jobEvent.description.toString())
        }
    }
}