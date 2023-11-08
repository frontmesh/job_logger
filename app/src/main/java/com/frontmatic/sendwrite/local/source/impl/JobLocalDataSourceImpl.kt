package com.frontmatic.sendwrite.local.source.impl

import com.frontmatic.sendwrite.local.SendWriteDatabase
import com.frontmatic.sendwrite.local.model.Job
import com.frontmatic.sendwrite.local.model.JobAndEvents
import com.frontmatic.sendwrite.local.source.JobLocalDataSource
import kotlinx.coroutines.flow.Flow

class JobLocalDataSourceImpl(database: SendWriteDatabase): JobLocalDataSource {
    private val jobDao = database.jobDao()
    override fun getByIdWithEvents(id: Long): Flow<JobAndEvents> {
        return jobDao.getJobAndEvents(id)
    }

    override fun getAllJobs(): Flow<List<Job>> {
        return jobDao.getAllSortedByApplied()
    }

    override fun getAllJobsWithEvents(): Flow<List<JobAndEvents>> {
        return jobDao.getAllWithEvents()
    }

    override fun getAllJobsSortedByTitle(): Flow<List<Job>> {
        return jobDao.getAll()
    }

    override fun getAllArchivedJobs(): Flow<List<Job>> {
        return jobDao.getAllArchived()
    }

    override fun getAllArchivedJobsWithEvents(): Flow<List<JobAndEvents>> {
        return jobDao.getAllArchivedWithEvents()
    }

    override fun getById(id: Long): Flow<Job> {
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