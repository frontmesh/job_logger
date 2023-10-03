package com.frontmatic.sendwrite.local.source.impl

import com.frontmatic.sendwrite.local.source.LocalDataSource
import com.frontmatic.sendwrite.local.SendWriteDatabase
import com.frontmatic.sendwrite.local.model.Job
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(database: SendWriteDatabase): LocalDataSource {
    private val jobDao = database.jobDao()

    override fun getAllJobs(): Flow<List<Job>> {
        return jobDao.getAllSortedByApplied()
    }

    override fun getAllJobsSortedByTitle(): Flow<List<Job>> {
        return jobDao.getAll()
    }

    override fun getAllArchivedJobs(): Flow<List<Job>> {
        return jobDao.getAllArchived()
    }

    override fun getJobById(id: Long): Flow<Job> {
        return jobDao.getById(id)
    }

    override suspend fun saveJob(job: Job): Long {
        return jobDao.insert(job)
    }

    override suspend fun archiveJob(job: Job): Int {
        return jobDao.update(
            job.copy(archived = true)
        )
    }

    override suspend fun deleteJob(job: Job): Int {
        return jobDao.delete(job)
    }

    override suspend fun deleteAllJobs() {
        TODO("Not yet implemented")
    }
}