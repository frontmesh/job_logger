package com.frontmatic.sendwrite.local.converters

import androidx.room.TypeConverter
import com.frontmatic.sendwrite.local.model.JobEventType

class EnumConverter {
    @TypeConverter
    fun fromJobEventType(value: String?): JobEventType? {
        return value?.let { JobEventType.valueOf(it) }
    }

    @TypeConverter
    fun jobEventTypeToString(jobEventType: JobEventType?): String? {
        return jobEventType?.name
    }
}