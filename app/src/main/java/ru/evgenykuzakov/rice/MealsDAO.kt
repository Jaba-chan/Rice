package ru.evgenykuzakov.rice

import android.icu.text.Transliterator.Position
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MealsDAO {
    @Query("SELECT * FROM meals")
    fun getAll(): List<MealsTest>

    @Query("SELECT * FROM meals WHERE `database` =:database ORDER BY position")
    fun getBreakfastData(database: DatabaseNamesEnum = DatabaseNamesEnum.BREAKFAST_DATABASE): List<MealsTest>

    @Query("SELECT * FROM meals WHERE `database` =:database ORDER BY position")
    fun getLunch(database: DatabaseNamesEnum = DatabaseNamesEnum.LUNCH_DATABASE): List<MealsTest>

    @Query("SELECT * FROM meals WHERE `database` =:database ORDER BY position")
    fun getDinnerData(database: DatabaseNamesEnum = DatabaseNamesEnum.DINNER_DATABASE): List<MealsTest>

    @Query("SELECT * FROM meals WHERE `database` =:database ORDER BY position")
    fun getExtraMealsData(database: DatabaseNamesEnum = DatabaseNamesEnum.EXTRA_MEALS_DATABASE): List<MealsTest>

    @Update
    suspend fun updateItem(item: MealsTest)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg item: MealsTest)

    @Query("DELETE FROM meals WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE from meals")
    fun clear()
}