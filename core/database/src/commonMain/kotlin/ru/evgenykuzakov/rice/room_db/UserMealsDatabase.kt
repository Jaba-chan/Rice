package ru.evgenykuzakov.rice.room_db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import ru.evgenykuzakov.rice.dao.UserMealDao
import ru.evgenykuzakov.rice.model.MealEntity

@Database(entities = [MealEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class UserMealsDatabase : RoomDatabase() {
    abstract fun getDao(): UserMealDao
}
