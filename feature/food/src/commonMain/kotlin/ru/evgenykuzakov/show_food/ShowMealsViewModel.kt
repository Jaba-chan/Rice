package ru.evgenykuzakov.show_food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.evgenykuzakov.SendSelectedDate
import ru.evgenykuzakov.domain.use_case.GetMealsUseCase

class ShowMealsViewModel(
    private val getMealsUseCase: GetMealsUseCase,
    private val dateWire: SendSelectedDate
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShowMealsUIState())
    val uiState: StateFlow<ShowMealsUIState> = _uiState

    init {
        viewModelScope.launch {
            dateWire.selectedDate.collect { date ->
                getMealsUseCase(date)
                    .collect { result ->
                        _uiState.update {
                            it.copy(
                                meals = result
                            )
                        }
                    }
            }
        }

    }
}