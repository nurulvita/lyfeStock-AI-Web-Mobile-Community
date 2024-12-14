package com.example.applyfestox.screen.manajemen

//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.applyfestox.R
import com.example.applyfestox.data.dummy.KandangDummyData
import com.example.applyfestox.screen.manajemen.Sapronak.SapronakNavigationGraph
import com.example.applyfestox.screen.manajemen.Sapronak.SapronakScreen
//import com.example.applyfestox.screen.manajemen.component.ButtonBar
import com.example.applyfestox.screen.manajemen.component.DonutChart
import com.example.applyfestox.screen.manajemen.component.DonutChartData
import com.example.applyfestox.screen.manajemen.component.DonutChartDataCollection
import com.example.applyfestox.screen.manajemen.component.toMoneyFormat
import com.example.applyfestox.ui.theme.MetallicYellow
import com.example.applyfestox.ui.theme.PetroleumGray
import com.example.applyfestox.ui.theme.PetroleumLightGray
import com.example.applyfestox.ui.theme.RobingEggBlue
import com.example.applyfestox.ui.theme.Sapphire
import com.example.applyfestox.ui.theme.Yellow50
import com.example.applyfestox.ui.theme.itemTextStyle
import com.example.applyfestox.ui.theme.moneyAmountStyle

val Poppins = FontFamily(
    Font(R.font.poppins_regular), // Make sure you use the correct font file name
    Font(R.font.poppins_bold, FontWeight.Bold)
)
val viewData = DonutChartDataCollection(
    listOf(
        DonutChartData(1200000.0f, Sapphire, title = "Obat", formattedValue = 1200000.0f.toMoneyFormat()),
        DonutChartData(1500000.0f, RobingEggBlue, title = "Vitamin", formattedValue = 1500000.0f.toMoneyFormat()),
        DonutChartData(300000.0f, MetallicYellow, title = "Pakan", formattedValue = 300000.0f.toMoneyFormat())

    )
)

@Composable
fun DashboardKandangScreen(
    period: String,
    location: String,
    duration: String,
    chickenCount: String,
    weight: String,
    farmName: String, // Farm name parameter
    onBackClick: () -> Unit, // Back button click handler
    farmId: String,
    feedingSchedule: String
) {
    val navController = rememberNavController()
    val selectedTab = remember { mutableStateOf(0) }
    val tabs = listOf("Ringkasan", "Sapronak", "Pengeluaran")


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow50) // Yellow background color
    ) {
        // TopBar with back button
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFF9800)) // Orange background for the top bar
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Dashboard Kandang",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        // Header with farm name
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFFFF9800)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.bgform),
                    contentDescription = "Farm Banner",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = farmName,
                    style = MaterialTheme.typography.headlineMedium.copy(color = Color.White),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Tab Navigation
        item {
            TabSection(selectedTab.value, tabs) { selectedTab.value = it }
        }


        // Content based on selected tab
        item {
            when (selectedTab.value) {
                0 -> RingkasanContent()
                1 -> SapronakContent(navController)
                2 -> PengeluaranContent()
            }
        }
    }
}



//SCREEN RINGKASAN
@Composable
fun RingkasanContent() {
    val kandang = KandangDummyData.aktifKandangList.firstOrNull()

    if (kandang != null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoBox(title = "Jumlah Ayam", value = kandang.chickenCount, backgroundColor = Color(0xFFE3F2FD))
                InfoBox(title = "Umur Ayam", value = kandang.duration, backgroundColor = Color(0xFF81C784))
                InfoBox(title = "Pakan Tersedia", value = kandang.weight, backgroundColor = Color(0xFFFFF59D))
            }

            Text(
                text = "Ringkasan Performa",
                style = MaterialTheme.typography.titleLarge.copy(color = Color.Black),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    PerformanceTable()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Tabel Informasi Pakan",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.Black)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FeedTable()
                }
            }
        }
    } else {
        Text(
            text = "Tidak ada data kandang",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
        )
    }
}

@Composable
fun InfoBox(title: String, value: String, backgroundColor: Color) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .background(backgroundColor)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(color = Color.Black)
            )
        }
    }
}

@Composable
fun TabSection(selectedTabIndex: Int, tabs: List<String>, onTabSelected: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // Mengurangi padding vertikal
            .background(Color.White, shape = RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            tabs.forEachIndexed { index, tab ->
                TabItem(
                    label = tab,
                    isSelected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) }
                )
            }
        }
    }
}

@Composable
fun TabItem(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(4.dp) // Mengurangi padding
            .background(
                if (isSelected) Color(0xFFFF9800) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp) // Mengurangi padding
            .clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp, // Mengurangi ukuran font
                color = if (isSelected) Color.White else Color.Black
            )
        )
    }
}


// SCREEN SAPRONAK
@Composable
fun SapronakContent(navController: NavController) {
    val selectedTab = remember { mutableStateOf(0) }
    val tabs = listOf("DOC", "Pakan", "OVK")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp) // Padding horizontal
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp) // Memberikan ruang untuk tombol di bawah
        ) {
            TabSection(selectedTab.value, tabs) { selectedTab.value = it }

            // Konten berdasarkan tab yang dipilih
            when (selectedTab.value) {
                0 -> DOCContent()
                1 -> PakanContent(navController) // Pass navController here
                2 -> OVKContent()
            }
        }
    }
}




