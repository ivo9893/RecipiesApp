package com.example.myrecipies.data.api.dto

import retrofit2.http.GET

interface ApiService {
    @GET("api/recipe/all")
    suspend fun getRecipies() : List<RecipeDto>
}