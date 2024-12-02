# Integrasi Flask API untuk Tim Mobile

Dokumentasi ini menjelaskan cara mengintegrasikan Flask API untuk prediksi gambar dengan aplikasi Android menggunakan Retrofit. Flask API di-host secara lokal dan diakses melalui **Ngrok**.

---

## **Informasi Dasar**

### **Base URL**
- URL dasar Flask API bersifat dinamis karena menggunakan **Ngrok** untuk membuka akses server lokal.  
- **Hubungi saya ml ops (roy) di wa grup atau personal** untuk mendapatkan URL Ngrok terbaru.  
- Contoh URL Ngrok:
https://abcd-1234.ngrok-free.app


### **Endpoint Diagnosa Kesehatan Ayam**
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


### **Endpoint untuk Cuaca**
 ```bash
POST /weather
 ```


### **Request**
- **Metode:** `POST`
- **Headers:**  
Content-Type: raw


- **Body:**  
- Ketik-an input di body-raw (json) seperti berikut:  
  ```
    {
  "cities": ["Sekupang", "Batam Center", "Nongsa", "Bengkong", "Tanjung Uncang", "Baloi", "Jakarta", "Surabaya", "Bandung", "Medan", "Denpasar", "Makassar", "Yogyakarta", "Semarang"]
   }
  ```
```

```
### **Contoh Respons**
```json

{
[
    {
        "city": "Sekupang",
        "temperature": 31.16,
        "humidity": 66,
        "windspeed": 2.77,
        "description": "overcast clouds"
    },
    {
        "city": "Batam Center",
        "temperature": 30.96,
        "humidity": 62,
        "windspeed": 3.09,
        "description": "scattered clouds"
    },
    {
        "city": "Nongsa",
        "temperature": 31.09,
        "humidity": 62,
        "windspeed": 3.09,
        "description": "scattered clouds"
    },
    {
        "city": "Bengkong",
        "temperature": 31.06,
        "humidity": 62,
        "windspeed": 3.09,
        "description": "scattered clouds"
    },
    {
        "city": "Tanjung Uncang",
        "temperature": 31.08,
        "humidity": 65,
        "windspeed": 2.9,
        "description": "overcast clouds"
    },
    {
        "city": "Baloi",
        "temperature": 31.04,
        "humidity": 62,
        "windspeed": 3.09,
        "description": "scattered clouds"
    },
    {
        "city": "Jakarta",
        "temperature": 28.1,
        "humidity": 76,
        "windspeed": 5.14,
        "description": "scattered clouds"
    },
    {
        "city": "Surabaya",
        "temperature": 26.68,
        "humidity": 85,
        "windspeed": 4.63,
        "description": "moderate rain"
    },
    {
        "city": "Bandung",
        "temperature": 26.38,
        "humidity": 99,
        "windspeed": 1.76,
        "description": "moderate rain"
    },
    {
        "city": "Medan",
        "temperature": 28.04,
        "humidity": 89,
        "windspeed": 6.17,
        "description": "scattered clouds"
    },
    {
        "city": "Denpasar",
        "temperature": 29.81,
        "humidity": 79,
        "windspeed": 4.12,
        "description": "scattered clouds"
    },
    {
        "city": "Makassar",
        "temperature": 31.04,
        "humidity": 70,
        "windspeed": 3.6,
        "description": "few clouds"
    },
    {
        "city": "Yogyakarta",
        "temperature": 25.64,
        "humidity": 91,
        "windspeed": 2.64,
        "description": "overcast clouds"
    },
    {
        "city": "Semarang",
        "temperature": 27.01,
        "humidity": 89,
        "windspeed": 2.06,
        "description": "moderate rain"
    }
]
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
