package com.example.myrecipies.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categories")
data class CategoriesEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "Name")
    val name: String
)