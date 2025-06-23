package ru.evgenykuzakov.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.evgenykuzakov.database.DatabaseNamesEnum
import ru.evgenykuzakov.database.model.MealEntity
import ru.evgenykuzakov.database.model.NutrientsEntity

@Dao
interface UserMealDao {

    @Query("SELECT * FROM meals")
    fun getAll(): List<MealEntity>

    @Query(
        "SELECT * FROM meals WHERE `database` =:database " +
                "AND date LIKE :date ORDER BY position"
    )
    fun getBreakfastData(
        database: DatabaseNamesEnum = DatabaseNamesEnum.BREAKFAST_DATABASE,
        date: String
    ): MutableList<MealEntity>

    @Query(
        "SELECT * FROM meals WHERE `database` =:database " +
                "AND date LIKE :date ORDER BY position"
    )
    fun getLunchData(
        database: DatabaseNamesEnum = DatabaseNamesEnum.LUNCH_DATABASE,
        date: String
    ): MutableList<MealEntity>

    @Query(
        "SELECT * FROM meals WHERE `database` =:database " +
                "AND date LIKE :date ORDER BY position"
    )
    fun getDinnerData(
        database: DatabaseNamesEnum = DatabaseNamesEnum.DINNER_DATABASE,
        date: String
    ): MutableList<MealEntity>

    @Query(
        "SELECT * FROM meals WHERE `database` =:database " +
                "AND date LIKE :date ORDER BY position"
    )
    fun getExtraMealsData(
        database: DatabaseNamesEnum = DatabaseNamesEnum.EXTRA_MEALS_DATABASE,
        date: String
    ): MutableList<MealEntity>

    @Query(
        "SELECT MAX(position) FROM meals WHERE `database` =:database " +
                "AND date LIKE :date"
    )
    fun getBreakfastLastPosition(
        database: DatabaseNamesEnum = DatabaseNamesEnum.BREAKFAST_DATABASE,
        date: String
    ): Int

    @Query(
        "SELECT MAX(position) FROM meals WHERE `database` =:database " +
                "AND date LIKE :date"
    )
    fun getLunchLastPosition(
        database: DatabaseNamesEnum = DatabaseNamesEnum.LUNCH_DATABASE,
        date: String
    ): Int

    @Query(
        "SELECT MAX(position) FROM meals WHERE `database` =:database " +
                "AND date LIKE :date"
    )
    fun getDinnerLastPosition(
        database: DatabaseNamesEnum = DatabaseNamesEnum.DINNER_DATABASE,
        date: String
    ): Int

    @Query(
        "SELECT MAX(position) FROM meals WHERE `database` =:database " +
                "AND date LIKE :date"
    )
    fun getExtraMealsLastPosition(
        database: DatabaseNamesEnum = DatabaseNamesEnum.EXTRA_MEALS_DATABASE,
        date: String
    ): Int

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

    @Query("UPDATE meals SET `database` = :database WHERE id = :id")
    fun updateDatabaseByID(id: Int, database: DatabaseNamesEnum)

    @Query("DELETE FROM meals WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE from meals")
    fun clear()
}
