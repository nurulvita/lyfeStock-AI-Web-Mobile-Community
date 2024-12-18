@file:Suppress("UNUSED_EXPRESSION")

package com.example.applyfestox.screen.manajemen

//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
//import com.example.applyfestox.screen.manajemen.component.ButtonBar
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.applyfestox.R
import com.example.applyfestox.data.dummy.KandangDummyData
import com.example.applyfestox.uii.screen.manajemen.component.DonutChart
import com.example.applyfestox.uii.screen.manajemen.component.DonutChartData
import com.example.applyfestox.uii.screen.manajemen.component.DonutChartDataCollection
import com.example.applyfestox.uii.screen.manajemen.component.toMoneyFormat
import theme.MetallicYellow
import theme.PetroleumGray
import theme.PetroleumLightGray
import theme.RobingEggBlue
import theme.Sapphire
import theme.Yellow50
import theme.itemTextStyle
import theme.moneyAmountStyle

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



@OptIn(ExperimentalMaterial3Api::class)
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
    feedingSchedule: String,
    navController: NavHostController

) {
    val selectedTab = remember { mutableStateOf(0) }
    val tabs = listOf("Ringkasan", "Sapronak", "Pengeluaran")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Dashboard Kandang",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFFFF9800) // Orange color
                )
            )
        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
//                    navController.navigate("form_pakan_screen")
//                },
//                shape = CircleShape,
//                containerColor = Color(0xFFFFC107)
//            ) {
//                Icon(
//                    painter = painterResource(id = android.R.drawable.ic_input_add), // "+" icon
//                    contentDescription = "Tambah",
//                    tint = Color.White
//                )
//            }
//        },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Memastikan FAB tidak menutupi konten
                    .background(Yellow50)
            ) {
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
                        1 -> SapronakContent()
                        2 -> PengeluaranContent()
                    }
                }
            }
        }
    )
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
            .padding(vertical = 8.dp)
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


