package ru.evgenykuzakov.rice.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.evgenykuzakov.data.di.dataModule
import ru.evgenykuzakov.food.di.foodFeatureModule
import ru.evgenykuzakov.rice.MainActivityViewModel

val appModule = module {

    viewModel { MainActivityViewModel() }

    includes(domainModule, dataModule, platformModule, foodFeatureModule)
}