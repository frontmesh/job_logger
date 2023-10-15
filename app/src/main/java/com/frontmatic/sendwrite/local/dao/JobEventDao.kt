package com.frontmatic.sendwrite.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frontmatic.sendwrite.local.model.JobEvent
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface JobEventDao {
    @Query("SELECT * FROM job_events")
    fun getAll(): Flow<List<JobEvent>>

    @Query("SELECT * FROM job_events WHERE id = :id")
    fun getById(id: Long): Flow<JobEvent>

    @Query("SELECT * FROM job_events WHERE job_id = :jobId")
    fun getByJobId(jobId: Long): Flow<List<JobEvent>>

    @Query("SELECT je.* FROM job_events je INNER JOIN jobs j ON je.job_id = j.id WHERE je.date BETWEEN :start AND :end AND j.archived = 0 ORDER BY je.date ASC")
    fun getEventRange(start: Long, end: Long): Flow<List<JobEvent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(jobEvent: JobEvent): Long

    @Query("DELETE FROM job_events WHERE id = :id")
    fun deleteById(id: Long): Int

    @Query("DELETE FROM job_events WHERE job_id = :jobId")
    fun deleteByJobId(jobId: Long): Int

    @Query("DELETE FROM job_events WHERE job_id = :jobId AND id = (SELECT MAX(id) FROM job_events WHERE job_id = :jobId)")
    fun removeLastEvent(jobId: Long): Int

}