//KONTEN DOC
@Composable
fun DOCContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9C4))
            .padding(start = 16.dp, end = 16.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // Text Judul (Tanggal)
        Text(
            text = "21 November 2024",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Card untuk detail DOC
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Baris atas: Ikon + Judul
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Ikon ayam
                    Image(
                        painter = painterResource(id = android.R.drawable.ic_menu_gallery), // Ganti dengan resource gambar Anda
                        contentDescription = "Icon Ayam",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 8.dp),
                        contentScale = ContentScale.Fit
                    )

                    // Kolom untuk informasi DOC
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "DOC AYAM UNGGUL",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Cobb (AS HATCHED)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFFFC107), // Warna kuning
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Baris bawah: Populasi dan Harga
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Informasi populasi
                    Column {
                        Text(
                            text = "Populasi DOC masuk",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray
                        )
                        Text(
                            text = "50 x Rp 20.000",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    // Harga total
                    Text(
                        text = "Rp 1.000.000",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFC107),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}


//KONTEN PAKAN
@Composable
fun PakanContent(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9C4))
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        CardItem(
            name = "BRO 1 PN",
            date = "15 November 2024",
            quantity = "20 sak",
            price = "Rp 20.000"
        )
        Spacer(modifier = Modifier.height(8.dp))
        CardItem(
            name = "MR 1 P",
            date = "15 November 2024",
            quantity = "10 sak",
            price = "Rp 50.000"
        )

        // Tombol Tambah di bawah layar
        FloatingActionButton(
            onClick = {
                navController.navigate("form_pakan_masuk_screen")
                      },
            shape = CircleShape,
            containerColor = Color(0xFFFFC107),
            modifier = Modifier
                .align(Alignment.CenterHorizontally) // Memposisikan tombol di tengah bawah
                .padding(bottom = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_input_add), // Ikon "+"
                contentDescription = "Tambah",
                tint = Color.White
            )
        }
    }
}

@Composable
fun CardItem(
    name: String,
    date: String,
    quantity: String,
    price: String
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder for image
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery), // Ganti dengan resource gambar Anda
                contentDescription = "Image",
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFFFF176), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tanggal: $date",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Kuantitas: $quantity",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = "Harga per kilo: $price",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}

//KONTEN OVK
@Composable
fun OVKContent(onAddClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9C4))
            .padding(16.dp)
    ) {
        // Text Judul (Tanggal)
        Text(
            text = "22 November 2024",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Card untuk detail barang
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Baris atas: Ikon + Judul
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Ikon obat
                    Image(
                        painter = painterResource(id = android.R.drawable.ic_menu_gallery), // Ganti dengan resource gambar Anda
                        contentDescription = "Icon Obat",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 8.dp),
                        contentScale = ContentScale.Fit
                    )

                    // Kolom untuk informasi barang
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Paragin",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Sachet",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFFFC107), // Warna kuning
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Baris bawah: Populasi dan Harga
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Informasi barang
                    Column {
                        Text(
                            text = "Barang Masuk",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray
                        )
                        Text(
                            text = "1 x Rp 20.000",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    // Harga total
                    Text(
                        text = "Rp 20.000",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }

        // Spacer untuk mengisi ruang yang tersisa
        Spacer(modifier = Modifier.weight(1f))

        // Tombol Tambah di bawah layar
        FloatingActionButton(
            onClick = onAddClick,
            shape = CircleShape,
            containerColor = Color(0xFFFFC107),
            modifier = Modifier
                .align(Alignment.CenterHorizontally) // Memposisikan tombol di tengah
                .padding(bottom = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_input_add), // Ikon "+"
                contentDescription = "Tambah",
                tint = Color.White
            )
        }
    }
}




//SCREEN PENGELUARAN
@Composable
fun PengeluaranContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ContentChart()


        Text(
            text = "Rekapitulasi Pengeluaran",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                PerformanceItem("Pakan", "Rp 10.000.000")
                PerformanceItem("Obat", "Rp 5.000.000")
                PerformanceItem("Vitamin", "Rp 2.000.000")
                PerformanceItem("Total", "Rp 17.000.000")
            }
        }

    }
}

@Composable
fun PerformanceItem(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
        )
    }
}

@Composable
fun SummaryItem(title: String, value: String, color: Color) {
    Card(
        modifier = Modifier
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$title: $value",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ContentChart() {
    DonutChart( data = viewData) { selected ->
        AnimatedContent(targetState = selected) {
            val amount = it?.amount ?: viewData.totalAmount
            val text = it?.title ?: "Total"

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("$${amount.toMoneyFormat(true)}",
                    style = moneyAmountStyle, color = PetroleumGray)
                Text(text, style = itemTextStyle, color = PetroleumLightGray)
            }
        }
    }
}

@Composable
fun PerformanceTable() {
    Column {
        PerformanceItem("IP", "40")
        PerformanceItem("FCR Actual", "50")
        PerformanceItem("FCR Standard", "0.25")
        PerformanceItem("Diff FCR", "49.75")
        PerformanceItem("ADG", "0 g/hari")
        PerformanceItem("Deplesi", "0%")
        PerformanceItem("Bobot Total", "2 kg")
        PerformanceItem("Bobot Rata-rata", "200 g")
    }
}

@Composable
fun FeedTable() {
    Column {
        PerformanceItem("Pakan Hari Ini", "10 kg")
        PerformanceItem("Total Pakan", "100 kg")
        PerformanceItem("Sisa Pakan", "90 kg")
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardKandangScreenPreview() {
    DashboardKandangScreen(
        period = "Periode 1",
        location = "Lokasi A",
        duration = "30 hari",
        chickenCount = "1000 ekor",
        weight = "500 kg",
        farmName = "Kandang Ayam Sejahtera",
        onBackClick = {}, // Placeholder untuk aksi kembali
        farmId = "12345",
        feedingSchedule = "Pagi & Sore"
    )
}

