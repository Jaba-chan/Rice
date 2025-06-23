package ru.evgenykuzakov.food

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ru.evgenykuzakov.data.local.LocalUserMealRepositoryImpl
import ru.evgenykuzakov.domain.model.Meal
import ru.evgenykuzakov.food.placeholder.MealItem
import ru.evgenykuzakov.food.placeholder.ShowMealsItemsScreenItem

@Composable
fun ShowMealsScreen(
    repository: LocalUserMealRepositoryImpl
){
    var state by mutableStateOf<List<Meal>>(emptyList())
    LaunchedEffect(Unit) {
        state = repository.getMeals()
    }
    val screenItems = buildList<ShowMealsItemsScreenItem> {
        repeat(state.size){
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
                ShowMealsItemsScreenItem.MealItem -> MealItem(state[index])
            }
        }
    }
}

sealed class ShowMealsContentType {
    data object MealsHeader : ShowMealsContentType()
    data object MealItem : ShowMealsContentType()
}