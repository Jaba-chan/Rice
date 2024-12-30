package ru.evgenykuzakov.rice.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "foods", indices = [Index(value = ["name"])])
data class FoodInfo(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,                            // название товара
    @ColumnInfo(name = "producer") val producer: String?,                   // производитель
    @ColumnInfo(name = "product_category") val productCategory: String?,    // категория товара
    @ColumnInfo(name = "extra_info") val extraInfo: String?,                // описание товара
    @ColumnInfo(name = "number_of_servings") val numberOfServings: Int?,    // количество порций
    @ColumnInfo(name = "source") val source: String,                        // откуда спарсил \ ресурс
    @ColumnInfo(name = "net_weight") val netWeight: Float,                  // нетто вес(без упаковки)
    @ColumnInfo(name = "calories") val calories: Int?,                      // калории
    @ColumnInfo(name = "protein") val protein: Float,                       // белок
    @ColumnInfo(name = "carbohydrates") val carbohydrates: Float,           // углеводы
    @ColumnInfo(name = "fats") val fats: Float                              // жиры
)

