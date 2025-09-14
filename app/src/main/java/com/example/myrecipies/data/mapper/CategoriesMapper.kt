package com.example.myrecipies.data.mapper

import com.example.myrecipies.data.api.dto.CategoriesDto
import com.example.myrecipies.data.db.entity.CategoriesEntity
import com.example.myrecipies.domain.model.Category


fun CategoriesEntity.toDomain(): Category = Category(
    id = id,
    name = name
)

fun CategoriesDto.toEntity(): CategoriesEntity = CategoriesEntity(
    id = id,
    name = name
)