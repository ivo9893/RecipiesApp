package com.example.myrecipies.data.repository

import com.example.myrecipies.data.api.UnitsApi
import com.example.myrecipies.data.api.dto.ApiService
import com.example.myrecipies.data.db.dao.RecipiesDao
import com.example.myrecipies.data.db.dao.UnitsDao
import com.example.myrecipies.data.mapper.toDomain
import com.example.myrecipies.data.mapper.toEntity
import com.example.myrecipies.domain.model.Category
import com.example.myrecipies.domain.model.Unit
import com.example.myrecipies.domain.repository.IRecipiesRepository
import com.example.myrecipies.domain.repository.IUnitsRepository
import javax.inject.Inject

class UnitsRepository @Inject constructor(
    private val api: UnitsApi,
    private val dao: UnitsDao
) : IUnitsRepository {

    override suspend fun saveAllUnits(){
        try {
            // Try fetching from network
            val remoteUnits = api.getUnits()
            val entities = remoteUnits.map { it.toEntity() }

            dao.insertAllUnits(entities)

        } catch (e: Exception) {
            System.err.println("Error fetching data: ${e.message}")
        }
    }

    override suspend fun getAllUnits(): List<Unit> {
        try{
            val cachedEntities = dao.getAllUnits()
            return cachedEntities.map { it.toDomain() }
        } catch (e: Exception) {
            System.err.println("Error fetching data: ${e.message}")
            return emptyList()
        }
    }

}