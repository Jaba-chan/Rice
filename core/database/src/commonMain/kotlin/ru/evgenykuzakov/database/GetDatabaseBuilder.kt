package ru.evgenykuzakov.database

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import ru.evgenykuzakov.database.room_db.UserMealsDatabase

fun getRoomDatabase(
    builder: RoomDatabase.Builder<UserMealsDatabase>
): UserMealsDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}