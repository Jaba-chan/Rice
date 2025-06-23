package ru.evgenykuzakov.domain.repository

import ru.evgenykuzakov.domain.model.Meal

interface LocalUserMealRepository {

    suspend fun getMeals(): List<Meal>

    suspend fun addMeal(meal: Meal)

    suspend fun deleteMeal(meal: Meal)

    suspend fun updateMeal(meal: Meal)

}