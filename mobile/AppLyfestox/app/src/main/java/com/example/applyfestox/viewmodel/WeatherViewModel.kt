package com.example.applyfestox.viewmodel

import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.applyfestox.data.remote.RetrofitClient
import com.example.applyfestox.data.remote.WeatherApi
import com.example.applyfestox.model.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@UnstableApi
class WeatherViewModel : ViewModel() {

    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData
        .also { Log.d("WeatherViewModel", "Weather data updated: $it") }


    private val weatherApi = RetrofitClient.retrofit.create(WeatherApi::class.java)

    // Fungsi untuk mengambil data cuaca
    @OptIn(UnstableApi::class)
    fun fetchWeather(city: String) {
        viewModelScope.launch {
            try {
                val apiKey = "7c0d52384e3419e8b1a68ae399455d2e"
                Log.d("WeatherApiRequest", "Fetching weather for $city") // Debugging log

                val response = weatherApi.getWeather(city, "metric", apiKey)
                Log.d("WeatherApiResponse", "Response: $response") // Debug response
                _weatherData.value = response
            } catch (e: Exception) {
                Log.e("WeatherError", "Error fetching weather data: ${e.message}")
            }
        }
    }

}
