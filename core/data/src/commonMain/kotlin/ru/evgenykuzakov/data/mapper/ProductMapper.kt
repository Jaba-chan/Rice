package ru.evgenykuzakov.data.mapper

import ru.evgenykuzakov.data.remoute.model.ProductDto
import ru.evgenykuzakov.domain.model.Product

fun ProductDto.toDomain() = Product(
    id = id,
    name = name,
    calories = calories,
    protein = protein,
    carbohydrates = carbohydrates,
    fats = fats,
    extraInfo = extraInfo,
    netWeight = netWeight,
    numberOfServings = numberOfServings,
    producer = producer,
    productCategory = productCategory,
    source = source
)