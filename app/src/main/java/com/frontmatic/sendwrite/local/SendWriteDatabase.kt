package com.frontmatic.sendwrite.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.frontmatic.sendwrite.local.dao.JobDao
import com.frontmatic.sendwrite.local.dao.JobEventDao
import com.frontmatic.sendwrite.local.model.Job
import com.frontmatic.sendwrite.local.model.JobEvent

@Database(entities = [
    Job::class,
    JobEvent::class,
 ], version = 1)
abstract class SendWriteDatabase: RoomDatabase() {
    abstract fun jobDao(): JobDao
    abstract fun jobEventDao(): JobEventDao
}