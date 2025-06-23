package ru.evgenykuzakov.rice.di

import org.koin.dsl.module
import ru.evgenykuzakov.data.di.dataModule
import ru.evgenykuzakov.food.di.foodFeatureModule

val appModule = module {
    includes(domainModule, dataModule, platformModule, foodFeatureModule)
}