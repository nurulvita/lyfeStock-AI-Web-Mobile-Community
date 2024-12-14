package com.example.applyfestox.screen.manajemen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.applyfestox.R

@Composable
fun JadwalPakanScreen(navController: NavController) {
    // State untuk mengatur visibilitas kotak putih
    var showAddScheduleBox by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Jadwal Pakan",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Start)
                .padding(top = 70.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(id = R.drawable.pakan),
            contentDescription = "Chicken Feed Schedule",
            modifier = Modifier.size(400.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { showAddScheduleBox = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7D00)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(text = "+  Jadwalkan Pemberian Pakan", color = Color.White, fontSize = 18.sp)
        }

        // Kondisional untuk menampilkan kotak putih
        if (showAddScheduleBox) {
            AddScheduleBox(
                onClose = { showAddScheduleBox = false },
                onSave = { /* Tambahkan logika untuk menyimpan jadwal */ }
            )
        }
    }
}

@Composable
fun AddScheduleBox(onClose: () -> Unit, onSave: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Tambah Jadwal Pakan",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            CustomTimeInput(label = "Jadwal Pakan Pagi")
            Spacer(modifier = Modifier.height(8.dp))
            CustomTimeInput(label = "Jadwal Pakan Sore")
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onSave,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Simpan")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onClose,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Batalkan")
                }
            }
        }
    }
}

fun CustomTimeInput(label: String) {


}

@Preview(showBackground = true)
@Composable
fun JadwalPakanScreenPreview() {
    JadwalPakanScreen(navController = rememberNavController()) // Menggunakan dummy NavController untuk Preview
}
