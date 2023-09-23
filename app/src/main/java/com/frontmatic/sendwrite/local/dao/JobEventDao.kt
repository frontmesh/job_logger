package com.frontmatic.sendwrite.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.frontmatic.sendwrite.local.model.JobEvent
import kotlinx.coroutines.flow.Flow

@Dao
interface JobEventDao {

    @Query("SELECT * FROM job_events")
    fun getAll(): Flow<List<JobEvent>>
}