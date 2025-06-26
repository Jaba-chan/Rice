package ru.evgenykuzakov.show_food

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import org.koin.compose.viewmodel.koinViewModel
import ru.evgenykuzakov.common.Resource

@Composable
fun ShowMealsScreen(
    viewModel: ShowMealsViewModel = koinViewModel(),
    selectedDate: LocalDate,
    paddingValues: PaddingValues
){
    val state by viewModel.uiState.collectAsState()
    val listItemsSize = when (val products = state.meals) {
        is Resource.Success -> products.data.size
        else -> 0
    }
    val screenItems = buildList<ShowMealsItemsScreenItem> {
        repeat(listItemsSize){
            add(ShowMealsItemsScreenItem.MealItem)
        }
    }

    LazyColumn(
        modifier = Modifier.padding(paddingValues)
    ) {
        itemsIndexed(
            items = screenItems,
            key = { index, item -> "${item.hashCode()}-${index}" },
            contentType = { _, item ->
                when (item) {
                    is ShowMealsItemsScreenItem.MealHeader -> ShowMealsContentType.MealsHeader
                    is ShowMealsItemsScreenItem.MealItem -> ShowMealsContentType.MealItem
                }
            }
        ) { index, item ->
            when (item) {
                ShowMealsItemsScreenItem.MealHeader -> {}
                ShowMealsItemsScreenItem.MealItem -> {
                    if (index == 0)
                        Spacer(modifier = Modifier.height(16.dp))
                    else
                        Spacer(modifier = Modifier.height(8.dp))
                    MealItem(
                        uiState = state,
                        position = index
                    )
                }
            }
        }
    }
}

sealed class ShowMealsContentType {
    data object MealsHeader : ShowMealsContentType()
    data object MealItem : ShowMealsContentType()
}