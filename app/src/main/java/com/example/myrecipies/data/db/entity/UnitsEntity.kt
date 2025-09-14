package com.example.myrecipies.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Units")
data class UnitsEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "Name")
    val name: String
)