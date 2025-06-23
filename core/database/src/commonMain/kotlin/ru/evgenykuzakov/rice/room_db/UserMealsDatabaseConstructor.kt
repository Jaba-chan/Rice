package ru.evgenykuzakov.rice.room_db

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<UserMealsDatabase> {
    override fun initialize(): UserMealsDatabase
}