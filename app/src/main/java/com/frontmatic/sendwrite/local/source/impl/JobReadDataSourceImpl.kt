package com.frontmatic.sendwrite.local.source.impl

import com.frontmatic.sendwrite.local.SendWriteDatabase
import com.frontmatic.sendwrite.local.dao.read.JobReadDao
import com.frontmatic.sendwrite.local.source.JobReadDataSource
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

class JobReadDataSourceImpl(database: SendWriteDatabase): JobReadDataSource {

    private val jobReadDao: JobReadDao = database.jobReadDao()

    override fun getJobsSavedCountForMonthOffset(monthOffset:Int): Flow<Int> {
        val startPeriod = LocalDate.now()
            .minusMonths(monthOffset.toLong())
            .with(TemporalAdjusters.firstDayOfMonth())
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val endPeriod = LocalDate.now()
            .minusMonths(monthOffset.toLong())
            .with(TemporalAdjusters.lastDayOfMonth())
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        return jobReadDao.getJobsSavedCountForPeriod(startPeriod, endPeriod)
    }
}