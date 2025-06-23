package ru.evgenykuzakov.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "parentId") val parentId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "database") val database: DatabaseNamesEnumEntity,
    @ColumnInfo(name = "position") val position: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "calories") val calories: Int?,
    @ColumnInfo(name = "protein") val protein: Float,
    @ColumnInfo(name = "carbohydrates") val carbohydrates: Float,
    @ColumnInfo(name = "fats") val fats: Float
)
