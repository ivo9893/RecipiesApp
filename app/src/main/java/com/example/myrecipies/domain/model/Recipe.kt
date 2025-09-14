package com.example.myrecipies.domain.model

import java.util.Date

data class Recipe(
    val id: Int,
    val name: String,
    val description: String,
    val cookTimeMinutes: Int,
    val prepTimeMinutes: Int,
    val servings: Int,
    val createdAt: Date,
    val updatedAt: Date?,
    val authorId: Int,
    val instructions: String
)