@Composable
fun SapronakContent() {
    val selectedTab = remember { mutableStateOf(0) }
    val tabs = listOf("DOC", "Pakan", "OVK")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFFFF9800), // Border orange
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                TabSection(selectedTab.value, tabs) { selectedTab.value = it }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Konten berdasarkan tab yang dipilih
            when (selectedTab.value) {
                0 -> DOCContent() // Tab DOC
                1 -> PakanContent() // Tab Pakan
                2 -> OVKContent() // Tab OVK
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
            .padding(bottom = 80.dp),
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Ikon ayam
                    Image(
                        painter = painterResource(id = R.drawable.pemberianpakan), // Ganti dengan resource gambar Anda
                        contentDescription = "Icon Ayam",
                        modifier = Modifier
                            .size(60.dp)
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


@Composable
fun PakanContent() {
    var showDialog by remember { mutableStateOf(false) }
    var selectedPakan by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var pricePerUnit by remember { mutableStateOf("") }
    val totalPrice = (quantity.toIntOrNull() ?: 0) * (pricePerUnit.toIntOrNull() ?: 0)
    val pakanOptions = listOf("BRO 1 PN", "MR 1 P", "STARTER 2 A", "FINISHER 3 B")

    val pakanList = remember { mutableStateListOf(
        PakanItem("BRO 1 PN", "15 November 2024", "20 sak", "Rp 20.000"),
        PakanItem("MR 1 P", "15 November 2024", "10 sak", "Rp 50.000")
    )}

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        pakanList.forEach { pakanItem ->
            CardItem(
                name = pakanItem.name,
                date = pakanItem.date,
                quantity = pakanItem.quantity,
                price = pakanItem.price
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Tombol Tambah di bawah layar
        FloatingActionButton(
            onClick = {
                showDialog = true
            },
            shape = CircleShape,
            containerColor = Color(0xFFFFC107),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_input_add),
                contentDescription = "Tambah",
                tint = Color.White
            )
        }
    }

    // Dialog Form
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Tambah Pakan Masuk") },
            text = {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Button for Pakan Selection
                    var expanded by remember { mutableStateOf(false) }

                    Box(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { expanded = !expanded },
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            Text(
                                text = if (selectedPakan.isEmpty()) "Pilih Jenis Pakan" else selectedPakan
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            pakanOptions.forEach { pakan ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedPakan = pakan
                                        expanded = false // Close dropdown after selection
                                    }
                                ) {
                                    Text(text = pakan)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { quantity = it },
                        label = { Text("Kuantitas (sak)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = pricePerUnit,
                        onValueChange = { pricePerUnit = it },
                        label = { Text("Harga Satuan (Rp)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Display Total Price
                    Text(
                        text = "Total Harga: Rp ${totalPrice}",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Add the new pakan item to the list
                        if (selectedPakan.isNotEmpty() && quantity.isNotEmpty() && pricePerUnit.isNotEmpty()) {
                            pakanList.add(
                                PakanItem(
                                    selectedPakan,
                                    "Tanggal Sekarang", // You can replace this with the actual date
                                    "$quantity sak",
                                    "Rp $pricePerUnit"
                                )
                            )
                        }
                        showDialog = false
                    }
                ) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Batal")
                }
            }
        )
    }
}


data class PakanItem(val name: String, val date: String, val quantity: String, val price: String)


@Composable
fun CardItem(
    name: String,
    date: String,
    quantity: String,
    price: String
) {
    Card(
        modifier = Modifier
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
                painter = painterResource(id = R.drawable.pakan), // Ganti dengan resource gambar Anda
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
    var showDialog by remember { mutableStateOf(false) }
    var selectedObat by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var pricePerUnit by remember { mutableStateOf("") }
    val totalPrice = (quantity.toIntOrNull() ?: 0) * (pricePerUnit.toIntOrNull() ?: 0)
    val obatOptions = listOf("Paragin", "Amoxicillin", "Vitamin C", "Ibuprofen")

    // State to store the list of obat items
    val obatList = remember { mutableStateListOf(
        OVKItem("Paragin", "15 November 2024", "5 sachet", "Rp 20.000"),
        OVKItem("Vitamin C", "15 November 2024", "3 bottle", "Rp 30.000")
    )}

    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        // Display each obat item from the list
        obatList.forEach { obatItem ->
            CardItemOVK(
                name = obatItem.name,
                date = obatItem.date,
                quantity = obatItem.quantity,
                price = obatItem.price
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Tombol Tambah di bawah layar
        FloatingActionButton(
            onClick = {
                showDialog = true
            },
            shape = CircleShape,
            containerColor = Color(0xFFFFC107),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_input_add),
                contentDescription = "Tambah",
                tint = Color.White
            )
        }
    }

    // Dialog Form
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Tambah Obat / Vitamin") },
            text = {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Button for Obat Selection
                    var expanded by remember { mutableStateOf(false) }

                    Box(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { expanded = !expanded },
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            Text(
                                text = if (selectedObat.isEmpty()) "Pilih Jenis Obat" else selectedObat
                            )
                        }

                        // Dropdown with options (after button is clicked)
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            obatOptions.forEach { obat ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedObat = obat
                                        expanded = false // Close dropdown after selection
                                    }
                                ) {
                                    Text(text = obat)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Input Fields for Quantity and Price Per Unit
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { quantity = it },
                        label = { Text("Kuantitas") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = pricePerUnit,
                        onValueChange = { pricePerUnit = it },
                        label = { Text("Harga Satuan (Rp)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Display Total Price
                    Text(
                        text = "Total Harga: Rp ${totalPrice}",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Add the new obat item to the list
                        if (selectedObat.isNotEmpty() && quantity.isNotEmpty() && pricePerUnit.isNotEmpty()) {
                            obatList.add(
                                OVKItem(
                                    selectedObat,
                                    "Tanggal Sekarang", // You can replace this with the actual date
                                    "$quantity x",
                                    "Rp $pricePerUnit"
                                )
                            )
                        }
                        showDialog = false
                    }
                ) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Batal")
                }
            }
        )
    }
}

// Data class to represent an OVK item
data class OVKItem(val name: String, val date: String, val quantity: String, val price: String)

@Composable
fun CardItemOVK(name: String, date: String, quantity: String, price: String) {
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
                        text = name,
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
                        text = "$quantity x Rp $price",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // Harga total
                Text(
                    text = price,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
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

//@Preview(showBackground = true)
//@Composable
//fun DashboardKandangScreenPreview() {
//    DashboardKandangScreen(
//        period = "Periode 1",
//        location = "Lokasi A",
//        duration = "30 hari",
//        chickenCount = "1000 ekor",
//        weight = "500 kg",
//        farmName = "Kandang Ayam Sejahtera",
//        onBackClick = {}, // Placeholder untuk aksi kembali
//        farmId = "12345",
//        feedingSchedule = "Pagi & Sore",
//        navController = navController
//    )
//}