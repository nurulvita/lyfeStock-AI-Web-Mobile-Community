package com.example.applyfestox.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.applyfestox.R

@Composable
fun CustomAppBar(
    title: String,
    navController: NavController,
    isMainScreen: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (isMainScreen) Arrangement.Center else Arrangement.Start
    ) {
        if (!isMainScreen) {
            IconButton(onClick = {
                if (!navController.popBackStack()) {
                    navController.navigateUp()
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
