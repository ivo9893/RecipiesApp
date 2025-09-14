package com.example.myrecipies.domain.model

import com.google.gson.Gson


data class ErrorResponse (

    val status: Int?,
    val message: String?
) {
    companion object{
        fun fromJson(json : String) : ErrorResponse? {
            return try{
                Gson().fromJson(json, ErrorResponse::class.java)
            }catch (e: Exception){
                null
            }
        }
    }
}