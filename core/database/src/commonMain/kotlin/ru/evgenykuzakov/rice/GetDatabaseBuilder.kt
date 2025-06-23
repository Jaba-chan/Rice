package ru.evgenykuzakov.rice

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import ru.evgenykuzakov.rice.room_db.UserMealsDatabase

fun getRoomDatabase(
    builder: RoomDatabase.Builder<UserMealsDatabase>
): UserMealsDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}