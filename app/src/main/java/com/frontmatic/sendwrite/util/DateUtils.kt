package com.frontmatic.sendwrite.util

import java.time.LocalDate
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