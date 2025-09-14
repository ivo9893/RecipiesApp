package com.example.myrecipies.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myrecipies.data.db.entity.CategoriesEntity
import com.example.myrecipies.data.db.entity.UnitsEntity

@Dao
interface UnitsDao {
    @Query("select * from Units")
    suspend fun getAllUnits(): List<UnitsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnits(unit: UnitsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUnits(units: List<UnitsEntity>)
}
