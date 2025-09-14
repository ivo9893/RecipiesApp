package com.example.myrecipies.data.mapper

import com.example.myrecipies.data.api.dto.CategoriesDto
import com.example.myrecipies.data.api.dto.UnitsDto
import com.example.myrecipies.data.db.entity.CategoriesEntity
import com.example.myrecipies.data.db.entity.UnitsEntity
import com.example.myrecipies.domain.model.Category

fun UnitsEntity.toDomain(): com.example.myrecipies.domain.model.Unit = com.example.myrecipies.domain.model.Unit(
    id = id,
    name = name
)

fun UnitsDto.toEntity(): UnitsEntity = UnitsEntity(
    id = id,
    name = name
)