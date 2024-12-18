package com.example.applyfestox.uii.screen.manajemen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.applyfestox.data.sqlite.KandangSessionHelper
import theme.Orange600

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahKandangScreen(navController: NavController) {
    val namaKandang = remember { mutableStateOf("") }
    val jumlahPopulasiAyam = remember { mutableStateOf("") }
    val alamatKandang = remember { mutableStateOf("") }
    val jenisKandangOptions = listOf("Tertutup", "Terbuka", "Semi-Tertutup")
    var selectedJenisKandang by remember { mutableStateOf(jenisKandangOptions[0]) }

    // Error handling states
    var namaKandangError by remember { mutableStateOf("") }
    var jumlahPopulasiAyamError by remember { mutableStateOf("") }
    var alamatKandangError by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    // Initialize the KandangSessionHelper
    val kandangSessionHelper = KandangSessionHelper(context = LocalContext.current)

    Column(modifier = Modifier.fillMaxSize()) {
        // TopAppBar
        TopAppBar(
            title = { Text("Tambah Kandang Baru") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Orange600,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )

        // Form Input
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Input Nama Kandang
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Nama Kandang",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    TextField(
                        value = namaKandang.value,
                        onValueChange = {
                            namaKandang.value = it
                            namaKandangError = "" // Clear error when typing
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White),
                        placeholder = { Text(text = "Masukkan nama kandang") },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                        singleLine = true,
                        isError = namaKandangError.isNotEmpty(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            focusedIndicatorColor = Orange600,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Red
                        )
                    )
                    if (namaKandangError.isNotEmpty()) {
                        Text(
                            text = namaKandangError,
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Input Jumlah Populasi Ayam
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Jumlah Populasi Ayam",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    TextField(
                        value = jumlahPopulasiAyam.value,
                        onValueChange = {
                            // Hanya izinkan angka
                            if (it.all { char -> char.isDigit() }) jumlahPopulasiAyam.value = it
                            jumlahPopulasiAyamError = "" // Clear error when typing
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White),
                        placeholder = { Text(text = "Masukkan jumlah populasi ayam") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                // Dismissing the keyboard when the user presses "Done"
                                keyboardController?.hide()
                            }
                        ),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                        singleLine = true,
                        isError = jumlahPopulasiAyamError.isNotEmpty(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            focusedIndicatorColor = Orange600,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Red
                        )
                    )
                    if (jumlahPopulasiAyamError.isNotEmpty()) {
                        Text(
                            text = jumlahPopulasiAyamError,
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Input Jenis Kandang - Popup Dialog
            item {
                JenisKandangDropdown(
                    jenisKandangOptions = jenisKandangOptions,
                    selectedJenisKandang = selectedJenisKandang,
                    onJenisKandangSelected = { selectedJenisKandang = it }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Input Alamat Kandang
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Alamat Kandang",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    TextField(
                        value = alamatKandang.value,
                        onValueChange = {
                            alamatKandang.value = it
                            alamatKandangError = "" // Clear error when typing
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White),
                        placeholder = { Text(text = "Masukkan alamat kandang") },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                        singleLine = true,
                        isError = alamatKandangError.isNotEmpty(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            focusedIndicatorColor = Orange600,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Red
                        )
                    )
                    if (alamatKandangError.isNotEmpty()) {
                        Text(
                            text = alamatKandangError,
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Tombol Simpan
            item {
                Button(
                    onClick = {
                        // Validation
                        if (namaKandang.value.isEmpty()) {
                            namaKandangError = "Nama kandang tidak boleh kosong"
                        }
                        if (jumlahPopulasiAyam.value.isEmpty()) {
                            jumlahPopulasiAyamError = "Jumlah populasi ayam tidak boleh kosong"
                        }
                        if (alamatKandang.value.isEmpty()) {
                            alamatKandangError = "Alamat kandang tidak boleh kosong"
                        }

                        // Proceed if all fields are valid
                        if (namaKandang.value.isNotEmpty() && jumlahPopulasiAyam.value.isNotEmpty() && alamatKandang.value.isNotEmpty()) {
                            try {
                                // Simpan data kandang
                                kandangSessionHelper.saveKandangData(
                                    namaKandang.value,
                                    jumlahPopulasiAyam.value,
                                    alamatKandang.value,
                                    selectedJenisKandang
                                )
                                // Menambahkan log setelah penyimpanan berhasil
                                Log.d("TambahKandang", "Data kandang berhasil disimpan: Nama: ${namaKandang.value}, Lokasi: ${alamatKandang.value}, Kapasitas: ${jumlahPopulasiAyam.value}")

                                // Navigasi ke halaman manajemen setelah berhasil menyimpan data
                                navController.navigate("manajemen") {
                                    // Menambahkan flag untuk membersihkan back stack jika diperlukan
                                    popUpTo("tambah_kandang_screen") { inclusive = true }
                                }
                            } catch (e: Exception) {
                                // Log error jika terjadi kesalahan saat menyimpan
                                Log.e("TambahKandang", "Error saat menyimpan data kandang: ${e.message}")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Orange600)
                ) {
                    Text(
                        text = "Simpan",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JenisKandangDropdown(
    jenisKandangOptions: List<String>,
    selectedJenisKandang: String,
    onJenisKandangSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) } // Kontrol tampilan dropdown

    // Dropdown field
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedJenisKandang,
            onValueChange = {},
            label = { Text("Jenis Kandang") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Orange600,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        // Dropdown menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            jenisKandangOptions.forEach { option ->
                androidx.compose.material3.DropdownMenuItem(
                    onClick = {
                        onJenisKandangSelected(option) // Set nilai terpilih
                        expanded = false // Tutup dropdown
                    },
                    text = { Text(option) }
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun TambahKandangScreenPreview() {
    // Create a mock NavController for preview
    val navController = rememberNavController() // This will create a NavController for preview
    TambahKandangScreen(navController = navController)
}