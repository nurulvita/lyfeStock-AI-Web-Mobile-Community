package com.example.applyfestox.uii.screen.deteksi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.applyfestox.R
import com.example.applyfestox.data.sqlite.DatabaseHelper
import theme.Orange600
import theme.Yellow50

@Composable
fun DiagnosaScreen(userId: Int) {
    val context = LocalContext.current
    val dbHelper = DatabaseHelper(context)
    val diagnosaList = remember { dbHelper.getDiagnosaData(userId).sortedByDescending { it.waktu } }

    val showDialog = remember { mutableStateOf(false) }
    val selectedDisease = remember { mutableStateOf("") }
    val selectedImageUri = remember { mutableStateOf("") }

    if (showDialog.value) {
        ShowDiseaseDetails(
            diseaseName = selectedDisease.value,
            imageUri = selectedImageUri.value,
            onDismiss = { showDialog.value = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow50)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Laporan Diagnosa",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (diagnosaList.isNotEmpty()) {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(diagnosaList) { diagnosa ->
                    ReportCard(
                        title = diagnosa.hasil,
                        date = diagnosa.waktu,
                        onClickDetail = {
                            selectedDisease.value = diagnosa.hasil
                            selectedImageUri.value = diagnosa.gambar
                            showDialog.value = true
                        }
                    )
                }
            }
        } else {
            Text(
                text = "Belum ada data diagnosa.",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}


@Composable
fun ReportCard(
    title: String,
    date: String,
    onClickDetail: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Orange600,
        elevation = 6.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClickDetail)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.laporandiagnosa),
                contentDescription = "Chicken Icon",
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = date,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }

            Text(
                text = "Lihat Detail >",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
    }
}


