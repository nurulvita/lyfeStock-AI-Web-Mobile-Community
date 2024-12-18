package com.example.applyfestox.data.network

data class AuthRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String,
    val user: UserResponse
)

data class UserRequest(
    val email: String,
    val nama_pengguna: String,
    val ttl: String?,       // Format: "YYYY-MM-DD"
    val alamat: String?,
    val photo: String?,
    val password: String
)

data class UserResponse(
    val user_id: Int,
    val email: String,
    val nama_pengguna: String,
    val ttl: String?,
    val alamat: String?,
    val photo: String?,
    val password: String
)

data class ApiResponse(
    val success: Boolean,
    val message: String
)

data class KandangRequest(
    val nama_kandang: String,
    val lokasi: String,
    val periode: Int,
    val jumlah_ayam: Int,
    val umur_ayam: Int,
    val pakan_tersedia: Int,
    val jadwal_pakan: String, // Simpan JSON sebagai String
    val status: String        // "Aktif" atau "Rehat"
)

data class KandangResponse(
    val id_kandang: Int,
    val nama_kandang: String,
    val lokasi: String,
    val periode: Int,
    val jumlah_ayam: Int,
    val umur_ayam: Int,
    val pakan_tersedia: Int,
    val jadwal_pakan: String, // Simpan JSON sebagai String
    val status: String
)

data class JadwalPakanRequest(
    val time_pagi: String,   // Format: "HH:mm:ss"
    val time_siang: String,  // Format: "HH:mm:ss"
    val time_malam: String,  // Format: "HH:mm:ss"
    val created_at: String   // Timestamp
)

data class JadwalPakanResponse(
    val jadwal_id: Int,
    val time_pagi: String,
    val time_siang: String,
    val time_malam: String,
    val created_at: String
)

data class PakanRequest(
    val nama_pakan: String,
    val jenis_pakan: String,
    val satuan: String,
    val harga: Double
)

data class PakanResponse(
    val id_pakan: Int,
    val nama_pakan: String,
    val jenis_pakan: String,
    val satuan: String,
    val harga: Double
)


data class OVKRequest(
    val jenis_ovk: String,
    val satuan: String,
    val harga: Double
)

data class OVKResponse(
    val id_ovk: Int,
    val jenis_ovk: String,
    val satuan: String,
    val harga: Double
)


data class RingkasanPerformaRequest(
    val id_kandang: Int,
    val fcr_target: Float,
    val fcr_actual: Float,
    val deplesi: Float,
    val bobot_total: Float,
    val tfr: Float,
    val ddg: Float
)

data class RingkasanPerformaResponse(
    val id_performa: Int,
    val id_kandang: Int,
    val fcr_target: Float,
    val fcr_actual: Float,
    val deplesi: Float,
    val bobot_total: Float,
    val tfr: Float,
    val ddg: Float
)

data class MasukRequest(
    val id_item: Int,
    val tanggal_masuk: String,  // Format: "YYYY-MM-DD"
    val kuantitas: Int,
    val harga_total: Double,
    val harga_satuan: Double
)

data class MasukResponse(
    val id_masuk: Int,
    val id_item: Int,
    val tanggal_masuk: String,
    val kuantitas: Int,
    val harga_total: Double,
    val harga_satuan: Double
)
