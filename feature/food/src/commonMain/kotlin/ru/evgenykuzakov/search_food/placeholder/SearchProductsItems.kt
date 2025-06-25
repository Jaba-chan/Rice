package ru.evgenykuzakov.search_food.placeholder

import androidx.compose.foundation.layout.Column
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import ru.evgenykuzakov.domain.model.Meal
import ru.evgenykuzakov.domain.model.Product
import ru.evgenykuzakov.food.placeholder.ShowMealsItemsScreenItem

@Composable
fun MealItem1(meal: Product){
    Column {
        Text(text = meal.name)
    }

}

@Composable
fun SearchField(
    onTextChanged: (String) -> Unit
){
    val text = mutableStateOf("")
    OutlinedTextField(
        value = text.value,
        onValueChange = onTextChanged
    )
}

sealed class SearchProductsItems{
    data object MealHeader : SearchProductsItems()
    data object MealItem : SearchProductsItems()
    data object SearchItem : SearchProductsItems()
}