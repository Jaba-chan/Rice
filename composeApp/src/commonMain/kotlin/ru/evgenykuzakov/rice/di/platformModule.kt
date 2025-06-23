package ru.evgenykuzakov.rice.di

import org.koin.dsl.module
import ru.evgenykuzakov.database.di.platformDatabaseModule

val platformModule = module {
    includes(platformDatabaseModule)
}