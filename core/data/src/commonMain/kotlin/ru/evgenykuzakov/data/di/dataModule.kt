package ru.evgenykuzakov.data.di

import org.koin.dsl.bind
import org.koin.dsl.module
import ru.evgenykuzakov.data.local.LocalUserMealRepositoryImpl
import ru.evgenykuzakov.data.remoute.RemoteProductRepositoryImpl
import ru.evgenykuzakov.domain.repository.LocalUserMealRepository
import ru.evgenykuzakov.domain.repository.RemoteProductRepository
import ru.evgenykuzakov.network.di.networkModule

val dataModule = module {
    single { LocalUserMealRepositoryImpl(get()) }.bind<LocalUserMealRepository>()
    single { RemoteProductRepositoryImpl(get(), get())}.bind<RemoteProductRepository>()
    includes(networkModule)
}