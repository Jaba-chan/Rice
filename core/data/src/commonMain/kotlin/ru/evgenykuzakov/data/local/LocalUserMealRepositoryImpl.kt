package ru.evgenykuzakov.data.local

import ru.evgenykuzakov.data.mapper.toDomain
import ru.evgenykuzakov.data.mapper.toEntity
import ru.evgenykuzakov.database.dao.UserMealDao
import ru.evgenykuzakov.domain.model.Meal
import ru.evgenykuzakov.domain.repository.LocalUserMealRepository


class LocalUserMealRepositoryImpl(
    private val dao: UserMealDao
): LocalUserMealRepository {
    override suspend fun getMeals(): List<Meal> {
        return dao.getAll().map { it.toDomain() }
    }

    override suspend fun addMeal(meal: Meal) {
        dao.insert(meal.toEntity())
    }

    override suspend fun deleteMeal(meal: Meal) {
        dao.delete(meal.id)
    }

    override suspend fun updateMeal(meal: Meal) {
        dao.updateItem(meal.toEntity())
    }
}