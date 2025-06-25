package ru.evgenykuzakov.data.mapper

import ru.evgenykuzakov.data.remoute.model.FirestoreDocument
import ru.evgenykuzakov.data.remoute.model.ProductDto

fun FirestoreDocument.toProductDto(): ProductDto {
    fun getString(key: String): String = fields[key]?.stringValue ?: ""
    fun getInt(key: String): Int = fields[key]?.integerValue?.toIntOrNull() ?: 0
    fun getFloat(key: String): Float = fields[key]?.doubleValue?.toFloat()
        ?: fields[key]?.integerValue?.toFloatOrNull()
        ?: 0f

    return ProductDto(
        id = getInt("id"),
        name = getString("name"),
        extraInfo = getString("extraInfo"),
        netWeight = getFloat("netWeight"),
        numberOfServings = getInt("numberOfServings"),
        producer = getString("producer"),
        productCategory = getString("productCategory"),
        source = getString("source"),
        calories = fields["calories"]?.integerValue?.toIntOrNull(),
        protein = getFloat("protein"),
        carbohydrates = getFloat("carbohydrates"),
        fats = getFloat("fats")
    )
}
