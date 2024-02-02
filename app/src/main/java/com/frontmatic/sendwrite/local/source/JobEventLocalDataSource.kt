package com.frontmatic.sendwrite.local.source

import com.frontmatic.sendwrite.local.model.JobEvent
import kotlinx.coroutines.flow.Flow

interface JobEventLocalDataSource {

    fun getByJobId(jobId: Long): Flow<List<JobEvent>>

    fun getTodayEvents(): Flow<List<JobEvent>>
    fun getWeeklyEvents(): Flow<List<JobEvent>>

    suspend fun saveJobEvent(jobEvent: JobEvent): Long

    suspend fun updateJobEvent(jobEvent: JobEvent)

    fun deleteById(id: Long): Int

    fun removeLastEvent(jobId: Long): Int
}