package com.example.applyfestox.uii.screen.deteksi

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.applyfestox.R
import com.example.applyfestox.screen.hasil.getDiseaseInfo
import theme.Orange500
import theme.Orange600
import theme.Yellow50
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
fun DeteksiScreen(navController: NavHostController, userId: Int) {
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

        DiagnosisCard(navController, userId)

        GuideCard(navController)

        DiagnosaScreen(userId)
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
fun DiagnosisCard(navController: NavHostController,userId: Int) {
    CustomCard(
        iconRes = R.drawable.diagnosaayam,
        title = "Diagnosa",
        description = "Ambil foto feses pada ayam anda",
        buttonText = "Cek kesehatan",
        onClick = { navController.navigate("camera_screen/$userId") }
    )
}

@Composable
fun CustomCard(
    iconRes: Int,
    title: String,
    description: String,
    buttonText: String? = null,
    onClick: () -> Unit
) {
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
                painter = painterResource(id = iconRes),
                contentDescription = "Icon",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                buttonText?.let {
                    Button(
                        onClick = onClick,
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(Orange500),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_camera_phone),
                            contentDescription = "Cek kesehatan",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = it,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

fun createImageFile(context: Context): File {
    val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return try {
        File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir)
    } catch (e: Exception) {
        Log.e("ImageFile", "Error creating image file", e)
        throw e
    }
}

fun processCapturedImage(imageUri: Uri) {
    Log.d("CapturedImage", "Image URI: $imageUri")
}

fun checkAndRequestCameraPermission(context: Context, onPermissionGranted: () -> Unit) {
    val permissions = arrayOf(android.Manifest.permission.CAMERA)

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

//@Composable
//fun ReportCard(
//    title: String,
//    date: String,
//    imageUri: String,
//    onClickDetail: () -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .padding(8.dp)
//            .fillMaxSize()
//    ) {
//        // Display the image from URI (using Coil to load the image)
//        val imagePainter = rememberAsyncImagePainter(Uri.parse(imageUri))
//        Image(
//            painter = imagePainter,
//            contentDescription = "Hasil Diagnosis Image",
//            modifier = Modifier
//                .padding(bottom = 8.dp)
//                .fillMaxWidth()
//                .height(200.dp), // Adjust the height as needed
//        )
//
//        Text(
//            text = title,
//            fontSize = 18.sp,
//            color = Color.Black,
//            modifier = Modifier.padding(bottom = 4.dp)
//        )
//
//        Text(
//            text = date,
//            fontSize = 14.sp,
//            color = Color.Gray,
//            modifier = Modifier.padding(bottom = 8.dp)
//        )
//
//        // Button or Action to view details (optional)
//        Text(
//            text = "Lihat Detail",
//            color = Color.Blue,
//            modifier = Modifier
//                .padding(top = 8.dp)
//                .clickable { onClickDetail() }
//        )
//    }
//}

@Composable
fun ShowDiseaseDetails(
    diseaseName: String,
    imageUri: String,
    onDismiss: () -> Unit
) {
    val diseaseInfo = getDiseaseInfo(diseaseName)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Detail Penyakit",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = Orange600)

            )
        },
        text = {
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Display Image
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Diagnosis Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 16.dp)
                )

                // Title and Disease Name
                Text(
                    text = "Nama Penyakit:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodyMedium, color = Orange600
                )
                Text(
                    text = diseaseName,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontSize = 16.sp,
                )

                // Description Section
                Text(
                    text = "Deskripsi:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodyMedium, color = Orange600
                )
                Text(
                    text = diseaseInfo.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Justify,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Pengobatan:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodyMedium, color = Orange600
                )
                Text(
                    text = diseaseInfo.treatment,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Justify,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Pencegahan:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodyMedium, color = Orange600
                )
                Text(
                    text = diseaseInfo.prevention,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Justify,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Obat-obatan:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium, color = Orange600
                )
                Text(
                    text = diseaseInfo.medication,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Justify,
                    fontSize = 16.sp,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                content = {
                    Text(
                        text = "Tutup",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Orange600
                    )
                }
            )
        },
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color.White,
        shape = MaterialTheme.shapes.medium
    )
}




