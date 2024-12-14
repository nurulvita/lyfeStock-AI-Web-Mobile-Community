package com.example.applyfestox.screen.manajemen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.applyfestox.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HasilDiagnosaScreen(
    morningFeedTime: String = "07:00 WIB",
    eveningFeedTime: String = "16:00 WIB",
    notificationReminder: String = "Ingat untuk memberikan pakan tepat waktu!",
    onBack: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.taiayam),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-100).dp),
            alignment = Alignment.Center
        )

        // Top Bar
        TopAppBar(
            title = { Text(text = "Jadwal Pakan") },
            navigationIcon = {
                IconButton(onClick = { onBack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Arrow"
                    )
                }
            },
            modifier = Modifier.align(Alignment.TopCenter),
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.White
            )
        )

        // Content with Rounded White Surface
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(Color.White)
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Jadwal Pakan Pagi: $morningFeedTime",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Jadwal Pakan Sore: $eveningFeedTime",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = notificationReminder,
                fontSize = 16.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Button to Edit Feeding Schedule
            Button(
                onClick = { /* Aksi untuk edit */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Edit Jadwal Pemberian Pakan", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Button to Delete Feeding Schedule
            Button(
                onClick = { /* Aksi untuk hapus */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Hapus Jadwal Pemberian Pakan", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHasilDiagnosaScreen() {
    HasilDiagnosaScreen()
}
