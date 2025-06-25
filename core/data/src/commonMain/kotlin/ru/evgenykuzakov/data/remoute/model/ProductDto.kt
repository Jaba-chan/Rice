package ru.evgenykuzakov.data.remoute.model

import kotlinx.serialization.Serializable

@Serializable
data class RunQueryResponse(
    val document: FirestoreDocument? = null,
    val readTime: String? = null
)

@Serializable
data class FirestoreDocument(
    val name: String,
    val fields: Map<String, FirestoreField>
)

@Serializable
data class FirestoreField(
    val stringValue: String? = null,
    val integerValue: String? = null,
    val doubleValue: Double? = null,
)

@Serializable
data class FirestoreArrayValue(
    val values: List<ProductDto> = emptyList()
)

@Serializable
data class ProductDto(
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
