package com.example.applyfestox.uii.screen.beranda

import WeatherViewModel
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.applyfestox.R
import com.example.applyfestox.data.model.ArticleData
import com.example.applyfestox.data.repository.getUserLocation
import com.example.applyfestox.data.sharedpreferences.LoginSessionHelper
import com.example.applyfestox.data.sqlite.User
import theme.Orange500
import theme.Orange600
import theme.Yellow50
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun BerandaScreen(navController: NavHostController, context: Context) {
    val sessionHelper = LoginSessionHelper(context)
    val currentUser = sessionHelper.getLoggedInUser()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { TopBar()}
        item {
            WelcomeSection(navController = navController, user = currentUser)
        }
        item { ModernWeatherSection(navController = navController, context = context)}
        item { PerformaSection() }
        item { FeedSection() }
        item { ArticleSection(navController) }
    }
}


@Composable
fun WelcomeSection(navController: NavHostController, user: User?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Orange600, RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (user != null && !user.photo.isNullOrEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(model = user.photo),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.avatars),
                contentScale = ContentScale.Crop,
                contentDescription = "Default Profile Image",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text("Selamat Datang,", fontSize = 14.sp)
            Text(user?.name ?: "Pengguna", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { navController.navigate("pemberitahuan") }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_notifications),
                contentDescription = "Notifications",
                tint = Orange600
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.logo22),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(130.dp)
                    .background(Yellow50)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Yellow50)
    )

}

