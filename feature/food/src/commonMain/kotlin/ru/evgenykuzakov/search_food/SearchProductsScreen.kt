package ru.evgenykuzakov.search_food

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import ru.evgenykuzakov.common.Resource
import ru.evgenykuzakov.designsystem.SearchField

@Composable
fun SearchProductsScreen(
    viewModel: SearchProductsViewModel = koinViewModel(),
    paddingValues: PaddingValues
) {
    val state by viewModel.uiState.collectAsState()
    val listItemsSize = when (val products = state.products) {
        is Resource.Success -> products.data.size
        else -> 0
    }
    val screenItems = buildList {
        add(SearchProductsItems.SearchItem)
        repeat(listItemsSize) {
            add(SearchProductsItems.ProductItem)
        }
    }

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        itemsIndexed(
            items = screenItems,
            key = { index, item -> "${item.hashCode()}-${index}" },
            contentType = { _, item ->
                when (item) {
                    is SearchProductsItems.ProductItem -> ShowMealsContentType.ProductItem
                    SearchProductsItems.SearchItem -> ShowMealsContentType.SearchField
                }
            }
        ) { index, item ->
            when (item) {
                SearchProductsItems.ProductItem -> {
                    ProductItem(
                        uiState = state,
                        position = listItemsSize - index,
                        onClick = {
                            viewModel.addMeal(it)
                        }
                    )
                    StyledHorizontalDivider()
                }

                SearchProductsItems.SearchItem ->
                    SearchField(
                        value = state.searchFieldText,
                        onClearClick = { viewModel.clearSearchText() },
                        onValueChange = { viewModel.getMatches(it) },
                    )
            }
        }
    }
}

sealed class ShowMealsContentType {
    data object ProductItem : ShowMealsContentType()
    data object SearchField : ShowMealsContentType()
}