package ru.evgenykuzakov.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.evgenykuzakov.show_food.ShowMealsViewModel
import ru.evgenykuzakov.search_food.SearchProductsViewModel
import ru.evgenykuzakov.SendSelectedDate

val foodFeatureModule = module {

    single { SendSelectedDate() }
    viewModel { ShowMealsViewModel( get(), get() ) }
    viewModel { SearchProductsViewModel( get(), get(), get()) }
}