package ru.evgenykuzakov.domain.repository

import ru.evgenykuzakov.domain.model.Product

interface RemoteProductRepository {

    suspend fun searchProductByName(name: String): List<Product>

}