package ru.evgenykuzakov.domain.model

data class Product(
   val id: Int,
   val name: String,
   val extraInfo: String,
   val netWeight: Float,
   val numberOfServings: Int,
   val producer: String,
   val productCategory: String,
   val source: String,
   val calories: Int?,
   val protein: Float,
   val carbohydrates: Float,
   val fats: Float
)