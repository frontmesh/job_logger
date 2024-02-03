package com.frontmatic.sendwrite.local.dao.read

import androidx.room.Dao
import androidx.room.Query
import com.frontmatic.sendwrite.local.model.JobEventType
import kotlinx.coroutines.flow.Flow

@Dao
interface JobEventReadDao {
    @Query("SELECT COUNT(id) FROM job_events WHERE date >= :periodStart AND date < :periodEnd AND type = :type")
    fun getTypeOfJobEventsCountForPeriodAndDate(periodStart: Long, periodEnd: Long, type: JobEventType): Flow<Int>
}