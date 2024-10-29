package ru.evgenykuzakov.rice

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MealsTest::class], version = 16)
abstract class MealsDatabase : RoomDatabase() {
    abstract fun mealsDao(): MealsDAO

    companion object {
        private var mealsDatabase: MealsDatabase? = null
        private val DB_NAME = "meals_database"


        fun getAppDatabase(context: Context): MealsDatabase? {
            if (mealsDatabase == null) {
                mealsDatabase = Room.databaseBuilder(
                    context,
                    MealsDatabase::class.java, DB_NAME
                ).
                fallbackToDestructiveMigration().build()
            }
            return mealsDatabase
        }
    }
}