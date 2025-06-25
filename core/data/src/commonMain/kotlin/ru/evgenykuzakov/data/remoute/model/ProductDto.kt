package ru.evgenykuzakov.data.remoute.model

import kotlinx.serialization.Serializable
import ru.evgenykuzakov.domain.model.DatabaseNamesEnum

@Serializable
data class ProductResponse(
    val products: List<ProductDto>
)

@Serializable
data class ProductDto(
    val id: Int = 0,
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
