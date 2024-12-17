package com.example.applyfestox.uii.screen.beranda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.applyfestox.data.network.WeatherResponse
import com.example.applyfestox.uii.viewmodel.WeatherViewModel
import theme.Orange600
import theme.Yellow50
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun CuacaScreen(navController: NavHostController, viewModel: WeatherViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val weatherData by viewModel.weatherData.collectAsState()
    val selectedCity by viewModel.selectedCity.collectAsState()

    val cities = listOf("Barelang", "Batam Center", "Nongsa", "Tanjung Uncang", "Sekupang", "Jakarta", "Surabaya", "Bandung", "Yogyakarta", "Samarinda")
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(selectedCity) {
        viewModel.fetchWeatherByCities(listOf(selectedCity))
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow50)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Orange600),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.elevation(4.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Kembali", tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Kembali", color = Color.White)
            }

            Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                Button(
                    onClick = { expanded = true },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Orange600),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.elevation(4.dp)
                ) {
                    Icon(Icons.Default.LocationCity, contentDescription = "Pilih Kota", tint = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Pilih Kota", color = Color.White)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    cities.forEach { city ->
                        DropdownMenuItem(onClick = {
                            viewModel.updateSelectedCity(city)
                            expanded = false
                        }) {
                            Text(text = city)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Cuaca Hari Ini $selectedCity",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (weatherData.isNotEmpty()) {
            WeatherContent(weatherData)
        } else {
            Text(
                text = "Memuat cuaca...",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun WeatherContent(weatherData: List<WeatherResponse>) {
    if (weatherData.isNotEmpty()) {
        val currentWeather = weatherData.first()
        val currentTime = remember {
            SimpleDateFormat("EEEE, dd/MM/yyyy - HH:mm", Locale.getDefault()).format(Date())
        }
        val groupedWeather = weatherData.drop(1).groupBy { formatDay(it.timestamp) }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentTime,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = getWeatherIcon(currentWeather.description)),
                    contentDescription = currentWeather.description,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "${currentWeather.temperature}°C",
                        fontSize = 50.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = currentWeather.description,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Kelembapan: ${currentWeather.humidity}%",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Kecepatan Angin: ${currentWeather.windspeed} km/jam",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Ramalan Cuaca Harian",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(alignment = Alignment.Start)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                groupedWeather.forEach { (day, weatherList) ->
                    item {
                        Text(
                            text = day,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    items(weatherList) { weather ->
                        WeatherItem(
                            time = weather.timestamp,
                            temperature = "${weather.temperature}°C",
                            humidity = "${weather.humidity}%",
                            windspeed = "${weather.windspeed} km/jam",
                            description = weather.description
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherItem(time: String, temperature: String, humidity: String, windspeed: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = getWeatherIcon(description)),
                contentDescription = description,
                tint = Color.Unspecified,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = time,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Kelembapan: $humidity",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Kecepatan Angin: $windspeed",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            // Suhu
            Text(
                text = temperature,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}




@Suppress("DEPRECATION")
fun formatDay(timestamp: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = dateFormat.parse(timestamp) ?: return "Lainnya"

    val today = Calendar.getInstance()
    today.time = Date()

    val calendar = Calendar.getInstance()
    calendar.time = date

    return when {
        calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) -> "Hari ini"

        calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) + 1 -> "Besok"

        else -> SimpleDateFormat("EEEE, dd/MM/yyyy", Locale.getDefault()).format(date) // Format tanggal lainnya
    }
}

