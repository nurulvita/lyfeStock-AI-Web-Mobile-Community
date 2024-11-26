# Integrasi Flask API untuk Tim Mobile

Dokumentasi ini menjelaskan cara mengintegrasikan Flask API untuk prediksi gambar dengan aplikasi Android menggunakan Retrofit. Flask API di-host secara lokal dan diakses melalui **Ngrok**.

---

## **Informasi Dasar**

### **Base URL**
- URL dasar Flask API bersifat dinamis karena menggunakan **Ngrok** untuk membuka akses server lokal.  
- **Hubungi saya ml ops (roy) di wa grup atau personal** untuk mendapatkan URL Ngrok terbaru.  
- Contoh URL Ngrok:
https://abcd-1234.ngrok-free.app


### **Endpoint**
 ```bash
POST /predict
 ```


### **Request**
- **Metode:** `POST`
- **Headers:**  
Content-Type: multipart/form-data


- **Body:**  
- Kirim file gambar menggunakan form-data dengan key-value berikut:  
  ```
  Key: file
  Value: File gambar (contoh: cocci1130.jpg)
  ```

### **Contoh Respons**
```json
{
"class": "Coccidiosis",
"confidence": 0.996834933757782
}
```

## **Persiapan untuk Integrasi Mobile**
Dependensi

Tambahkan dependensi berikut ke file build.gradle di proyek Anda:

gradle
```
dependencies {
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.github.bumptech.glide:glide:4.15.1' // Untuk pratinjau gambar
    implementation 'androidx.room:room-runtime:2.5.1' // Untuk database SQLite
    kapt 'androidx.room:room-compiler:2.5.1'
}
```

### **Langkah Implementasi**
1. Buat Interface API dengan Retrofit:
   
kotlin
```
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("predict")
    fun predict(@Part file: MultipartBody.Part): Call<PredictionResponse>
}

data class PredictionResponse(
    val `class`: String,
    val confidence: Double
)
```
2. Buat Retrofit Instance:
   
kotlin
```
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://abcd-1234.ngrok-free.app" // URL Ngrok terbaru

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
```

3. Fungsi untuk Upload Gambar
   
kotlin
```
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

fun uploadImage(
    imageFile: File,
    onSuccess: (String, Double) -> Unit,
    onFailure: (String) -> Unit
) {
    val requestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
    val body = MultipartBody.Part.createFormData("file", imageFile.name, requestBody)

    RetrofitInstance.api.predict(body).enqueue(object : Callback<PredictionResponse> {
        override fun onResponse(
            call: Call<PredictionResponse>,
            response: Response<PredictionResponse>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    onSuccess(it.`class`, it.confidence)
                }
            } else {
                onFailure("Error: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
            onFailure("Failure: ${t.message}")
        }
    })
}
```
4. Panggil Fungsi di Activity/Fragment:
   
kotlin
```
val imageFile = File(filePath) // Path gambar yang dipilih pengguna
uploadImage(imageFile,
    onSuccess = { predictionClass, confidence ->
        // Tampilkan hasil prediksi
        Log.d("Prediction", "Class: $predictionClass, Confidence: $confidence")
    },
    onFailure = { error ->
        // Tampilkan error
        Log.e("Prediction", error)
    }
)
```

## **Langkah Pengujian**
Jalankan Flask API:
(Hubungi ml ops agar menghidupkan Flask API)

dan minta URL Ngrok ke ml ops:
dapatkan URL terbaru untuk pengujian.

### **Uji Aplikasi Mobile:**

1. Pastikan gambar dapat diunggah melalui UI aplikasi.
2. Tampilkan hasil prediksi di aplikasi.
3. Simpan hasil ke database SQLite untuk digunakan kembali.

## Jika ada pertanyaan atau memerlukan URL baru, hubungi saya karena saya yang mengelola server Flask.
