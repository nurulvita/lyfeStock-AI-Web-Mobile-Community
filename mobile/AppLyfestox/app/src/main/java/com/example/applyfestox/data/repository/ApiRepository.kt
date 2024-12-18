package com.example.applyfestox.data.repository

import com.example.applyfestox.data.network.ApiService1
import com.example.applyfestox.data.network.JadwalPakanRequest
import com.example.applyfestox.data.network.KandangRequest
import com.example.applyfestox.data.network.MasukRequest
import com.example.applyfestox.data.network.OVKRequest
import com.example.applyfestox.data.network.PakanRequest
import com.example.applyfestox.data.network.RingkasanPerformaRequest
import com.example.applyfestox.data.network.UserRequest

class ApiRepository(private val apiService: ApiService1) {

    // Tabel User
    suspend fun getUsers() = apiService.getUsers()

    suspend fun createUser(request: UserRequest) = apiService.createUser(request)

    suspend fun updateUser(userId: Int, request: UserRequest) = apiService.updateUser(userId, request)

    suspend fun deleteUser(userId: Int) = apiService.deleteUser(userId)

    // Tabel Kandang
    suspend fun getKandang() = apiService.getKandang()

    suspend fun createKandang(request: KandangRequest) = apiService.createKandang(request)

    suspend fun updateKandang(kandangId: Int, request: KandangRequest) = apiService.updateKandang(kandangId, request)

    suspend fun deleteKandang(kandangId: Int) = apiService.deleteKandang(kandangId)

    // Tabel Jadwal Pakan
    suspend fun getJadwalPakan() = apiService.getJadwalPakan()

    suspend fun createJadwalPakan(request: JadwalPakanRequest) = apiService.createJadwalPakan(request)

    suspend fun updateJadwalPakan(jadwalId: Int, request: JadwalPakanRequest) = apiService.updateJadwalPakan(jadwalId, request)

    suspend fun deleteJadwalPakan(jadwalId: Int) = apiService.deleteJadwalPakan(jadwalId)

    // Tabel Pakan
    suspend fun getPakan() = apiService.getPakan()

    suspend fun createPakan(request: PakanRequest) = apiService.createPakan(request)

    suspend fun updatePakan(pakanId: Int, request: PakanRequest) = apiService.updatePakan(pakanId, request)

    suspend fun deletePakan(pakanId: Int) = apiService.deletePakan(pakanId)

    // Tabel OVK
    suspend fun getOVK() = apiService.getOVK()

    suspend fun createOVK(request: OVKRequest) = apiService.createOVK(request)

    suspend fun updateOVK(ovkId: Int, request: OVKRequest) = apiService.updateOVK(ovkId, request)

    suspend fun deleteOVK(ovkId: Int) = apiService.deleteOVK(ovkId)

    // Tabel Ringkasan Performa
    suspend fun getRingkasanPerforma() = apiService.getRingkasanPerforma()

    suspend fun createRingkasanPerforma(request: RingkasanPerformaRequest) = apiService.createRingkasanPerforma(request)

    suspend fun updateRingkasanPerforma(performaId: Int, request: RingkasanPerformaRequest) = apiService.updateRingkasanPerforma(performaId, request)

    suspend fun deleteRingkasanPerforma(performaId: Int) = apiService.deleteRingkasanPerforma(performaId)

    // Tabel OVK Masuk
    suspend fun getOVKMasuk() = apiService.getOVKMasuk()

    suspend fun createOVKMasuk(request: MasukRequest) = apiService.createOVKMasuk(request)

    suspend fun updateOVKMasuk(masukId: Int, request: MasukRequest) = apiService.updateOVKMasuk(masukId, request)

    suspend fun deleteOVKMasuk(masukId: Int) = apiService.deleteOVKMasuk(masukId)

    // Tabel Pakan Masuk
    suspend fun getPakanMasuk() = apiService.getPakanMasuk()

    suspend fun createPakanMasuk(request: MasukRequest) = apiService.createPakanMasuk(request)

    suspend fun updatePakanMasuk(masukId: Int, request: MasukRequest) = apiService.updatePakanMasuk(masukId, request)

    suspend fun deletePakanMasuk(masukId: Int) = apiService.deletePakanMasuk(masukId)
}

