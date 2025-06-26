package ru.evgenykuzakov.common.util

import kotlinx.datetime.LocalDate
import platform.Foundation.NSCalendar
import platform.Foundation.NSDateComponents
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale

actual fun getFormattedDate(date: LocalDate): String {
    val formatter = NSDateFormatter()
    formatter.locale = NSLocale.currentLocale
    formatter.dateFormat = "EEEE, d MMMM"

    val components = NSDateComponents().apply {
        year = date.year.toLong()
        month = date.monthNumber.toLong()
        day = date.dayOfMonth.toLong()
    }

    val calendar = NSCalendar.currentCalendar
    val nsDate = calendar.dateFromComponents(components)!!
    return formatter.stringFromDate(nsDate)
}

actual fun getFormattedToDayOfWeekDate(date: LocalDate): String {
    val formatter = NSDateFormatter()
    formatter.locale = NSLocale.currentLocale
    formatter.dateFormat = "EE"

    val components = NSDateComponents().apply {
        year = date.year.toLong()
        month = date.monthNumber.toLong()
        day = date.dayOfMonth.toLong()
    }

    val calendar = NSCalendar.currentCalendar
    val nsDate = calendar.dateFromComponents(components)!!
    return formatter.stringFromDate(nsDate).apply { this[0].uppercaseChar() }
}