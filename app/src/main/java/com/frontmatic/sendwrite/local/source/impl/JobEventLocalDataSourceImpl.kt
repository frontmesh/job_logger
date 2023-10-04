package com.frontmatic.sendwrite.local.source.impl

import com.frontmatic.sendwrite.local.SendWriteDatabase
import com.frontmatic.sendwrite.local.source.JobEventLocalDataSource

class JobEventLocalDataSourceImpl(database: SendWriteDatabase): JobEventLocalDataSource {
    private val jobEventDao = database.jobEventDao()
}