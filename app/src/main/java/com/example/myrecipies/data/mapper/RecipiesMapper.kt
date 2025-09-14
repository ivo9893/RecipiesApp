package com.example.myrecipies.data.mapper

import com.example.myrecipies.data.api.dto.RecipeDto
import com.example.myrecipies.data.db.entity.RecipeEntity
import com.example.myrecipies.domain.model.Recipe
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun RecipeEntity.toDomain(): Recipe = Recipe(
    id = id,
    name = name,
    description = description,
    cookTimeMinutes = cookTimeMinutes,
    prepTimeMinutes = prepTimeMinutes,
    servings = servings,
    createdAt = createdAt,
    updatedAt = updatedAt,
    authorId = authorId,
    instructions = instructions
)

fun RecipeDto.toEntity(): RecipeEntity = RecipeEntity(
    id = id,
    name = name,
    description = description,
    cookTimeMinutes = cookTimeMinutes,
    prepTimeMinutes = prepTimeMinutes,
    servings = servings,
    createdAt = parseDate(createdAt),
    updatedAt = updatedAt?.let { parseDate(it) },
    authorId = authorId,
    instructions = instructions
)

fun parseDate(dateStr: String): Date {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    formatter.timeZone = java.util.TimeZone.getTimeZone("UTC")
    return formatter.parse(dateStr) ?: throw IllegalArgumentException("Invalid date format")
}