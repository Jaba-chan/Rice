package ru.evgenykuzakov.rice

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class MealsTest(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "database") val database: DatabaseNamesEnum,
    @ColumnInfo(name = "position") val position: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "calories") val calories: Int?,
    @ColumnInfo(name = "protein") val protein: Double,
    @ColumnInfo(name = "carbohydrates") val carbohydrates: Double,
    @ColumnInfo(name = "fats") val fats: Double
)

data class Nutrients(
    val calories: Int,
    val protein: Double,
    val fats: Double,
    val carbohydrates: Double
)