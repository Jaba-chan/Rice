package ru.evgenykuzakov.rice.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "muscle_groups",
    foreignKeys = [ForeignKey(
        entity = Exercises::class,
        parentColumns = ["exercise"],
        childColumns = ["exercise_name"])])
data class MuscleGroups(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "exercise_name") val exercisesName: String,
    @ColumnInfo(name = "muscle") val muscle: Int
    )