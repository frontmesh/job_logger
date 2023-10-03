package com.frontmatic.sendwrite.local

import com.frontmatic.sendwrite.local.model.Job
import com.frontmatic.sendwrite.local.source.LocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource
) {

    fun getAllJobs(): Flow<List<Job>> {
        return localDataSource.getAllJobs()
    }

    fun getAllJobsSortedByTitle(): Flow<List<Job>> {
        return localDataSource.getAllJobsSortedByTitle()
    }

    fun getAllArchivedJobs(): Flow<List<Job>> {
        return localDataSource.getAllArchivedJobs()
    }

    fun getJobById(id: Long): Flow<Job> {
        return localDataSource.getJobById(id)
    }

    suspend fun saveJob(job: Job) {
        localDataSource.saveJob(job)
    }

    suspend fun deleteJob(job: Job) {
        localDataSource.deleteJob(job)
    }

    suspend fun deleteAllJobs() {
        localDataSource.deleteAllJobs()
    }
}