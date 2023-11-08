package com.frontmatic.sendwrite.local.source

import com.frontmatic.sendwrite.local.model.Job
import com.frontmatic.sendwrite.local.model.JobAndEvents
import kotlinx.coroutines.flow.Flow

interface JobLocalDataSource {
    fun getAllJobs(): Flow<List<Job>>

    fun getAllJobsWithEvents(): Flow<List<JobAndEvents>>
    fun getAllJobsSortedByTitle(): Flow<List<Job>>
    fun getAllArchivedJobs(): Flow<List<Job>>

    fun getAllArchivedJobsWithEvents(): Flow<List<JobAndEvents>>
    fun getById(id: Long): Flow<Job>

    fun getByIdWithEvents(id: Long): Flow<JobAndEvents>

    suspend fun saveJob(job: Job): Long
    suspend fun archiveJob(job: Job): Int
    suspend fun deleteJob(id: Job): Int
    suspend fun deleteAllJobs()
}