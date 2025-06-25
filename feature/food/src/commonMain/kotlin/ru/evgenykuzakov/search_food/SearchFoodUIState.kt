package ru.evgenykuzakov.search_food

import ru.evgenykuzakov.domain.model.Product

data class SearchFoodUIState(
    val products: List<Product> = emptyList()
)