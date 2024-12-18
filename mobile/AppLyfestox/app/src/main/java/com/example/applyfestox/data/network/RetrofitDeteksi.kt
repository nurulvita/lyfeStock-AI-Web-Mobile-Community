package com.example.applyfestox.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitDeteksi {
    private const val BASE_URL = "https://application-lyfestox.1p9b391w1688.us-south.codeengine.appdomain.cloud/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
        }
}