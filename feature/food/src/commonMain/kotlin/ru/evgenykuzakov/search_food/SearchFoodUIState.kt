package ru.evgenykuzakov.search_food

import ru.evgenykuzakov.common.Resource
import ru.evgenykuzakov.domain.model.Product

data class SearchFoodUIState(
    val products: Resource<List<Product>> = Resource.Loading(),
    val searchFieldText: String = ""
)