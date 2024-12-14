package com.example.applyfestox.screen.beranda

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.example.applyfestox.data.sharedpreferences.LoginSessionHelper
import com.example.applyfestox.data.sqlite.User
import com.example.applyfestox.model.ArticleData
import com.example.applyfestox.ui.theme.Orange500
import com.example.applyfestox.ui.theme.Orange600
import com.example.applyfestox.ui.theme.Yellow50
import com.example.applyfestox.viewmodel.WeatherViewModel


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
        item { ModernWeatherSection() }
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
//                painter = rememberImagePainter(user.photo)
                painter = painterResource(id = R.drawable.avatars),
                contentDescription = "Profile Image",
                modifier = Modifier.size(48.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.avatars),
                contentDescription = "Default Profile Image",
                modifier = Modifier.size(48.dp)
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


@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun ModernWeatherSection() {
    val viewModel:WeatherViewModel = viewModel()
    val weatherData by viewModel.weatherData.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchWeather("Samarinda")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            // Gambar sebagai background
            Image(
                painter = painterResource(id = R.drawable.ic_bkcuaca2),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            weatherData?.let { data ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Header
                    Column {
                        Text(
                            text = "Cuaca Hari Ini",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = data.name,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Main Weather Info
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = "${data.main.temp}°C",
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = data.weather.firstOrNull()?.description ?: "Tidak tersedia",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.DarkGray
                            )
                        }

                        val temperature = data.main.temp as? Double ?: 0.0

                        val weatherIcon = when {
                            temperature >= 30 -> R.drawable.cerah
                            temperature in 20.0..29.9 -> R.drawable.berawan
                            temperature in 10.0..19.9 -> R.drawable.ic_awan
                            else -> R.drawable.ic_hujan
                        }

                        Image(
                            painter = painterResource(id = weatherIcon),
                            contentDescription = "Weather Icon",
                            modifier = Modifier.size(80.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Additional Info
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        InfoItem(icon = R.drawable.ic_drops, label = "Kelembapan", value = "${data.main.humidity}%")
                        InfoItem(icon = R.drawable.ic_wind, label = "Kecepatan Angin", value = "${data.wind.speed} km/h")
                    }
                }
            } ?: Text(
                text = "Memuat cuaca...",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun InfoItem(icon: Int, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier.size(24.dp)
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


@Composable
fun PerformaSection() {
    Column() {
        Text("Performa", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            item {
                PerformanceCard(
                    title = "Jumlah Ayam",
                    iconId = R.drawable.ayam,
                    count = "0"
                )
            }
            item {
                PerformanceCard(
                    title = "Jumlah Telur",
                    iconId = R.drawable.telur,
                    count = "0"
                )
            }
            item {
                PerformanceCard(
                    title = "Jumlah Pakan",
                    iconId = R.drawable.pakan,
                    count = "0"
                )
            }
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            item {
                StatusCard(
                    title = "Kandang Aktif",
                    count = "0",
                    backgroundColor = Color(0xFFA3D89E)
                )
            }
            item {
                StatusCard(
                    title = "Kandang Rehat",
                    count = "0",
                    backgroundColor = Color(0xFFFFD27F)
                )
            }
        }
    }
}

@Composable
fun PerformanceCard(title: String, iconId: Int, count: String) {
    Card(
        modifier = Modifier.size(150.dp, 100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = title,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(title, fontSize = 14.sp, color = Color(0xFFDA6F0A))
                Spacer(modifier = Modifier.height(4.dp))
                Text(count, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }
    }
}

@Composable
fun StatusCard(title: String, count: String, backgroundColor: Color) {
    Card(
        modifier = Modifier.height(60.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.kandang),
                contentDescription = title,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(count, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(title, fontSize = 14.sp, color = Color.White)
        }
    }
}

@Composable
fun FeedSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Orange500)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Pemberian Pakan Hari ini", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text("Kamis, 24 Oktober 2024", fontSize = 12.sp)
            Text("Jam 07:30 WITA di Kandang Steven", fontSize = 12.sp)
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
            .width(230.dp),
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