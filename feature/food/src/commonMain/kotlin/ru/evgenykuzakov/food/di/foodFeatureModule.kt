package ru.evgenykuzakov.food.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.evgenykuzakov.food.ShowMealsViewModel
import ru.evgenykuzakov.search_food.SearchProductsViewModel

val foodFeatureModule = module {
    viewModel { ShowMealsViewModel( get() ) }
    viewModel { SearchProductsViewModel( get() ) }
}