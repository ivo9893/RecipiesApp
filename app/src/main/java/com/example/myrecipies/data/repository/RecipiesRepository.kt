package com.example.myrecipies.data.repository

import com.example.myrecipies.data.api.dto.ApiService
import com.example.myrecipies.data.db.dao.RecipiesDao
import com.example.myrecipies.data.mapper.toDomain
import com.example.myrecipies.data.mapper.toEntity
import com.example.myrecipies.domain.model.Recipe
import com.example.myrecipies.domain.repository.IRecipiesRepository
import javax.inject.Inject

class RecipiesRepository @Inject constructor(
    private val api: ApiService,
    private val dao: RecipiesDao
) : IRecipiesRepository {

    override suspend fun getAllRecipes(): List<Recipe> {
        return try {
            // Try fetching from network
            val remoteRecipes = api.getRecipies()
            val entities = remoteRecipes.map { it.toEntity() }

            // Save to local DB
            dao.insertRecipies(entities)

            // Return domain models
            entities.map { it.toDomain() }

        } catch (e: Exception) {
            System.err.println("Error fetching data: ${e.message}")
            val cachedEntities = dao.getAllRecipes()
            cachedEntities.map { it.toDomain() }
        }

    }

}