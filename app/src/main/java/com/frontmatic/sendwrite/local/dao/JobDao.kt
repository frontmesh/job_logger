package com.frontmatic.sendwrite.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.frontmatic.sendwrite.local.model.Job
import com.frontmatic.sendwrite.local.model.JobAndEvents
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {
    @Query("SELECT * FROM jobs WHERE archived = 0 ORDER BY title DESC")
    fun getAll(): Flow<List<Job>>

    @Query("SELECT * FROM jobs WHERE archived = 0 ORDER BY id DESC")
    fun getAllWithEvents(): Flow<List<JobAndEvents>>


    @Query("SELECT * FROM jobs WHERE archived = 0 ORDER BY title ASC")
    fun getAllSortedByApplied(): Flow<List<Job>>

    @Transaction
    @Query("SELECT * FROM jobs WHERE id = :id")
   fun getJobAndEvents(id: Long): Flow<JobAndEvents>

    @Query("SELECT * FROM jobs WHERE id = :id")
    fun getById(id: Long): Flow<Job>

    @Query("SELECT * FROM jobs WHERE archived = 1")
    fun getAllArchived(): Flow<List<Job>>

    @Query("SELECT * FROM jobs WHERE archived = 1")
    fun getAllArchivedWithEvents(): Flow<List<JobAndEvents>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(job: Job): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(job: Job): Int

    @Delete(entity = Job::class)
    fun delete(job: Job): Int
}