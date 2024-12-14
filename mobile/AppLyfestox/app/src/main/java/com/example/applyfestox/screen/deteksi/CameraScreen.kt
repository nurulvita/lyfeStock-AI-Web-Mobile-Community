package com.example.applyfestox.screen.deteksi

import android.net.Uri
import android.util.Log
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
import com.example.applyfestox.ui.theme.Orange500
import com.example.applyfestox.ui.theme.Orange600
import com.example.applyfestox.ui.theme.Yellow50

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(navController: NavHostController) {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                val result = "Penyakit X terdeteksi"
                navController.navigate("hasilScreen/$result?uri=${imageUri.value}")
            } else {
                Log.e("CameraError", "Failed to capture image.")
                Toast.makeText(context, "Failed to capture image", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                Log.d("Gallery", "Image selected: $uri")
                navController.navigate("hasilScreen?uri=$uri")
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
                    IconButton(onClick = { navController.popBackStack() }) { // Back navigation
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Orange600,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )

        Spacer(modifier = Modifier.height(50.dp))

        Image(
            painter = painterResource(id = R.drawable.upload),
            contentDescription = "Upload Image",
            modifier = Modifier
                .size(300.dp)
                .padding(16.dp)
        )

        Text(text = "Pilih dari galeri atau ambil foto", fontSize = 16.sp, modifier = Modifier.padding(bottom = 32.dp))

        Spacer(modifier = Modifier.height(55.dp))

        Button(
            onClick = { galleryLauncher.launch("image/*") },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 32.dp, vertical = 8.dp)
                .shadow(8.dp, RoundedCornerShape(50))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_gallery),
                contentDescription = "Gallery",
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Buka Galeri", fontSize = 16.sp, color = Orange600)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val tempFile = createImageFile(context)
                val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", tempFile)
                imageUri.value = uri
                cameraLauncher.launch(uri)
            },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(Orange500),
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 32.dp, vertical = 8.dp)
                .shadow(8.dp, RoundedCornerShape(50))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = "Camera",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Buka Kamera", fontSize = 16.sp, color = Color.White)
        }
    }
}