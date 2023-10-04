package com.frontmatic.sendwrite.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.frontmatic.sendwrite.local.model.Job
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {
    @Query("SELECT * FROM jobs ORDER BY title DESC")
    fun getAll(): Flow<List<Job>>

    @Query("SELECT * FROM jobs ORDER BY applied ASC")
    fun getAllSortedByApplied(): Flow<List<Job>>

    @Query("SELECT * FROM jobs WHERE id = :id")
    fun getById(id: Long): Flow<Job>

    @Query("SELECT * FROM jobs WHERE archived = 1")
    fun getAllArchived(): Flow<List<Job>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(job: Job): Long

    @Update()
    fun update(job: Job): Int

    @Delete
    fun delete(job: Job): Int
}