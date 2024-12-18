//package com.example.applyfestox.data.network
//
//import androidx.media3.common.util.Log
//import com.example.applyfestox.data.model.WeatherResponse
//import com.example.applyfestox.data.remote.RetrofitClient
//import com.example.applyfestox.data.remote.WeatherRequest
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//fun getWeatherForecast() {
//    val cities = listOf("Barelang", "Batam Center", "Jakarta")
//    val weatherRequest = WeatherRequest()
//    weatherRequest.cities = cities
//
//    val apiService = RetrofitClient.getRetrofitInstance().create(ApiService::class.java)
//    val call = apiService.getWeatherForecast(weatherRequest)
//
//    call.enqueue(object : Callback<List<WeatherResponse>> {
//        override fun onResponse(call: Call<List<WeatherResponse>>, response: Response<List<WeatherResponse>>) {
//            if (response.isSuccessful) {
//                val weatherResponses = response.body()
//                // Tampilkan data cuaca
//                weatherResponses?.forEach { weather ->
//                    Log.d("Weather", "City: ${weather.city} Temp: ${weather.temperature}")
//                }
//            }
//        }
//
//        override fun onFailure(call: Call<List<WeatherResponse>>, t: Throwable) {
//            // Tangani kegagalan
//            Log.e("Error", "Request failed", t)
//        }
//    })
//}