package com.example.myrecipies.domain.repository

import com.example.myrecipies.domain.model.Category

interface ICategoriesRepository {

    suspend fun saveAllCategories()

    suspend fun getAllCategories() : List<Category>
}