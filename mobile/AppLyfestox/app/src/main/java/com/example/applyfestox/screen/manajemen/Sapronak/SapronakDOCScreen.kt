package com.example.applyfestox.screen.manajemen.Sapronak

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
//import eu.example.lyfestox.R // Pastikan path sesuai dengan file Anda

@Composable
fun SaproDOCContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9C4))
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
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

@Preview(showBackground = true, widthDp = 360)
@Composable
fun SaproDOCContentPreview() {
    SaproDOCContent()
}
