package com.frontmatic.sendwrite.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DateTimeException
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

@RequiresApi(Build.VERSION_CODES.O)
fun getStartAndEndPeriodMillis(monthOffset: Int): Pair<Long, Long> {
    val currentDate = LocalDate.now().minusMonths(monthOffset.toLong())

    val startPeriod = currentDate
        .with(TemporalAdjusters.firstDayOfMonth())
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    val endPeriod = currentDate
        .with(TemporalAdjusters.lastDayOfMonth())
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    return Pair(startPeriod, endPeriod)
}

// month offset is from -11 to 11, 0 is current month, positive offset is previous month, negative offset is next month
@RequiresApi(Build.VERSION_CODES.O)
fun getMonthName(monthOffset: Int): String {
    return try {
        val currentMonth = YearMonth.now().month.value
        val targetMonth = ((currentMonth - 1 - monthOffset) % 12 + 12) % 12 + 1
        val monthEnum = Month.of(targetMonth)
        monthEnum.toString()
    } catch (e: DateTimeException) {
        "Invalid Month"
    }
}

