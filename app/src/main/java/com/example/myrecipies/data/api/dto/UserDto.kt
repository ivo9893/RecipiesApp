package com.example.myrecipies.data.api.dto


data class CreateUserDto(

    val email : String,
    val password : String,
    val firstName : String,
    val LastName : String
)

data class User(
    val id : Int,
    val email : String,
    val name : String
)