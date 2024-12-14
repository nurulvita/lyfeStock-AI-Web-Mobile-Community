package com.example.applyfestox.screen.manajemen

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.applyfestox.ui.theme.Orange600
import com.example.applyfestox.ui.theme.Yellow50
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahPakanForm(navController: NavController) {
    val namaPakan = remember { mutableStateOf("") }
    val jenisPakan = remember { mutableStateOf("") }
    val kuantitasPakan = remember { mutableStateOf("") }
    val hargaSatuanPakan = remember { mutableStateOf("") }
    val tanggalMasukPakan = remember { mutableStateOf("") } // Field untuk tanggal

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Tambah Kandang",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        // Nama Pakan
        item {
            OutlinedTextField(
                value = namaPakan.value,
                onValueChange = { namaPakan.value = it },
                label = { Text("Nama Pakan") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Yellow50),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Orange600,
                    cursorColor = Orange600
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Jenis Pakan
        item {
            OutlinedTextField(
                value = jenisPakan.value,
                onValueChange = { jenisPakan.value = it },
                label = { Text("Jenis Pakan") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Yellow50),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Orange600,
                    cursorColor = Orange600
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Kuantitas
        item {
            OutlinedTextField(
                value = kuantitasPakan.value,
                onValueChange = { kuantitasPakan.value = it },
                label = { Text("Kuantitas") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Yellow50),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Orange600,
                    cursorColor = Orange600
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Harga Satuan
        item {
            OutlinedTextField(
                value = hargaSatuanPakan.value,
                onValueChange = { hargaSatuanPakan.value = it },
                label = { Text("Harga Satuan") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Yellow50),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Orange600,
                    cursorColor = Orange600
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Tanggal
        item {
            TanggalPakanMasuk(tanggalMasukPakan)

            Spacer(modifier = Modifier.height(30.dp))
        }

        // Tombol Simpan dan Batal
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        // Aksi untuk tombol Batal
                        // Misalnya, kembali ke layar sebelumnya atau mengosongkan field
                        navController.popBackStack() // Kembali ke layar sebelumnya
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp) // Spasi antara tombol
                ) {
                    Text(
                        text = "Batal",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                Button(
                    onClick = {
                        navController.navigate("manajemen_screen")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Orange600),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp) // Spasi antara tombol
                ) {
                    Text(
                        text = "Simpan",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TanggalPakanMasuk (birthDate: MutableState<String>) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    OutlinedTextField(
        value = birthDate.value,
        onValueChange = { },
        label = { Text("Tanggal") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = {
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        birthDate.value = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = Orange600
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Yellow50),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Orange600,
            cursorColor = Orange600
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TambahPakanFormPreview() {
    // Simulasi NavController kosong untuk preview
    val fakeNavController = rememberNavController()
    TambahPakanForm(navController = fakeNavController)
}