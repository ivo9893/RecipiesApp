package com.example.myrecipies.data.repository

import com.example.myrecipies.data.api.CategoriesApi
import com.example.myrecipies.data.db.dao.CategoriesDao
import com.example.myrecipies.data.mapper.toDomain
import com.example.myrecipies.data.mapper.toEntity
import com.example.myrecipies.domain.model.Category
import com.example.myrecipies.domain.repository.ICategoriesRepository
import javax.inject.Inject

class CategoriesRepository @Inject constructor(
    private val api: CategoriesApi,
    private val dao: CategoriesDao
)  : ICategoriesRepository  {

    override suspend fun saveAllCategories(){
        try {
            // Try fetching from network
            val remoteCategories = api.getCategories()
            val entities = remoteCategories.map { it.toEntity() }

            dao.insertAllCategories(entities)

        } catch (e: Exception) {
            System.err.println("Error fetching data: ${e.message}")
        }
    }

    override suspend fun getAllCategories(): List<Category> {
        try{
            val cachedEntities = dao.getAllCategories()
            return cachedEntities.map { it.toDomain() }
        } catch (e: Exception) {
            System.err.println("Error fetching data: ${e.message}")
            return emptyList()
        }
    }


}