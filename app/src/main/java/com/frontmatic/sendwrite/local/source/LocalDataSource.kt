package com.frontmatic.sendwrite.local.source

import com.frontmatic.sendwrite.local.model.Job
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun getAllJobs(): Flow<List<Job>>
    suspend fun saveJob(job: Job)
    suspend fun deleteJob(job: Job)
    suspend fun deleteAllJobs()
}