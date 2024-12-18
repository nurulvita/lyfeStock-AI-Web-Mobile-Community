//package com.example.applyfestox.database
//
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//object RetrofitInstance {
//
//    private val client: OkHttpClient by lazy {
//        val logging = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//
//        OkHttpClient.Builder()
//            .addInterceptor(logging) // Log setiap request dan response
//            .addInterceptor { chain ->
//                val request = chain.request().newBuilder()
//                    .addHeader("Cookie", "__test=5aeac5013d2cf873e3f3b38835cc2ab7") // Tambahkan cookie jika diperlukan
//                    .build()
//                chain.proceed(request)
//            }
//            .build()
//    }
//
//    private val retrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl("http://lyfestox11.fwh.is/")
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val api: ApiService by lazy {
//        retrofit.create(ApiService::class.java)
//    }
//}
