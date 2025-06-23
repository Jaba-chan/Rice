package ru.evgenykuzakov.rice.di

import org.koin.dsl.module
import ru.evgenykuzakov.domain.use_case.AddMealsUseCase
import ru.evgenykuzakov.domain.use_case.DeleteMealsUseCase
import ru.evgenykuzakov.domain.use_case.GetMealsUseCase
import ru.evgenykuzakov.domain.use_case.UpdateMealsUseCase

val domainModule = module{
    single { GetMealsUseCase(get()) }
    single { AddMealsUseCase(get()) }
    single { DeleteMealsUseCase(get()) }
    single { UpdateMealsUseCase(get()) }
}