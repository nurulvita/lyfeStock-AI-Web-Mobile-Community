package com.example.applyfestox.screen.deteksi

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.applyfestox.R
import com.example.applyfestox.ui.theme.Orange500
import com.example.applyfestox.ui.theme.Yellow50
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val poppinsFontFamily = FontFamily(
    Font(R.font.poppins_regular),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeteksiScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow50)
            .padding(16.dp)
    ) {
        TopAppBar(
            title = {
                Image(
                    painter = painterResource(id = R.drawable.logo22),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(130.dp)
                        .background(Yellow50)

                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Yellow50)
        )
        Text(
            text = "Diagnosa Penyakit",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Orange500,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        DiagnosisCard(navController)

        GuideCard(navController)

        Text(
            text = "Laporan Diagnosa",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        ReportCard("Hasil Diagnosa", "15 Oktober 2024", Orange500)
        ReportCard("Hasil Diagnosa", "02 Oktober 2024", Orange500)
        ReportCard("Hasil Diagnosa", "17 September 2024", Orange500)
    }
}

@Composable
fun GuideCard(navController: NavHostController) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.panduan),
                contentDescription = "Guide",
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Panduan",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )

            Row(
                modifier = Modifier
                    .clickable { navController.navigate("panduan_screen") }
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Lihat Detail",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Orange500,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_right_arrow_orange),
                    contentDescription = "Right Arrow",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}


@Composable
fun DiagnosisCard(navController: NavHostController) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.diagnosaayam),
                contentDescription = "diagnosis",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Diagnosa",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    text = "Ambil foto feses pada ayam anda",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Button(
                    onClick = {
                        // Navigate to CameraScreen
                        navController.navigate("camera_screen")
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(Orange500),
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_camera_phone),
                        contentDescription = "Cek kesehatan",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Cek kesehatan",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}


fun createImageFile(context: Context): File {
    val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG_${timestamp}_", /* Prefix */
        ".jpg",              /* Suffix */
        storageDir           /* Directory */
    )
}

fun processCapturedImage(imageUri: Uri) {
    Log.d("CapturedImage", "Image URI: $imageUri")
}


fun checkAndRequestCameraPermission(context: Context, onPermissionGranted: () -> Unit) {
    val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    val missingPermissions = permissions.filter {
        ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
    }

    if (missingPermissions.isEmpty()) {
        onPermissionGranted()
    } else {
        ActivityCompat.requestPermissions(
            context as Activity,
            missingPermissions.toTypedArray(),
            100
        )
    }
}


@Composable
fun ReportCard(title: String, date: String, backgroundColor: Color) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.laporandiagnosa),
                contentDescription = "Report Icon",
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Text(
                    text = date,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier.clickable { /* Handle the click action */ }
            ) {
                Text(
                    text = "Lihat Detail",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_right_arrow_white),
                    contentDescription = "Right Arrow",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}