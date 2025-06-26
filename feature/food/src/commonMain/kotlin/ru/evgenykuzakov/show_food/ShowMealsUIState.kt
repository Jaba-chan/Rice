package ru.evgenykuzakov.show_food

import ru.evgenykuzakov.common.Resource
import ru.evgenykuzakov.domain.model.Meal

data class ShowMealsUIState(
    val meals: Resource<List<Meal>> = Resource.Loading()
)