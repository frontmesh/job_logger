package com.frontmatic.sendwrite.ui.screen.events
enum class EventPeriod {
    TODAY,
    WEEK,
    MONTH,
    YEAR
}
data class EventListScreenNavArgs(
    val period: EventPeriod
)
