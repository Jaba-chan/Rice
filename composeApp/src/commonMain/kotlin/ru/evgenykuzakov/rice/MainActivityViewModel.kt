package ru.evgenykuzakov.rice

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate
import ru.evgenykuzakov.shared.util.getWeak

class MainActivityViewModel(

) : ViewModel() {
    private val _uiState = MutableStateFlow(AppUIState())
    val uiState: StateFlow<AppUIState> = _uiState

    fun selectDate(date: LocalDate) {
        _uiState.update {
            it.copy(
                selectedDate = date,
                weakDays = date.getWeak()
            )
        }
    }

    fun setDatePickerVisibility(isVisible: Boolean) {
        _uiState.update {
            it.copy(
                isDatePickerVisible = isVisible,)
        }
    }

}