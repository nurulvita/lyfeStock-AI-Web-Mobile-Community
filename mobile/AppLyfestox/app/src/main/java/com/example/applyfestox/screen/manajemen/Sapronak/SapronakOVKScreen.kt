package com.example.applyfestox.screen.manajemen.Sapronak

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import eu.example.lyfestox.R

@Composable
fun SaproOVKContent(onAddClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9C4))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
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
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFC107),
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }

        // Tombol Tambah di bawah layar
        FloatingActionButton(
            onClick = onAddClick,
            shape = CircleShape,
            containerColor = Color(0xFFFFC107),
            modifier = Modifier
                .align(Alignment.BottomCenter)
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

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun SaproOvkContentPreview() {
    SaproOVKContent {
        // Aksi ketika tombol "+" ditekan (contoh fungsi kosong untuk preview)
    }
}
