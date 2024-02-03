package com.frontmatic.sendwrite.local.dao.read

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JobReadDao {
    // get count of all jobs saved in the database in the current month
    @Query("SELECT COUNT(id) FROM jobs WHERE created_at >= :periodStart AND created_at < :periodEnd")
    fun getJobsSavedCountForPeriod(periodStart: Long, periodEnd: Long): Flow<Int>
}