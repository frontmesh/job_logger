package com.frontmatic.sendwrite.local

import com.frontmatic.sendwrite.local.model.Job
import com.frontmatic.sendwrite.local.source.LocalDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource
) {
    suspend fun getAllJobs() {
        localDataSource.getAllJobs()
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