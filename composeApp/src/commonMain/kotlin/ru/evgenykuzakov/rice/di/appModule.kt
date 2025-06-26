package ru.evgenykuzakov.rice.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.evgenykuzakov.data.di.dataModule
import ru.evgenykuzakov.di.foodFeatureModule
import ru.evgenykuzakov.rice.MainActivityViewModel

val appModule = module {

    viewModel { MainActivityViewModel(get()) }

    includes(domainModule, dataModule, platformModule, foodFeatureModule)
}