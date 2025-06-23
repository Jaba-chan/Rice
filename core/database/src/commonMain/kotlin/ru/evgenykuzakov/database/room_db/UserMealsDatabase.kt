package ru.evgenykuzakov.database.room_db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import ru.evgenykuzakov.database.dao.UserMealDao
import ru.evgenykuzakov.database.model.MealEntity

@Database(entities = [MealEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class UserMealsDatabase : RoomDatabase() {
    abstract fun getDao(): UserMealDao
}
