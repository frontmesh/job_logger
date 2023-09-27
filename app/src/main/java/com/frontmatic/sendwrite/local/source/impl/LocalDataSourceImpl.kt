package com.frontmatic.sendwrite.local.source.impl

import com.frontmatic.sendwrite.local.source.LocalDataSource
import com.frontmatic.sendwrite.local.SendWriteDatabase
import com.frontmatic.sendwrite.local.model.Job
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(database: SendWriteDatabase): LocalDataSource {
    private val jobDao = database.jobDao()
    override suspend fun getAllJobs(): Flow<List<Job>> {
        return jobDao.getAll()
    }

    override suspend fun saveJob(job: Job) {
        jobDao.insert(job)
    }

    override suspend fun deleteJob(job: Job) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllJobs() {
        TODO("Not yet implemented")
    }
}