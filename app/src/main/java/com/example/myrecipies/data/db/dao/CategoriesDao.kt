package com.example.myrecipies.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myrecipies.data.db.entity.CategoriesEntity

@Dao
interface CategoriesDao {
    @Query("select * from Categories")
    suspend fun getAllCategories(): List<CategoriesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoriesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(categories: List<CategoriesEntity>)
}