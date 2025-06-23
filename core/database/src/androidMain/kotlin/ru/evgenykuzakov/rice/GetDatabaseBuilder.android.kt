package ru.evgenykuzakov.rice

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.evgenykuzakov.rice.room_db.UserMealsDatabase

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<UserMealsDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("meals.db")
    return Room.databaseBuilder<UserMealsDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}