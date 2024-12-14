package com.example.applyfestox.screen.deteksi

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.applyfestox.ui.theme.Orange500
import com.example.applyfestox.ui.theme.Yellow50
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HasilScreen(
    navController: NavHostController,
    result: String,
    imageUri: Uri?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow50)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TopAppBar(
            title = { Text(text = "Hasil Diagnosis", fontSize = 20.sp) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Yellow50)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Hasil Diagnosis Anda:",
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = result,
            fontSize = 24.sp,
            color = Orange500,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it) // Pass the correct URI
                        .crossfade(true)
                        .error(android.R.drawable.ic_menu_report_image) // Fallback in case of error
                        .build()
                ),
                contentDescription = "Gambar Hasil",
                modifier = Modifier
                    .size(300.dp) // Perbesar ukuran gambar
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp)) // Rounded corners
                    .border(2.dp, Color.Gray, RoundedCornerShape(16.dp)), // Border dengan rounded corners
                contentScale = ContentScale.Crop
            )
        } ?: run {
            Text(
                text = "Gambar tidak tersedia",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(16.dp)
            )
        }

        val file = File(imageUri?.path ?: "")
        if (file.exists()) {
            Log.d("HasilScreen", "File exists: ${file.absolutePath}")
        } else {
            Log.d("HasilScreen", "File does not exist")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.popBackStack() },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(Orange500),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = "Kembali", fontSize = 16.sp, color = Color.White)
        }
    }
}