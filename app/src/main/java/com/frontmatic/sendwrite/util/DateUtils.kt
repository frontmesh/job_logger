package com.frontmatic.sendwrite.util

import java.time.DateTimeException
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

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

// month offset is from 0 to -11
fun getMonthName(monthOffset: Int): String {
    return try {
        val currentMonth = YearMonth.now().month.value
        val targetMonth = (currentMonth + monthOffset).let { adjustedMonth ->
            if (adjustedMonth <= 0) {
                adjustedMonth + 12
            } else {
                adjustedMonth
            }
        }

        val monthEnum = Month.of(targetMonth)
        monthEnum.toString()
    } catch (e: DateTimeException) {
        "Invalid Month"
    }
}