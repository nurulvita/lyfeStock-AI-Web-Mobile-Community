package com.example.applyfestox.data.network

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("predict")
    fun predictChickenDisease(
        @Part file: MultipartBody.Part
    ): Call<PredictionResponse>

    @POST("weather")
    suspend fun getWeather(@Body request: WeatherRequest): List<WeatherResponse>
}

data class PredictionResponse(
    val `class`: String,
    val confidence: Double
)

data class WeatherRequest(
    val cities: List<String>
)

data class WeatherResponse(
    val city: String,
    val timestamp: String,
    val temperature: Double,
    val humidity: Int,
    val windspeed: Double,
    val description: String
)

