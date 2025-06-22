package ru.evgenykuzakov.rice.room_db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TodoEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class UserMealsDatabase : RoomDatabase() {
    abstract fun getDao(): TodoDao
}
