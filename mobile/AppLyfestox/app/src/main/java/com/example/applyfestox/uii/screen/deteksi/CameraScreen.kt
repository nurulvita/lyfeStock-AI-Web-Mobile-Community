package com.example.applyfestox.screen.deteksi

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import com.example.applyfestox.R
import com.example.applyfestox.data.repository.uploadImage
import com.example.applyfestox.uii.screen.deteksi.createImageFile
import theme.Orange500
import theme.Orange600
import theme.Yellow50
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(navController: NavHostController, userId: Int) {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success && imageUri.value != null) {
                handleImageUpload(
                    uri = imageUri.value,
                    context = context,
                    navController = navController,
                    userId = userId // Pass userId here
                )
            } else {
                Toast.makeText(context, "Gagal mengambil foto", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                handleImageUpload(
                    uri = uri,
                    context = context,
                    navController = navController,
                    userId = userId // Pass userId here
                )
            } else {
                Toast.makeText(context, "Gagal memilih gambar", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow50),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TopAppBar(
            title = { Text("Deteksi Penyakit") },
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

        Spacer(modifier = Modifier.height(50.dp))

        Image(
            painter = painterResource(id = R.drawable.upload),
            contentDescription = "Upload Image",
            modifier = Modifier.size(300.dp).padding(16.dp)
        )

        Text(
            text = "Pilih dari galeri atau ambil foto",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Spacer(modifier = Modifier.height(55.dp))

        ActionButton(
            text = "Buka Galeri",
            icon = R.drawable.ic_gallery,
            backgroundColor = Color.White,
            textColor = Orange600,
            onClick = { galleryLauncher.launch("image/*") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        ActionButton(
            text = "Buka Kamera",
            icon = R.drawable.ic_camera,
            backgroundColor = Orange500,
            textColor = Color.White,
            onClick = {
                val tempFile = createImageFile(context)
                val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", tempFile)
                imageUri.value = uri
                cameraLauncher.launch(uri) // Ensure the imageUri is set before launching camera
            }
        )
    }
}

private fun handleImageUpload(
    uri: Uri?,
    context: android.content.Context,
    navController: NavHostController,
    userId: Int // Pass userId dynamically here
) {
    uri?.let {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(it)

        if (inputStream != null) {
            val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir).apply {
                outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            uploadImage(
                imageFile = tempFile,
                onSuccess = { predictionClass, confidence ->

                    val encodedClass = Uri.encode(predictionClass)
                    val encodedUri = Uri.encode(it.toString())

                    // Pass userId dynamically to the navController
                    navController.navigate("hasilScreen/$encodedClass/$confidence/$encodedUri/$userId")
                },
                onFailure = { error ->
                    Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
                }
            )
        } else {
            Toast.makeText(context, "Gagal membaca gambar", Toast.LENGTH_SHORT).show()
        }
    } ?: Toast.makeText(context, "Gambar tidak ditemukan", Toast.LENGTH_SHORT).show()
}



@Composable
fun ActionButton(
    text: String,
    icon: Int,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(backgroundColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 32.dp, vertical = 8.dp)
            .shadow(8.dp, RoundedCornerShape(50))
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (backgroundColor == Color.White) Orange600 else Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 16.sp, color = textColor)
    }
}