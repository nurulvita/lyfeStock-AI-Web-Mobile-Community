package com.example.applyfestox.screen.manajemen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.applyfestox.R


@Composable
fun KandangKosong(onTambahKandangClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Halo,\nUcup",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(id = R.drawable.ayamkandang),
            contentDescription = "Chicken Coop",
            modifier = Modifier.size(400.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onTambahKandangClick() }, // Navigasi ke TambahKandangScreen
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7D00)),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(text = "+  Tambah Kandang", color = Color.White, fontSize = 18.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewKandangKosong() {
    KandangKosong(onTambahKandangClick = { /* Preview action */ })
}
