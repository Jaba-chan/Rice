package ru.evgenykuzakov.rice.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import ru.evgenykuzakov.rice.data.FoodInfo
import ru.evgenykuzakov.rice.databases.DAO.FoodDAO

@Database(entities = [FoodInfo::class], version = 4)
abstract class FoodInfoDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDAO
    companion object {

        private var appDatabase: FoodInfoDatabase? = null

        fun getFoodInfoDatabase(context: Context?): FoodInfoDatabase? {
            if (appDatabase == null) {
                appDatabase = databaseBuilder(
                    context!!,
                    FoodInfoDatabase::class.java, "foods.db"
                )
                    .createFromAsset("database/food_data.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return appDatabase
        }
    }
}