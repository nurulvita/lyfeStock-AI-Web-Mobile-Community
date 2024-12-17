package com.example.applyfestox.data.network

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse
}
