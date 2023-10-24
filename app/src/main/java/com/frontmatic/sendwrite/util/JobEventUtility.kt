package com.frontmatic.sendwrite.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.rounded.AppRegistration
import androidx.compose.material.icons.rounded.Approval
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.material.icons.rounded.People
import androidx.compose.material.icons.rounded.Task
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

            JobEventType.OTHER -> Pair(
                Icons.Rounded.Notes,
                "$name $description"
            )
        }
    }

    fun getIconForJobEvent(eventType: JobEventType) : ImageVector = when (eventType) {
        JobEventType.APPLIED -> Icons.Rounded.AppRegistration
        JobEventType.INTERVIEW -> Icons.Rounded.People
        JobEventType.ASSIGNMENT -> Icons.Rounded.Task
        JobEventType.OFFER -> Icons.Rounded.AttachMoney
        JobEventType.OTHER -> Icons.Rounded.Dashboard
    }

    fun getTitleForJetLimeItem(jobEvent: JobEvent) : String = when (jobEvent.type) {
        JobEventType.APPLIED -> "${jobEvent.typeToString} - ${jobEvent.dateToRelativeTime}"
        else -> "${jobEvent.typeToString} - ${jobEvent.name ?: ""}"
//        JobEventType.INTERVIEW -> jobEvent.name ?: ""
//        JobEventType.ASSIGNMENT -> jobEvent.name ?: ""
//        JobEventType.OFFER -> jobEvent.name ?: ""
//        JobEventType.OTHER -> jobEvent.name ?: ""
    }

    fun getAvailableJobEventType(eventType: JobEventType, events: List<JobEvent>): Boolean {
        return when (eventType) {
            JobEventType.APPLIED -> events.isEmpty()
            JobEventType.INTERVIEW,
            JobEventType.ASSIGNMENT,
            JobEventType.OTHER -> events.any { it.type == JobEventType.APPLIED }
            JobEventType.OFFER -> events.any { it.type == JobEventType.INTERVIEW }
        }
    }
}