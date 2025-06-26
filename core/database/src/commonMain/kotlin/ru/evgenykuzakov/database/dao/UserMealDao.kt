package ru.evgenykuzakov.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.evgenykuzakov.database.model.MealEntity
import ru.evgenykuzakov.database.model.NutrientsEntity

@Dao
interface UserMealDao {

    @Query("SELECT * FROM meals")
    fun getAll(): List<MealEntity>

    @Query("SELECT * FROM meals WHERE date LIKE :date")
    fun getAllByDAte(date: String): MutableList<MealEntity>

    @Query(
        "SELECT SUM(calories) as calories, " +
                "SUM(protein) as protein, " +
                "SUM(fats) as fats, " +
                "SUM(carbohydrates) as carbohydrates" +
                " FROM meals WHERE date LIKE :date"
    )
    fun getNutrients(date: String): NutrientsEntity

    @Query("SELECT SUM(calories) as calories FROM meals WHERE date LIKE :date")
    fun getCalories(date: String): Int

    @Update
    suspend fun updateItem(item: MealEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg item: MealEntity)

    @Query("UPDATE meals SET position = :position WHERE id = :id")
    fun updatePositionByID(position: Int, id: Int)


    @Query("DELETE FROM meals WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE from meals")
    fun clear()
}
