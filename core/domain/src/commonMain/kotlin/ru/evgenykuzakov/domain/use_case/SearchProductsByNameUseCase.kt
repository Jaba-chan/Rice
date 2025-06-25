package ru.evgenykuzakov.domain.use_case

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.evgenykuzakov.common.Resource
import ru.evgenykuzakov.domain.model.Meal
import ru.evgenykuzakov.domain.model.Product
import ru.evgenykuzakov.domain.repository.RemoteProductRepository

class SearchProductsByNameUseCase(
    private val repository: RemoteProductRepository
) {
    operator fun invoke(name: String): Flow<Resource<List<Product>>> =
        flow {
            emit(Resource.Loading())
            emit(Resource.Success(repository.searchProductByName(name)))
        }.catch { e ->
            emit(Resource.Error(e.message))
        }.flowOn(Dispatchers.IO)
}