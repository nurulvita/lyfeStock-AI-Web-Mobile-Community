package com.example.applyfestox.screen.hasil

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.applyfestox.data.model.DiseaseInfo
import com.example.applyfestox.data.sqlite.DatabaseHelper
import theme.Orange500
import theme.Orange600
import theme.Yellow50
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun getDiseaseInfo(disease: String): DiseaseInfo {
    return when (disease) {
        "Coccidiosis" -> DiseaseInfo(
            description = "Koksidiosis adalah penyakit parasit yang menyerang saluran pencernaan, khususnya usus pada hewan ternak. Penyakit ini sering disebabkan oleh protozoa dari genus Eimeria yang berkembang biak di dalam saluran pencernaan, sehingga menyebabkan kerusakan jaringan usus dan menimbulkan gejala seperti diare berdarah, penurunan nafsu makan, dan pertumbuhan yang lambat.",
            treatment = "Pengobatan dilakukan dengan menggunakan obat anticoccidial seperti sulfa atau amprolium. Selain itu, perbaikan kondisi lingkungan dan sanitasi juga sangat penting untuk mendukung proses penyembuhan.",
            prevention = "Untuk mencegah koksidiosis, pastikan air minum selalu bersih, kandang memiliki sanitasi yang baik, dan hindari kepadatan ternak yang terlalu tinggi.",
            medication = "Obat-obatan yang biasa digunakan meliputi sulfa drugs dan amprolium."
        )
        "Healthy" -> DiseaseInfo(
            description = "Hewan berada dalam kondisi sehat, yang berarti tidak ada gejala atau tanda-tanda penyakit yang terdeteksi. Kondisi ini mencerminkan manajemen kesehatan yang baik dan lingkungan yang mendukung kesehatan hewan.",
            treatment = "Tidak diperlukan pengobatan khusus karena hewan dalam kondisi prima.",
            prevention = "Lakukan praktik kesehatan yang baik seperti pemberian pakan bergizi, air bersih, dan lingkungan kandang yang terjaga kebersihannya.",
            medication = "Tidak ada obat yang diperlukan."
        )
        "New Castle Disease" -> DiseaseInfo(
            description = "Penyakit New Castle adalah infeksi virus yang sangat menular dan menyerang unggas seperti ayam. Penyakit ini disebabkan oleh virus dari keluarga Paramyxoviridae yang dapat menyebabkan gejala seperti pernapasan sulit, batuk, bersin, kelumpuhan, hingga kematian mendadak.",
            treatment = "Pengobatan utama untuk penyakit ini adalah melalui vaksinasi. Namun, pada unggas yang sudah terinfeksi, hanya perawatan suportif seperti pemberian nutrisi tambahan dan elektrolit yang bisa dilakukan.",
            prevention = "Pencegahan dilakukan dengan memastikan unggas divaksinasi secara rutin, menjaga kebersihan kandang, dan menghindari kontak dengan unggas yang terinfeksi.",
            medication = "Vaksin untuk pencegahan dan terapi suportif untuk meringankan gejala."
        )
        "Salmonella" -> DiseaseInfo(
            description = "Infeksi Salmonella adalah penyakit bakteri yang menyerang usus pada manusia dan hewan. Pada unggas, penyakit ini dapat menyebabkan diare, penurunan berat badan, dan bahkan kematian pada kasus yang parah. Penyakit ini sering menyebar melalui pakan atau air yang terkontaminasi.",
            treatment = "Pengobatan dilakukan dengan menggunakan antibiotik yang diresepkan oleh dokter hewan. Pemantauan kondisi unggas yang terinfeksi juga diperlukan untuk memastikan respons terhadap terapi.",
            prevention = "Pastikan sanitasi kandang selalu terjaga, pakan disimpan dengan baik agar tidak terkontaminasi, dan air minum selalu bersih.",
            medication = "Antibiotik seperti Ciprofloxacin atau yang direkomendasikan oleh dokter hewan."
        )
        else -> DiseaseInfo(
            description = "No information available.",
            treatment = "Consult a veterinarian.",
            prevention = "Maintain good hygiene.",
            medication = "None."
        )
    }
}
data class DetectionHistory(
    val disease: String,
    val confidence: Float,
    val imageUri: String,
    val date: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HasilScreen(
    navController: NavHostController,
    predictionClass: String,
    confidence: Float,
    imageUri: String,
    userId: Int
) {
    val context = LocalContext.current
    val decodedImageUri = Uri.decode(imageUri)
    val imageUriObj = Uri.parse(decodedImageUri)

    val isLowConfidence = confidence < 0.85f
    val diseaseInfo = getDiseaseInfo(predictionClass)

    val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val currentDate = dateFormatter.format(Date())

    val currentTime = remember {
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }

    var showPopup by remember { mutableStateOf(false) }
    val dbHelper = DatabaseHelper(context)

    // Simpan hasil deteksi ke database menggunakan ViewModel
    LaunchedEffect(confidence) {
        val newEntry = DetectionHistory(
            disease = predictionClass,
            confidence = confidence,
            imageUri = imageUri,
            date = "$currentDate | $currentTime"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hasil Deteksi Penyakit") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Orange600,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Yellow50)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Date of Diagnosis
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Orange500)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Tanggal Diagnosis",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$currentDate | $currentTime",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .shadow(4.dp, RoundedCornerShape(16.dp))
                ) {
                    Image(
                        painter = rememberImagePainter(imageUriObj),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                // Confidence Section
                val DarkGreen = Color(0xFF2E7D32)
                val confidenceColor = if (confidence < 0.85f) Color.Red else DarkGreen
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        // Tingkat Kepastian
                        Text(
                            text = "Tingkat Kepastian: ${"%.2f".format(confidence * 100)}%",
                            fontSize = 18.sp,
                            color = confidenceColor,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                        )

                        if (confidence < 0.85f) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "Warning Icon",
                                    tint = Color.Red,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Gambar tidak terdefinisi dengan tingkat kepastian rendah. Silakan ambil gambar dengan benar.",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Success Icon",
                                        tint = DarkGreen,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Gambar terdeteksi penyakit.",
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Button(
                                        onClick = {
                                            val bitmap = uriToBitmap(context, imageUriObj)
                                            val success = dbHelper.saveDiagnosa(
                                                context = context,
                                                waktu = "$currentDate | $currentTime",
                                                hasil = predictionClass,
                                                gambarBitmap = bitmap,
                                                userId = userId
                                            )
                                            if (success) {
                                                Toast.makeText(context, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
                                            } else {
                                                Toast.makeText(context, "Gagal menyimpan data!", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        shape = RoundedCornerShape(50),
                                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp)
                                            .padding(end = 4.dp)
                                    ) {
                                        Text(text = "Simpan", color = Color.White)
                                    }

                                    Button(
                                        onClick = { showPopup = true },
                                        shape = RoundedCornerShape(50),
                                        colors = ButtonDefaults.buttonColors(Orange600),
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp)
                                            .padding(start = 4.dp)
                                    ) {
                                        Text(text = "Selanjutnya", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )

    if (showPopup) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        ) {
            ModalBottomSheet(
                onDismissRequest = { showPopup = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Deskripsi Penyakit:",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        textAlign = TextAlign.Justify,
                        color = Orange600
                    )
                    Text(
                        text = diseaseInfo.description,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Justify
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Cara Penanganan:",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        textAlign = TextAlign.Justify,
                        color = Orange600
                    )
                    Text(
                        text = diseaseInfo.treatment,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Justify
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Pencegahan:",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        textAlign = TextAlign.Justify,
                        color = Orange600,
                    )
                    Text(
                        text = diseaseInfo.prevention,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Justify
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Obat-obatan:",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        textAlign = TextAlign.Justify,
                        color = Orange600
                    )
                    Text(
                        text = diseaseInfo.medication,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    }
}

fun uriToBitmap(context: Context, uri: Uri): Bitmap {
    return context.contentResolver.openInputStream(uri)?.use { inputStream ->
        BitmapFactory.decodeStream(inputStream)
    } ?: throw IllegalArgumentException("Cannot decode bitmap from URI")
}
