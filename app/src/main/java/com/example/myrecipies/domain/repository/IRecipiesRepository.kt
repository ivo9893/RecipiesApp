package com.example.myrecipies.domain.repository

import com.example.myrecipies.domain.model.Recipe

interface IRecipiesRepository {

    suspend fun getAllRecipes(): List<Recipe>
}