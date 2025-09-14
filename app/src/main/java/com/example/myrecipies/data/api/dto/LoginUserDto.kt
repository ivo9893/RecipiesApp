package com.example.myrecipies.data.api.dto

data class LoginUserDto (
    val email : String,
    val password : String
)

data class LoginUserResponseDto(
    val accessToken : String,
    val refreshToken : String,
    val accessTokenExpiration : String,
    val userId : Int,
    val email : String
)
