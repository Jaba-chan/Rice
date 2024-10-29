package ru.evgenykuzakov.rice

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [FoodInfo::class], version = 2)
abstract class FoodInfoDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDAO
    companion object {

        private var appDatabase: FoodInfoDatabase? = null

        fun getFoodInfoDatabase(context: Context?): FoodInfoDatabase? {
            if (appDatabase == null) {
                appDatabase = databaseBuilder(
                    context!!,
                    FoodInfoDatabase::class.java, "your_database_name.db"
                )
                    .createFromAsset("database/testDB.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return appDatabase
        }
    }
}