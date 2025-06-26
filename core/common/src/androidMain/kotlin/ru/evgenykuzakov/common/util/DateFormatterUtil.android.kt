package ru.evgenykuzakov.common.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual fun getFormattedDate(date: LocalDate): String {
    val instant = date.atStartOfDayIn(TimeZone.currentSystemDefault())
    val utilDate = Date(instant.toEpochMilliseconds())

    val formatter = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
    return formatter.format(utilDate).replaceFirstChar { it.uppercaseChar() }
}

actual fun getFormattedToDayOfWeekDate(date: LocalDate): String {
    val instant = date.atStartOfDayIn(TimeZone.currentSystemDefault())
    val utilDate = Date(instant.toEpochMilliseconds())

    val formatter = SimpleDateFormat("EE", Locale.getDefault())
    return formatter.format(utilDate)
}