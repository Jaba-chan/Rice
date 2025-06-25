package ru.evgenykuzakov.food.placeholder

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import ru.evgenykuzakov.domain.model.Meal

@Composable
fun MealItem(meal: Meal){
    Column {
        Text(text = meal.name)
        Text(text = meal.amount.toString())
    }

}

sealed class ShowMealsItemsScreenItem {
    data object MealHeader : ShowMealsItemsScreenItem()
    data object MealItem : ShowMealsItemsScreenItem()
}