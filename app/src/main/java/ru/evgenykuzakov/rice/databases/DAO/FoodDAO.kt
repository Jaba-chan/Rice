package ru.evgenykuzakov.rice.databases.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.evgenykuzakov.rice.data.FoodInfo

@Dao
interface FoodDAO {
    @Query("SELECT * FROM foods")
    fun getAll(): List<FoodInfo>

    @Query("""
        SELECT * FROM foods
        WHERE name LIKE :query || '%'          
           OR name LIKE '% ' || :query || '%'    
           OR name LIKE '%' || :query || '%'    
        ORDER BY 
           CASE 
               WHEN name LIKE :query || '%' THEN 1        
               WHEN name LIKE '% ' || :query || '%' THEN 2 
               ELSE 3                                   
           END
    """)
    fun findByName(query: String): List<FoodInfo>

    @Query("SELECT * FROM foods WHERE id = :id LIMIT 1")
    fun findById(id: Int): FoodInfo

    @Insert
    fun insertAll(vararg foods: FoodInfo)

    @Delete
    fun delete(food: FoodInfo)
}