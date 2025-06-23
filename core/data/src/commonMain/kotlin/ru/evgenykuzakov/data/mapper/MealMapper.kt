package ru.evgenykuzakov.data.mapper

import ru.evgenykuzakov.database.model.MealEntity
import ru.evgenykuzakov.domain.model.DatabaseNamesEnum
import ru.evgenykuzakov.domain.model.Meal

fun MealEntity.toDomain() = Meal(
    id = id,
    parentId = parentId,
    name = name,
    amount = amount,
    database = DatabaseNamesEnum.entries.first {it.name == database},
    position = position,
    date = date,
    calories = calories,
    protein = protein,
    carbohydrates = carbohydrates,
    fats = fats
)

fun Meal.toEntity() = MealEntity(
    id = id,
    parentId = parentId,
    name = name,
    amount = amount,
    database = DatabaseNamesEnum.entries.first {it.name == database.name},
    position = position,
    date = date,
    calories = calories,
    protein = protein,
    carbohydrates = carbohydrates,
    fats = fats
)