
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applyfestox.data.network.RetrofitInstancecc
import com.example.applyfestox.data.network.WeatherApiService
import com.example.applyfestox.data.network.WeatherResponsecc
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    // Flow for weather data
    private val _weatherData = MutableStateFlow<List<WeatherResponsecc>>(emptyList())
    val weatherData: StateFlow<List<WeatherResponsecc>> = _weatherData.asStateFlow()


    // Flow for selected city
    private val _selectedCity = MutableStateFlow("Barelang")
    val selectedCity: StateFlow<String> = _selectedCity.asStateFlow()

    // Retrofit API instance
    private val apiService: WeatherApiService = RetrofitInstancecc.api

    // API Key for OpenWeatherMap
    private val apiKey = "9d71d654483af8ce489125e2cf818286"

    /**
     * Fetch weather data for a list of cities.
     */
    fun fetchWeatherByCities(cities: List<String>) {
        viewModelScope.launch {
            try {
                val weatherResponses = cities.mapNotNull { city ->
                    apiService.getCurrentWeatherByCity(city, apiKey)
                }
                _weatherData.value = weatherResponses
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchWeather(cities: List<String>) {
        viewModelScope.launch {
            try {
                val weatherResponses = cities.mapNotNull { city ->
                    apiService.getCurrentWeatherByCity(city, apiKey)
                }
                _weatherData.value = weatherResponses
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Fetch weather data using latitude and longitude.
     */
    fun fetchWeatherByCoordinates(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val weatherResponse = apiService.getCurrentWeather(lat, lon, apiKey)
                _weatherData.value = listOf(weatherResponse)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Update selected city and fetch its weather data.
     */
    fun updateSelectedCity(city: String) {
        _selectedCity.value = city
        fetchWeatherByCities(listOf(city))
    }



}
