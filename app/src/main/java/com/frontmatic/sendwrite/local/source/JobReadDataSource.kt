package com.frontmatic.sendwrite.local.source

import kotlinx.coroutines.flow.Flow

interface JobReadDataSource {

    fun getJobsSavedCountForMonthOffset(monthOffset: Int): Flow<Int>
}