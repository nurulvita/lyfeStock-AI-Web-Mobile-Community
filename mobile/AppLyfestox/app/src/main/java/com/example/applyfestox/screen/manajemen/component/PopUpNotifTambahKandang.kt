package com.example.applyfestox.screen.manajemen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddKandangPopUp(kandangName: String, onAddClick: () -> Unit, onCancelClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xA6000000)), // Semi-transparent background
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(280.dp) // Reduced width a little
                .padding(5.dp) // Reduced padding
                .background(Color.White, RoundedCornerShape(16.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(17.dp) // Less spacing between items
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .size(36.dp),
            )

            Text(
                text = "Tambahkan \"$kandangName\" menjadi kandangmu?",
                fontSize = 15.sp, // Slightly smaller font size
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Button(
                onClick = onAddClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp) // Slightly reduced height
            ) {
                Text("Tambahkan", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = onCancelClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp) // Slightly reduced height
                    .padding(bottom = 10.dp)
            ) {
                Text("Batalkan", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAddKandangPopUp() {
    AddKandangPopUp(
        kandangName = "Kandang A",
        onAddClick = { /* Handle add click */ },
        onCancelClick = { /* Handle cancel click */ }
    )
}