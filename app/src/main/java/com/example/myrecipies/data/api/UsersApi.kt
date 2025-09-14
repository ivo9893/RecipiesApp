package com.example.myrecipies.data.api

import com.example.myrecipies.data.api.dto.CreateUserDto
import com.example.myrecipies.data.api.dto.LoginUserDto
import com.example.myrecipies.data.api.dto.LoginUserResponseDto
import com.example.myrecipies.data.api.dto.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsersApi {
    @POST("api/users/register")
    suspend fun registerUser(@Body userDto: CreateUserDto) : Response<User>

    @POST("api/Auth/login")
    suspend fun loginUser(@Body userDto: LoginUserDto) : Response<LoginUserResponseDto>

}