package com.example.applyfestox.screen.manajemen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.applyfestox.data.sqlite.DatabaseHelper
import com.example.applyfestox.ui.theme.Orange600
import com.example.applyfestox.ui.theme.Yellow50
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahKandangScreen(navController: NavController) {
    val namaKandang = remember { mutableStateOf("") }
    val jenisKandang = remember { mutableStateOf("") }
    val jumlahPopulasiAyam = remember { mutableStateOf("") }
    val alamatKandang = remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Isi Data Kandang",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Isi formulir di bawah untuk mengisi data kandang",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Nama Kandang
        item {
            OutlinedTextField(
                value = namaKandang.value,
                onValueChange = { namaKandang.value = it },
                label = { Text("Nama Kandang") },
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

        // Jenis Kandang
//        item {
//            OutlinedTextField(
//                value = jenisKandang.value,
//                onValueChange = { jenisKandang.value = it },
//                label = { Text("Jenis Kandang") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Yellow50),
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Orange600,
//                    cursorColor = Orange600
//                )
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//        }

        // Jumlah Populasi Ayam
        item {
            OutlinedTextField(
                value = jumlahPopulasiAyam.value,
                onValueChange = { jumlahPopulasiAyam.value = it },
                label = { Text("Jumlah Populasi Ayam") },
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

        // Alamat Kandang
        item {
            OutlinedTextField(
                value = alamatKandang.value,
                onValueChange = { alamatKandang.value = it },
                label = { Text("Alamat Kandang") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Yellow50),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Orange600,
                    cursorColor = Orange600
                )
            )

            Spacer(modifier = Modifier.height(30.dp))
        }

        // Tombol Simpan
        item {
            Spacer(modifier = Modifier.fillMaxHeight(0.4f))

            Button(
                onClick = {
                    navController.navigate("manajemen_screen")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Orange600),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
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

@Preview(showBackground = true)
@Composable
fun TambahKandangScreenPreview() {
    // Simulasi NavController kosong untuk preview
    val fakeNavController = NavController(LocalContext.current)
    TambahKandangScreen(navController = fakeNavController)
}
