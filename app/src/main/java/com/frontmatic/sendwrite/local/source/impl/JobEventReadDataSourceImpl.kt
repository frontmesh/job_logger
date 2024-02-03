package com.frontmatic.sendwrite.local.source.impl

import com.frontmatic.sendwrite.local.SendWriteDatabase
import com.frontmatic.sendwrite.local.source.JobEventReadDataSource

class JobEventReadDataSourceImpl(database: SendWriteDatabase): JobEventReadDataSource {

    private val jobEventReadDao = database.jobEventReadDao()

}