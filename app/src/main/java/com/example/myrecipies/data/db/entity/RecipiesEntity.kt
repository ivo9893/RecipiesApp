package com.example.myrecipies.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "Recipies")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "cook_time_minutes")
    val cookTimeMinutes: Int,

    @ColumnInfo(name = "prep_time_minutes")
    val prepTimeMinutes: Int,

    @ColumnInfo(name = "servings")
    val servings: Int,

    @ColumnInfo(name = "created_at")
    val createdAt: Date,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Date? = null,

    @ColumnInfo(name = "authorId")
    val authorId: Int,

    @ColumnInfo(name = "instructions")
    val instructions: String
)