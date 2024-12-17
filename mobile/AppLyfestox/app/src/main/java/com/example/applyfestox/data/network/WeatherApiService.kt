package com.example.applyfestox.data.network

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class WeatherResponsecc(
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    @SerializedName("name") val city: String,
    @SerializedName("dt") val timestamp: Long
)

data class Weather(
    val description: String
)

data class Main(
    val temp: Double,
    val humidity: Int,
)

data class Wind(
    val speed: Double
)


interface WeatherApiService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponsecc

    @GET("weather")
    suspend fun getCurrentWeatherByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponsecc
}


object RetrofitInstancecc {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    val api: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }
}

fun translateWeatherDescription(description: String): String {
    return when (description) {
        "clear sky" -> "Langit cerah"
        "few clouds" -> "Sedikit berawan"
        "scattered clouds" -> "Awan tersebar"
        "broken clouds" -> "Awan pecah/mendung"
        "overcast clouds" -> "Mendung tebal"
        "shower rain" -> "Hujan ringan"
        "rain" -> "Hujan"
        "thunderstorm" -> "Badai petir"
        "snow" -> "Salju"
        "mist" -> "Kabut"
        "haze" -> "Kabut asap"
        "smoke" -> "Asap"
        "dust" -> "Debu"
        "fog" -> "Kabut tebal"
        "sand" -> "Pasir"
        "ash" -> "Abu"
        "squall" -> "Angin kencang"
        "tornado" -> "Tornado"
        else -> "Deskripsi tidak diketahui"
    }
}
