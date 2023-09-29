package com.frontmatic.sendwrite.local.source

import com.frontmatic.sendwrite.local.model.Job
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAllJobs(): Flow<List<Job>>

    fun getJobById(id: Long): Flow<Job>
    suspend fun saveJob(job: Job): Long
    suspend fun deleteJob(job: Job): Long
    suspend fun deleteAllJobs()
}