//package com.example.applyfestox.data.remote
//
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//object RetrofitClient {
//
//    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
////    private const val BASE_URL = "https://5954-2404-c0-5110-00-278f-a3cf.ngrok-free.app/weather"
//
//    // Create an OkHttpClient instance (optional)
//    private val client = OkHttpClient.Builder().build()
//
//    // Create Retrofit instance
//    val retrofit: Retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .client(client)
//        .build()
//}