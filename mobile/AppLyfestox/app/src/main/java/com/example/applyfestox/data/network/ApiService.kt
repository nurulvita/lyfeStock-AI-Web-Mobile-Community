package com.example.applyfestox.data.network

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService1 {

    // Tabel User
    @GET("/user")
    suspend fun getUsers(): List<UserResponse>

    @POST("/user")
    suspend fun createUser(@Body request: UserRequest): UserResponse

    @PUT("/user/{id}")
    suspend fun updateUser(@Path("id") userId: Int, @Body request: UserRequest): UserResponse

    @DELETE("/user/{id}")
    suspend fun deleteUser(@Path("id") userId: Int): ApiResponse

    // Tabel Kandang
    @GET("/kandang")
    suspend fun getKandang(): List<KandangResponse>

    @POST("/kandang")
    suspend fun createKandang(@Body request: KandangRequest): KandangResponse

    @PUT("/kandang/{id}")
    suspend fun updateKandang(@Path("id") kandangId: Int, @Body request: KandangRequest): KandangResponse

    @DELETE("/kandang/{id}")
    suspend fun deleteKandang(@Path("id") kandangId: Int): ApiResponse

    // Tabel Jadwal Pakan
    @GET("/jadwal-pakan")
    suspend fun getJadwalPakan(): List<JadwalPakanResponse>

    @POST("/jadwal-pakan")
    suspend fun createJadwalPakan(@Body request: JadwalPakanRequest): JadwalPakanResponse

    @PUT("/jadwal-pakan/{id}")
    suspend fun updateJadwalPakan(@Path("id") jadwalId: Int, @Body request: JadwalPakanRequest): JadwalPakanResponse

    @DELETE("/jadwal-pakan/{id}")
    suspend fun deleteJadwalPakan(@Path("id") jadwalId: Int): ApiResponse

    // Tabel Pakan
    @GET("/pakan")
    suspend fun getPakan(): List<PakanResponse>

    @POST("/pakan")
    suspend fun createPakan(@Body request: PakanRequest): PakanResponse

    @PUT("/pakan/{id}")
    suspend fun updatePakan(@Path("id") pakanId: Int, @Body request: PakanRequest): PakanResponse

    @DELETE("/pakan/{id}")
    suspend fun deletePakan(@Path("id") pakanId: Int): ApiResponse

    // Tabel OVK
    @GET("/ovk")
    suspend fun getOVK(): List<OVKResponse>

    @POST("/ovk")
    suspend fun createOVK(@Body request: OVKRequest): OVKResponse

    @PUT("/ovk/{id}")
    suspend fun updateOVK(@Path("id") ovkId: Int, @Body request: OVKRequest): OVKResponse

    @DELETE("/ovk/{id}")
    suspend fun deleteOVK(@Path("id") ovkId: Int): ApiResponse

    // Tabel Ringkasan Performa
    @GET("/ringkasan-performa")
    suspend fun getRingkasanPerforma(): List<RingkasanPerformaResponse>

    @POST("/ringkasan-performa")
    suspend fun createRingkasanPerforma(@Body request: RingkasanPerformaRequest): RingkasanPerformaResponse

    @PUT("/ringkasan-performa/{id}")
    suspend fun updateRingkasanPerforma(@Path("id") performaId: Int, @Body request: RingkasanPerformaRequest): RingkasanPerformaResponse

    @DELETE("/ringkasan-performa/{id}")
    suspend fun deleteRingkasanPerforma(@Path("id") performaId: Int): ApiResponse

    // Tabel OVK Masuk
    @GET("/ovk-masuk")
    suspend fun getOVKMasuk(): List<MasukResponse>

    @POST("/ovk-masuk")
    suspend fun createOVKMasuk(@Body request: MasukRequest): MasukResponse

    @PUT("/ovk-masuk/{id}")
    suspend fun updateOVKMasuk(@Path("id") masukId: Int, @Body request: MasukRequest): MasukResponse

    @DELETE("/ovk-masuk/{id}")
    suspend fun deleteOVKMasuk(@Path("id") masukId: Int): ApiResponse

    // Tabel Pakan Masuk
    @GET("/pakan-masuk")
    suspend fun getPakanMasuk(): List<MasukResponse>

    @POST("/pakan-masuk")
    suspend fun createPakanMasuk(@Body request: MasukRequest): MasukResponse

    @PUT("/pakan-masuk/{id}")
    suspend fun updatePakanMasuk(@Path("id") masukId: Int, @Body request: MasukRequest): MasukResponse

    @DELETE("/pakan-masuk/{id}")
    suspend fun deletePakanMasuk(@Path("id") masukId: Int): ApiResponse
}
