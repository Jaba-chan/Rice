package ru.evgenykuzakov.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.evgenykuzakov.domain.model.DatabaseNamesEnum
import ru.evgenykuzakov.domain.model.Meal
import ru.evgenykuzakov.domain.repository.LocalUserMealRepository

class ShowMealsViewModel(
    private val repo: LocalUserMealRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<List<Meal>>(emptyList())
    val uiState: StateFlow<List<Meal>> = _uiState

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.addMeal(
                    Meal(
                        1,
                        1,
                        "Dddd",
                        10,
                        DatabaseNamesEnum.EXTRA_MEALS_DATABASE,
                        1,
                        "dd",
                        111,
                        14f,
                        12f,
                        14f
                    )
                )
            }
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.addMeal(
                    Meal(
                        2,
                        1,
                        "Dddd",
                        10,
                        DatabaseNamesEnum.EXTRA_MEALS_DATABASE,
                        2,
                        "dd",
                        111,
                        14f,
                        12f,
                        14f
                    )
                )
            }
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) { _uiState.value = repo.getMeals() }
        }
    }
}