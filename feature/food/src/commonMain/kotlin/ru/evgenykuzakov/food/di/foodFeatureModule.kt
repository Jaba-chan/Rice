package ru.evgenykuzakov.food.di

import org.koin.dsl.module
import ru.evgenykuzakov.food.ShowMealsScreen
import ru.evgenykuzakov.food.ShowMealsViewModel

val foodFeatureModule = module {
    single { ShowMealsViewModel( get() ) }
}