@Composable
fun ModernWeatherSection(context: Context, navController: NavHostController) {
    val viewModel: WeatherViewModel = viewModel()
    val weatherData by viewModel.weatherData.collectAsState()
    val location = getUserLocation(context).value
    val currentTime = remember {
        SimpleDateFormat("EEEE, dd/MM/yyyy : HH:mm", Locale.getDefault()).format(Date())
    }

    LaunchedEffect(location) {
        location?.let {
            viewModel.fetchWeatherByCoordinates(it.first, it.second)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        weatherData.firstOrNull()?.let { data ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(Orange500)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Cuaca di ${data.city}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = currentTime,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Image(
                                painter = painterResource(
                                    id = getWeatherIcon(data.weather[0].description)
                                ),
                                contentDescription = "Weather Icon",
                                modifier = Modifier.size(72.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${data.main.temp}°C",
                                fontSize = 42.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = data.weather[0].description.capitalize(Locale.ROOT),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        InfoItem(icon = R.drawable.ic_drops, label = "Kelembapan", value = "${data.main.humidity}%")
                        InfoItem(icon = R.drawable.ic_wind, label = "Angin", value = "${data.wind.speed} km/h")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { navController.navigate("cuaca_screen") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Info Selengkapnya",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Orange600
                        )
                    }
                }
            }
        } ?: Text(
            text = "Memuat data cuaca...",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun InfoItem(icon: Int, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.White,
                fontWeight = FontWeight.Light
            )
            Text(
                text = value,
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

//fun getWeatherIcon(description: String): Int {
//    // Pastikan semua string dibandingkan dalam format lowercase
//    val lowerDescription = description.lowercase(Locale.getDefault())
//    return when (lowerDescription) {
//        "rain", "shower rain", "thunderstorm", "light rain","moderate rain" -> R.drawable.petir
//
//        "clear sky", "sun", "few clouds" -> R.drawable.cerah
//
//        "scattered clouds", "broken clouds", "overcast clouds",
//        "mist", "haze", "fog", "smoke", "dust", "sand", "ash", "squall", "tornado" -> R.drawable.berawan
//
//        else -> R.drawable.berawan
//    }
//}
fun getWeatherIcon(description: String): Int {
    // Normalisasi ke huruf kecil untuk menghindari masalah dengan case-sensitive
    val lowerDescription = description.lowercase(Locale.getDefault())
    return when (lowerDescription) {
        // Kategori Hujan
        "rain", "light rain", "moderate rain", "heavy rain", "shower rain",
        "drizzle", "thunderstorm", "thunderstorm with rain", "sleet" -> R.drawable.hujan

        // Kategori Cerah
        "clear sky", "sun", "sunny", "few clouds" -> R.drawable.cerah

        // Kategori Berawan
        "scattered clouds", "broken clouds", "overcast clouds",
        "partly cloudy", "mostly cloudy", "cloudy" -> R.drawable.berawan

        // Kategori Angin Kencang atau Cuaca Ekstrem
        "squall", "tornado", "gale", "storm", "hurricane" -> R.drawable.ekstrem

        else -> R.drawable.ic_location
    }
}


@Composable
fun PerformaSection() {
    Column() {
        Text("Performa", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PerformanceCard(
                title = "Jumlah",
                iconId = R.drawable.ayam,
                count = "0",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            PerformanceCard(
                title = "Jumlah",
                iconId = R.drawable.telur,
                count = "0",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            PerformanceCard(
                title = "Jumlah",
                iconId = R.drawable.pakan,
                count = "0",
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatusCard(
                title = "Aktif",
                count = "0",
                backgroundColor = Color(0xFFA3D89E),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatusCard(
                title = "Rehat",
                count = "0",
                backgroundColor = Color(0xFFFFD27F),
                modifier = Modifier
            )
        }
    }
}

@Composable
fun PerformanceCard(title: String, iconId: Int, count: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(120.dp)
            .shadow(4.dp, shape = RoundedCornerShape(16.dp))
            .background(
                color = Color(0xFFF9F9F9),
                shape = RoundedCornerShape(16.dp),
            )
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = title,
                modifier = Modifier
                    .size(48.dp)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = count,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally) // Centers the text horizontally
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color(0xFFDA6F0A)
            )
        }
    }
}

@Composable
fun StatusCard(title: String, count: String, backgroundColor: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(160.dp)
            .height(80.dp)
            .shadow(8.dp, shape = RoundedCornerShape(16.dp))
            .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.kandang),
                contentDescription = title,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = count,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Text(
                    text = title,
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun FeedSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, shape = RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Orange500),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.pakan),
                contentDescription = "Feed Icon",
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = CircleShape
                    )
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            // Konten teks
            Column {
                Text(
                    "Pemberian Pakan Hari ini",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    "Kamis, 24 Oktober 2024",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Text(
                    "Jam 07:30 WITA",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PemberitahuanScreen(navController: NavHostController) {
    val backgroundColor = Color(0xFFFFFBEE)
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = "Pemberitahuan",
                        color = Color(0xFFFF8C00),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFFFF8C00)
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = backgroundColor
                )
            )
        },
        containerColor = backgroundColor
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Tidak ada pemberitahuan",
                color = Color(0xFFFF8C00),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ArticleSection(navController: NavHostController) {
    Text(
        text = "Artikel Terkait",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    val articles = listOf(
        ArticleData(
            title = "Waspadai Penyakit Pada Ayam Broiler",
            imageUrl = "https://www.deheus.id/siteassets/news/foto-utama.jpg?mode=crop&width=2552&height=1367",
            url = "https://www.deheus.id/cari/berita-dan-artikel/waspadai-penyakit-pada-ayam-broiler-yang-dapat-merugikan-usaha-anda"
        ),
        ArticleData(
            title = "Cara Menentukan FCR untuk Ayam Broiler dan Layer: Panduan Lengkap",
            imageUrl = "https://www.deheus.id/siteassets/news/article/cara-menentukan-fcr-untuk-ayam-broiler-dan-layer-panduan-lengkap/large-poultry_chickens_white_layer_chickens.jpg?mode=crop&width=2552&height=1367",
            url = "https://www.deheus.id/cari/berita-dan-artikel/cara-menentukan-fcr-untuk-ayam-broiler-dan-layer-panduan-lengkap"
        ),
        ArticleData(
            title = "Pemberian Pakan Optimal Terhadap Induk Broiler untuk Hasilkan DOC yang Sehat",
            imageUrl = "https://www.deheus.id/siteassets/news/article/poultry_feeding_chickens.jpg?mode=crop&width=2552&height=1367",
            url = "https://www.deheus.id/cari/berita-dan-artikel/pemberian-pakan-optimal"
        )
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(articles) { article ->
            ArticleCard(article = article, navController = navController)
        }
    }
}

@Composable
fun ArticleCard(article: ArticleData, navController: NavHostController) {
    Card(
        modifier = Modifier
            .clickable {
                val encodedUrl = Uri.encode(article.url)
                navController.navigate("article_detail_screen/$encodedUrl")
            }
            .width(230.dp)
            .shadow(4.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(article.imageUrl),
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = article.title,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val encodedUrl = Uri.encode(article.url)
                    navController.navigate("article_detail_screen/$encodedUrl")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Orange600),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Lihat Detail",
                    color = Color.White,
                    fontSize = 14.sp
                )

            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}