package ru.evgenykuzakov.food

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import ru.evgenykuzakov.data.local.LocalUserMealRepositoryImpl
import ru.evgenykuzakov.food.placeholder.MealItem
import ru.evgenykuzakov.food.placeholder.ShowMealsItemsScreenItem

@Composable
fun ShowMealsScreen(
    viewModel: ShowMealsViewModel = koinViewModel(),
){
    var state = viewModel.uiState
    val screenItems = buildList<ShowMealsItemsScreenItem> {
        repeat(2){
            add(ShowMealsItemsScreenItem.MealItem)
        }
    }

    LazyColumn {
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
                ShowMealsItemsScreenItem.MealItem -> MealItem(state.value[index])
            }
        }
    }
}

sealed class ShowMealsContentType {
    data object MealsHeader : ShowMealsContentType()
    data object MealItem : ShowMealsContentType()
}