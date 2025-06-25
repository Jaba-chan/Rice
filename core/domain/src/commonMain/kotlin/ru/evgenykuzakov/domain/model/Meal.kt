package ru.evgenykuzakov.domain.model


data class Meal(
   val id: Int,
   val parentId: Int,
   val name: String,
   val amount: Int,
   val database: DatabaseNamesEnum,
   val position: Int,
   val date: String,
   val calories: Int?,
   val protein: Float,
   val carbohydrates: Float,
   val fats: Float
)