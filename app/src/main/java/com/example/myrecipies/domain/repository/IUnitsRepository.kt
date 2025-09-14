package com.example.myrecipies.domain.repository

import com.example.myrecipies.domain.model.Category
import com.example.myrecipies.domain.model.Unit


interface IUnitsRepository {

    suspend fun saveAllUnits()

    suspend fun getAllUnits() : List<Unit>
}