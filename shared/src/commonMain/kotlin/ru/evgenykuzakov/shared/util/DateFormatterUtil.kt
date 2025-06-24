package ru.evgenykuzakov.shared.util

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

expect fun getFormattedDate(date: LocalDate): String

expect fun getFormattedToDayOfWeekDate(date: LocalDate): String

fun getTodayDate() = Clock.System.now()
    .toLocalDateTime(TimeZone.currentSystemDefault())
    .date

fun LocalDate.getWeak(): MutableList<LocalDate> {
    val weekDays = mutableListOf<LocalDate>()
    var start = this.minus(period = DatePeriod(days = this.dayOfWeek.ordinal))
    weekDays.add(start)
    repeat(6){
        start = start.plus(period = DatePeriod(days = 1))
        weekDays.add(start)
    }
    return weekDays
}

fun Long.formatMillisToDate(): LocalDate = Instant
        .fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date