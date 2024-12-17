package com.example.applyfestox.uii.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applyfestox.data.network.ApiService
import com.example.applyfestox.data.network.RetrofitClient
import com.example.applyfestox.data.network.WeatherRequest
import com.example.applyfestox.data.network.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    // StateFlow untuk data cuaca
    private val _weatherData = MutableStateFlow<List<WeatherResponse>>(emptyList())
    val weatherData: StateFlow<List<WeatherResponse>> = _weatherData.asStateFlow()

    // StateFlow untuk kota yang dipilih
    private val _selectedCity = MutableStateFlow("Barelang")
    val selectedCity: StateFlow<String> = _selectedCity.asStateFlow()

    // Instance API Service
    private val apiService: ApiService = RetrofitClient.getRetrofitInstance().create(ApiService::class.java)

    /**
     * Mengambil data cuaca berdasarkan kota.
     */
    fun fetchWeatherByCities(cities: List<String>) {
        viewModelScope.launch {
            try {
                val request = WeatherRequest(cities)
                val response = apiService.getWeather(request)
                _weatherData.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                _weatherData.value = emptyList() // Handle error dengan data kosong
            }
        }
    }


    /**
     * Update kota yang dipilih dan ambil data cuaca.
     */
    fun updateSelectedCity(city: String) {
        _selectedCity.value = city
        fetchWeatherByCities(listOf(city))
    }
}
