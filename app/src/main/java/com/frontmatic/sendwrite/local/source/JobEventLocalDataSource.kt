package com.frontmatic.sendwrite.local.source

import com.frontmatic.sendwrite.local.model.JobEvent
import kotlinx.coroutines.flow.Flow

interface JobEventLocalDataSource {

    fun getByJobId(jobId: Long): Flow<List<JobEvent>>

    suspend fun saveJobEvent(jobEvent: JobEvent): Long

    fun deleteById(id: Long): Int

    fun removeLastEvent(jobId: Long): Int
}