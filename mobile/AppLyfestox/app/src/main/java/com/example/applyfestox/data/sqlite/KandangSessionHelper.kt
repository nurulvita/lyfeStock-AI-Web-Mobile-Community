package com.example.applyfestox.data.sqlite

import android.content.Context
import android.content.SharedPreferences

class KandangSessionHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    fun saveKandangData(namaKandang: String, jumlahPopulasiAyam: String, alamatKandang: String, jenisKandang: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_NAMA_KANDANG, namaKandang)
        editor.putString(KEY_JUMLAH_POPULASI_AYAM, jumlahPopulasiAyam)
        editor.putString(KEY_ALAMAT_KANDANG, alamatKandang)
        editor.putString(KEY_JENIS_KANDANG, jenisKandang)
        editor.apply()
    }

    fun getKandangData(): KandangData? {
        return KandangData(
            namaKandang = sharedPreferences.getString(KEY_NAMA_KANDANG, "") ?: "",
            jumlahPopulasiAyam = sharedPreferences.getString(KEY_JUMLAH_POPULASI_AYAM, "") ?: "",
            alamatKandang = sharedPreferences.getString(KEY_ALAMAT_KANDANG, "") ?: "",
            jenisKandang = sharedPreferences.getString(KEY_JENIS_KANDANG, "") ?: ""
        )
    }

    companion object {
        private const val SHARED_PREF_NAME = "kandang_session"
        private const val KEY_NAMA_KANDANG = "nama_kandang"
        private const val KEY_JUMLAH_POPULASI_AYAM = "jumlah_populasi_ayam"
        private const val KEY_ALAMAT_KANDANG = "alamat_kandang"
        private const val KEY_JENIS_KANDANG = "jenis_kandang"
    }
}

data class KandangData(
    val namaKandang: String,
    val jumlahPopulasiAyam: String,
    val alamatKandang: String,
    val jenisKandang:String
)