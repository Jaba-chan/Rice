package ru.evgenykuzakov.domain.repository

import ru.evgenykuzakov.domain.model.Meal

interface RemoteProductRepository {

    suspend fun searchProductByName(name: String): List<Meal>

}