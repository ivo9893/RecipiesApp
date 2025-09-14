package com.example.myrecipies.data.api

import com.example.myrecipies.data.api.dto.CategoriesDto
import com.example.myrecipies.data.api.dto.UnitsDto
import retrofit2.http.GET

interface UnitsApi {
    @GET("api/Units/all")
    suspend fun getUnits() : List<UnitsDto>
}