package com.example.myrecipies.data.api

import com.example.myrecipies.data.api.dto.CategoriesDto
import retrofit2.http.GET

interface CategoriesApi {
    @GET("api/categories/all")
    suspend fun getCategories() : List<CategoriesDto>

}