package ru.evgenykuzakov.rice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import ru.evgenykuzakov.common.util.getWeak
import ru.evgenykuzakov.common.util.toStringDate
import ru.evgenykuzakov.SendSelectedDate

class MainActivityViewModel(
    private val dateWire: SendSelectedDate
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppUIState())
    val uiState: StateFlow<AppUIState> = _uiState

    private fun trigger() {
        viewModelScope.launch {
            dateWire.sendDate(_uiState.value.selectedDate.toStringDate())
        }
    }

    fun selectDate(date: LocalDate) {
        _uiState.update {
            it.copy(
                selectedDate = date,
                weakDays = date.getWeak()
            )
        }
        trigger()
    }

    fun setDatePickerVisibility(isVisible: Boolean) {
        _uiState.update {
            it.copy(
                isDatePickerVisible = isVisible,)
        }
    }

}