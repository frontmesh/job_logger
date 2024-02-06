package com.frontmatic.sendwrite.local.source.impl

import android.util.Log
import com.frontmatic.sendwrite.local.SendWriteDatabase
import com.frontmatic.sendwrite.local.model.JobEventType
import com.frontmatic.sendwrite.local.source.JobEventReadDataSource
import com.frontmatic.sendwrite.util.getStartAndEndPeriodMillis
import kotlinx.coroutines.flow.Flow

class JobEventReadDataSourceImpl(database: SendWriteDatabase): JobEventReadDataSource {

    private val jobEventReadDao = database.jobEventReadDao()
    override fun getTypeCountForMonthOffset(monthOffset: Int, type: JobEventType): Flow<Int> {
        val (startPeriod, endPeriod) = getStartAndEndPeriodMillis(monthOffset)

        return jobEventReadDao.getTypeOfJobEventsCountForPeriodAndDate(startPeriod, endPeriod, type)
    }

}