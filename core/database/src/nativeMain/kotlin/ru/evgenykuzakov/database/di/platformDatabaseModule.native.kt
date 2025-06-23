package ru.evgenykuzakov.database.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.evgenykuzakov.database.dao.UserMealDao
import ru.evgenykuzakov.database.getDatabaseBuilder
import ru.evgenykuzakov.database.getRoomDatabase

actual val platformDatabaseModule = module {
    single<UserMealDao> { getRoomDatabase(getDatabaseBuilder()).getDao() }
}