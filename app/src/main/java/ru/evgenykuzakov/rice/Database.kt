package ru.evgenykuzakov.rice

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [FoodInfo::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDAO
    companion object {
        @Volatile
        private var appDatabase: AppDatabase? = null

        fun getAppDatabase(context: Context?): AppDatabase? {
            if (appDatabase == null) {
                appDatabase = databaseBuilder(
                    context!!,
                    AppDatabase::class.java, "your_database_name.db"
                )
                    .createFromAsset("database/111.db") // путь к базе данных в папке assets
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return appDatabase
        }
    }
}