package ru.evgenykuzakov.data.di

import org.koin.dsl.bind
import org.koin.dsl.module
import ru.evgenykuzakov.data.local.LocalUserMealRepositoryImpl
import ru.evgenykuzakov.database.di.platformDatabaseModule
import ru.evgenykuzakov.domain.repository.LocalUserMealRepository

val dataModule = module {
    single { LocalUserMealRepositoryImpl(get()) }.bind<LocalUserMealRepository>()
}