package ru.evgenykuzakov.rice

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodDAO {
    @Query("SELECT * FROM foods")
    fun getAll(): List<FoodInfo>

    @Query("SELECT * FROM foods WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): FoodInfo

    @Insert
    fun insertAll(vararg foods: FoodInfo)

    @Delete
    fun delete(food: FoodInfo)
}