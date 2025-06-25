package ru.evgenykuzakov.search_food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.evgenykuzakov.domain.repository.RemoteProductRepository

class SearchProductsViewModel(
    private val repo: RemoteProductRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchFoodUIState())
    val uiState: StateFlow<SearchFoodUIState> = _uiState

    fun getMatches(name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.update {
                    it.copy(
                        products = repo.searchProductByName(name),
                    )
                }
            }
        }
    }
}