package com.frontmatic.sendwrite.local.source

import com.frontmatic.sendwrite.local.model.JobEventType
import kotlinx.coroutines.flow.Flow

interface JobEventReadDataSource {

    fun getTypeCountForMonthOffset(monthOffset: Int, type: JobEventType): Flow<Int>
}