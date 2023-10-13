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

    // query date between start and end
    @Query("SELECT * FROM job_events WHERE date BETWEEN :start AND :end ORDER BY date ASC")
    fun getTodayEvents(start: Long, end: Long): Flow<List<JobEvent>>


    @Query("SELECT * FROM job_events WHERE date(date) < date('now', '+7 day') AND type != 'APPLIED'")
    fun getEventsWhereDueDateIsThisWeek(): Flow<List<JobEvent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(jobEvent: JobEvent): Long

    @Query("DELETE FROM job_events WHERE id = :id")
    fun deleteById(id: Long): Int

    @Query("DELETE FROM job_events WHERE job_id = :jobId")
    fun deleteByJobId(jobId: Long): Int

    @Query("DELETE FROM job_events WHERE job_id = :jobId AND id = (SELECT MAX(id) FROM job_events WHERE job_id = :jobId)")
    fun removeLastEvent(jobId: Long): Int

}