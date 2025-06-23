package ru.evgenykuzakov.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.evgenykuzakov.database.room_db.UserMealsDatabase

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<UserMealsDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("meals.db")
    return Room.databaseBuilder<UserMealsDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}