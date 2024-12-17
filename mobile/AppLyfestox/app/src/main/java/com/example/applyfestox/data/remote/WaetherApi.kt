//package com.example.applyfestox.data.remote
//
//import com.example.applyfestox.data.model.WeatherResponse
//import retrofit2.Call
//import retrofit2.http.Body
//import retrofit2.http.POST
//
//interface WeatherApi {
////    @GET("weather")
////    suspend fun getWeather(
////        @Query("q") city: String,
////        @Query("units") units: String = "metric",
////        @Query("appid") apiKey: String
////    ): WeatherResponse
//
//    @POST("/weather")
//    fun getWeather(
//        @Body cities: WeatherRequest
//    ): Call<List<WeatherResponse>>
//}
//
//data class WeatherRequest(
//    val cities: List<String>
//)