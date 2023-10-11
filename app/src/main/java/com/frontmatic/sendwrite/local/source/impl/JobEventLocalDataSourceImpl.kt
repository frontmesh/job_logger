package com.frontmatic.sendwrite.local.source.impl

import com.frontmatic.sendwrite.local.SendWriteDatabase
import com.frontmatic.sendwrite.local.model.JobEvent
import com.frontmatic.sendwrite.local.source.JobEventLocalDataSource
import kotlinx.coroutines.flow.Flow
import java.util.Date

class JobEventLocalDataSourceImpl(database: SendWriteDatabase): JobEventLocalDataSource {
    private val jobEventDao = database.jobEventDao()
    override fun getByJobId(jobId: Long): Flow<List<JobEvent>> {
        return jobEventDao.getByJobId(jobId)
    }

    override fun getTodayEvents(date: Long): Flow<List<JobEvent>> {
        return jobEventDao.getTodayEvents(date)
    }

    override suspend fun saveJobEvent(jobEvent: JobEvent): Long {
        return jobEventDao.insert(jobEvent)
    }

    override fun deleteById(id: Long): Int {
        return jobEventDao.deleteById(id)
    }

    override fun removeLastEvent(jobId: Long): Int {
        return jobEventDao.removeLastEvent(jobId)
    }
}