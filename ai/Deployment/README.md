# Integrasi Flask API untuk Tim Mobile

Dokumentasi ini menjelaskan cara mengintegrasikan Flask API untuk prediksi gambar dan prediksi cuaca dengan aplikasi Android menggunakan Retrofit. Flask API di-host secara lokal dan diakses melalui **Ngrok**.

---

## **Informasi Dasar**

### **Base URL**
- URL dasar Flask API bersifat dinamis karena menggunakan **Ngrok** untuk membuka akses server lokal dan URL dapat berubah setiap kali server di-restart.  
- **Hubungi saya ml ops (roy) di wa grup atau personal** untuk mendapatkan URL Ngrok terbaru.  
- Contoh URL Ngrok:
https://abcd-1234.ngrok-free.app


### **Endpoint Prediksi Diagnosa Kesehatan Ayam**
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


### **Endpoint untuk Prediksi Cuaca**
 ```bash
POST /weather
 ```


### **Request**
- **Metode:** `POST`
- **Headers:**  
Content-Type: raw


- **Body:**  
- Ketik-an input di raw (json) seperti berikut:  
  ```
  {
  "cities": ["Barelang", "Batam Center", "Nongsa", "Tanjung Uncang", "Sekupang", "Jakarta", "Surabaya", "Bandung", "Yogyakarta", "Samarinda"]
  }
  ```



### **Contoh Respons**
```json

[
    {
        "city": "Barelang",
        "timestamp": "2024-12-02 12:00:00",
        "temperature": 29.84,
        "humidity": 70,
        "windspeed": 1.95,
        "description": "light rain"
    },
    {
        "city": "Barelang",
        "timestamp": "2024-12-02 15:00:00",
        "temperature": 28.34,
        "humidity": 75,
        "windspeed": 2.32,
        "description": "light rain"
    },
    {
        "city": "Barelang",
        "timestamp": "2024-12-02 18:00:00",
        "temperature": 25.84,
        "humidity": 82,
        "windspeed": 6.77,
        "description": "moderate rain"
    },
    {
        "city": "Barelang",
        "timestamp": "2024-12-02 21:00:00",
        "temperature": 25.6,
        "humidity": 79,
        "windspeed": 7.02,
        "description": "light rain"
    },
    {
        "city": "Barelang",
        "timestamp": "2024-12-03 00:00:00",
        "temperature": 26.44,
        "humidity": 75,
        "windspeed": 5.17,
        "description": "overcast clouds"
    },
```


## **Persiapan untuk Integrasi Mobile**
Dependensi

Tambahkan dependensi berikut ke file build.gradle di proyek Anda:

gradle
```
dependencies {
implementation "com.squareup.retrofit2:retrofit:2.9.0"
implementation "com.squareup.retrofit2:converter-gson:2.9.0"
implementation "androidx.room:room-runtime:2.5.0"
kapt "androidx.room:room-compiler:2.5.0"
implementation "androidx.compose.ui:ui:1.5.0"
implementation "androidx.compose.material:material:1.5.0"
implementation "androidx.compose.ui:ui-tooling-preview:1.5.0"

}
```

### **Langkah Implementasi**
1. Buat Interface API dengan Retrofit:
   
kotlin
```
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Multipart
    @POST("/predict")
    fun predictChickenDisease(
        @Part file: MultipartBody.Part
    ): Call<PredictionResponse>

    @POST("/weather")
    fun getWeather(
        @Body cities: WeatherRequest
    ): Call<List<WeatherResponse>>
}

data class PredictionResponse(
    val `class`: String,
    val confidence: Double
)

data class WeatherRequest(
    val cities: List<String>
)

data class WeatherResponse(
    val city: String,
    val timestamp: String,
    val temperature: Double,
    val humidity: Int,
    val windspeed: Double,
    val description: String
)

```
2. Buat Retrofit Instance:
   
kotlin
```
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://abcd-1234.ngrok-free.app" #url yang ml ops
kasih

    val apiService: ApiService by lazy {
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
5. Prediksi Cuaca
   Untuk mengirim request cuaca ke Flask API:
   
kotlin
```
fun getWeatherForecast() {
    val cities = listOf("Barelang", "Batam Center", "Jakarta")
    val weatherRequest = WeatherRequest()
    weatherRequest.cities = cities

    val apiService = RetrofitClient.getRetrofitInstance().create(ApiService::class.java)
    val call = apiService.getWeatherForecast(weatherRequest)

    call.enqueue(object : Callback<List<WeatherResponse>> {
        override fun onResponse(call: Call<List<WeatherResponse>>, response: Response<List<WeatherResponse>>) {
            if (response.isSuccessful) {
                val weatherResponses = response.body()
                // Tampilkan data cuaca
                weatherResponses?.forEach { weather ->
                    Log.d("Weather", "City: ${weather.city} Temp: ${weather.temperature}")
                }
            }
        }

        override fun onFailure(call: Call<List<WeatherResponse>>, t: Throwable) {
            // Tangani kegagalan
            Log.e("Error", "Request failed", t)
        }
    })
}
```


## **Langkah Pengujian**
Jalankan Flask API:
(Hubungi ml ops agar menghidupkan Flask API)

dan minta URL Ngrok ke ml ops:
dapatkan URL terbaru untuk pengujian.

### **Uji Aplikasi Mobile:**

1. Pastikan gambar dapat diunggah melalui UI aplikasi.
2. Tampilkan hasil prediksi diagnosa dan cuaca di aplikasi.
3. Simpan hasil ke database SQLite untuk digunakan kembali.

## Note : Pengujian lokal berjalan lancar dan Flask API sudah terdeploy di ibm cloud dan bersifat kredensial
