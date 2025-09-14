package com.example.myrecipies.data.api.dto

data class RecipeDto(
    val id: Int,
    val name: String,
    val description: String,
    val cookTimeMinutes: Int,
    val prepTimeMinutes: Int,
    val servings: Int,
    val createdAt: String,
    val updatedAt: String?,
    val authorId: Int,
    val instructions: String
)