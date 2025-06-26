package ru.evgenykuzakov

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class SendSelectedDate {
    private val _selectedDate = MutableSharedFlow<String>(replay = 1)
    val selectedDate: SharedFlow<String> = _selectedDate

    suspend fun sendDate(date: String) {
        _selectedDate.emit(date)
    }
}