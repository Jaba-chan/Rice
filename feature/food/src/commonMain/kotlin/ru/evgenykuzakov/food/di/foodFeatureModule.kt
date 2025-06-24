package ru.evgenykuzakov.food.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.evgenykuzakov.food.ShowMealsViewModel

val foodFeatureModule = module {
    viewModel { ShowMealsViewModel( get() ) }
}