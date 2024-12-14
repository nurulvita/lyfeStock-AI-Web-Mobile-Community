//package com.example.applyfestox.database
//
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import retrofit2.Call
//import retrofit2.http.Field
//import retrofit2.http.FormUrlEncoded
//import retrofit2.http.GET
//import retrofit2.http.Multipart
//import retrofit2.http.POST
//import retrofit2.http.Part
//
//interface ApiService {
//    // Endpoint untuk login
//    @FormUrlEncoded
//    @POST("checkLogin.php")
//    fun login(
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): Call<ApiResponse<Users>>
//
//    // Endpoint untuk register
//    @Multipart
//    @POST("registerdata.php")
//    fun register(
//        @Part("email") email: RequestBody,
//        @Part("nama_pengguna") namaPengguna: RequestBody,
//        @Part("password") password: RequestBody,
//        @Part("tanggal_lahir") tanggalLahir: RequestBody,
//        @Part photo: MultipartBody.Part?
//    ): Call<ApiResponse<Unit>>
//
//    @GET("users")
//    fun getUsers(): Call<List<Users>>
//}
