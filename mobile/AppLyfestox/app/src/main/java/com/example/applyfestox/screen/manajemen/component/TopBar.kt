package com.example.applyfestox.screen.manajemen.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black, // Set the text color to match the icon
                modifier = Modifier.padding(start = 8.dp) // Adding padding for alignment with the icon
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack, // Back icon
                    contentDescription = "Back",
                    tint = Color.Black // Set the icon color to match the text
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = androidx.compose.material3.TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFFFFF7E6) // Background color of the TopBar
        )
    )
}


@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    // Contoh pemanggilan TopBar dengan sebuah title dan action untuk navigasi kembali
    TopBar(
        title = "Tambah Kandang",
        onBackClick = { /* Handle back action here */ }
    )
}
