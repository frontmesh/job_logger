package com.frontmatic.sendwrite.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.frontmatic.sendwrite.local.model.Job
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {

    @Query("SELECT * FROM jobs")
    fun getAll(): Flow<List<Job>>
}