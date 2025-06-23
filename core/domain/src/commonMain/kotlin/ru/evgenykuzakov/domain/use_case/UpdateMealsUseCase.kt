package ru.evgenykuzakov.domain.use_case

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.evgenykuzakov.common.Resource
import ru.evgenykuzakov.domain.model.Meal
import ru.evgenykuzakov.domain.repository.LocalUserMealRepository

class UpdateMealsUseCase(
    private val repository: LocalUserMealRepository
) {
    operator fun invoke(meal: Meal): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())
            emit(Resource.Success(repository.updateMeal(meal)))
        }.catch { e ->
            emit(Resource.Error(e.message))
        }.flowOn(Dispatchers.IO)
}