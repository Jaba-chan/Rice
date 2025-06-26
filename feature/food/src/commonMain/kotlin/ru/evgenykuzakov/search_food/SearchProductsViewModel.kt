package ru.evgenykuzakov.search_food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.evgenykuzakov.domain.model.DatabaseNamesEnum
import ru.evgenykuzakov.domain.model.Meal
import ru.evgenykuzakov.domain.model.Product
import ru.evgenykuzakov.domain.use_case.AddMealUseCase
import ru.evgenykuzakov.domain.use_case.SearchProductsByNameUseCase
import ru.evgenykuzakov.SendSelectedDate

class SearchProductsViewModel(
    private val searchProductsByNameUseCase: SearchProductsByNameUseCase,
    private val addMealsUseCase: AddMealUseCase,
    private val dataWire: SendSelectedDate
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchFoodUIState())
    val uiState: StateFlow<SearchFoodUIState> = _uiState

    fun getMatches(name: String) {
        _uiState.update { it.copy(searchFieldText = name) }
        viewModelScope.launch {
            searchProductsByNameUseCase(name = name)
                .collect { result ->
                    _uiState.update {
                        it.copy(
                            products = result,
                        )
                    }
                }
        }
    }

    fun addMeal(product: Product) {
        viewModelScope.launch {
            println("Start")

            val date = dataWire.selectedDate.first()
            println(date)
            addMealsUseCase(
                Meal(
                    id = 0,
                    parentId = product.id,
                    name = product.name,
                    amount = 100,
                    database = DatabaseNamesEnum.BREAKFAST_DATABASE,
                    position = 1,
                    date = date,
                    calories = product.calories,
                    protein = product.protein,
                    carbohydrates = product.carbohydrates,
                    fats = product.fats
                )
            ).collect {}
        }
    }

    fun clearSearchText() {
        _uiState.update {
            it.copy(
                searchFieldText = "",
            )
        }
    }

}