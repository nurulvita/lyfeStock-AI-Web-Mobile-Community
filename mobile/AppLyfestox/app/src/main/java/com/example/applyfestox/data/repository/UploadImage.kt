package com.example.applyfestox.data.repository

import android.util.Log
import com.example.applyfestox.data.network.PredictionResponse
import com.example.applyfestox.data.network.RetrofitDeteksi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

fun uploadImage(
    imageFile: File,
    onSuccess: (String, Double) -> Unit,
    onFailure: (String) -> Unit
) {
    if (!imageFile.exists() || !imageFile.canRead()) {
        onFailure("File tidak valid atau tidak dapat dibaca.")
        return
    }

    try {
        val requestBody = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", imageFile.name, requestBody)

        RetrofitDeteksi.api.predictChickenDisease(body).enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(
                call: Call<PredictionResponse>,
                response: Response<PredictionResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { prediction ->
                        onSuccess(prediction.`class`, prediction.confidence)
                    } ?: run {
                        onFailure("Respon API kosong.")
                    }
                } else {
                    val errorMsg = "Error ${response.code()}: ${response.errorBody()?.string() ?: "Tidak diketahui"}"
                    Log.e("UploadImage", errorMsg)
                    onFailure(errorMsg)
                }
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                val failureMessage = "Gagal mengunggah: ${t.localizedMessage ?: "Kesalahan tidak diketahui"}"
                Log.e("UploadImage", failureMessage, t)
                onFailure(failureMessage)
            }
        })
    } catch (e: Exception) {
        val exceptionMessage = "Exception: ${e.localizedMessage ?: "Tidak diketahui"}"
        Log.e("UploadImage", exceptionMessage, e)
        onFailure(exceptionMessage)
    }
}
