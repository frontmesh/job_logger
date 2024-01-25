package com.frontmatic.sendwrite.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.frontmatic.sendwrite.local.converters.DateConverter
import com.frontmatic.sendwrite.local.converters.EnumConverter
import com.frontmatic.sendwrite.local.dao.JobDao
import com.frontmatic.sendwrite.local.dao.JobEventDao
import com.frontmatic.sendwrite.local.model.Job
import com.frontmatic.sendwrite.local.model.JobEvent
import java.util.Date

@Database(
    entities = [
        Job::class,
        JobEvent::class,
    ],
    autoMigrations = [
        AutoMigration (from = 3, to = 4)
    ],
    version = 4
)
@TypeConverters(
    DateConverter::class,
    EnumConverter::class
)
abstract class SendWriteDatabase: RoomDatabase() {
    abstract fun jobDao(): JobDao
    abstract fun jobEventDao(): JobEventDao
}


