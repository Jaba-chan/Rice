package ru.evgenykuzakov.rice

import kotlinx.datetime.LocalDate
import ru.evgenykuzakov.shared.util.getTodayDate
import ru.evgenykuzakov.shared.util.getWeak

data class AppUIState(
    val selectedDate: LocalDate = getTodayDate(),
    val weakDays: List<LocalDate> = getTodayDate().getWeak(),
    val isDatePickerVisible: Boolean = false
)