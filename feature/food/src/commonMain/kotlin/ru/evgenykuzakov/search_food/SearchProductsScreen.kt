package ru.evgenykuzakov.search_food

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import ru.evgenykuzakov.search_food.placeholder.MealItem1
import ru.evgenykuzakov.search_food.placeholder.SearchField
import ru.evgenykuzakov.search_food.placeholder.SearchProductsItems

@Composable
fun SearchProductsScreen(
    viewModel: SearchProductsViewModel = koinViewModel(),
){
    val state by viewModel.uiState.collectAsState()
    val screenItems = buildList {
        add(SearchProductsItems.SearchItem)
        repeat(state.products.size - 1){
            add(SearchProductsItems.MealItem)
        }
    }

    LazyColumn {
        itemsIndexed(
            items = screenItems,
            key = { index, item -> "${item.hashCode()}-${index}" },
            contentType = { _, item ->
                when (item) {
                    is SearchProductsItems.MealHeader -> ShowMealsContentType.MealsHeader
                    is SearchProductsItems.MealItem -> ShowMealsContentType.MealItem
                    SearchProductsItems.SearchItem -> ShowMealsContentType.SearchField
                }
            }
        ) { index, item ->
            when (item) {
                SearchProductsItems.MealHeader -> {}
                SearchProductsItems.MealItem -> MealItem1(state.products[index])
                SearchProductsItems.SearchItem -> SearchField(onTextChanged = {
                    viewModel.getMatches(
                        it
                    )
                })
            }
        }
    }
}

sealed class ShowMealsContentType {
    data object MealsHeader : ShowMealsContentType()
    data object MealItem : ShowMealsContentType()
    data object SearchField : ShowMealsContentType()
}