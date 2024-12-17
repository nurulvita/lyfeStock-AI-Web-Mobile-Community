package com.example.applyfestox.uii.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.applyfestox.R
import theme.Orange500
import theme.Poppins
import theme.Yellow50

@Composable
fun WelcomeScreen(navController: NavController) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow50),
    ) {
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = screenWidth * 0.05f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(screenWidth * 0.25f)
                    .padding(top = screenHeight * 0.01f)
            )

            Image(
                painter = painterResource(id = R.drawable.peternakan),
                contentDescription = null,
                modifier = Modifier
                    .size(screenWidth * 0.9f, screenHeight * 0.4f)
                    .padding(top = screenHeight * 0.02f)
            )

            Text(
                text = "Selamat Datang",
                fontSize = (screenWidth * 0.075f).value.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = Poppins,
                modifier = Modifier.padding(vertical = screenHeight * 0.03f) // 3% tinggi layar
            )

            Text(
                text = "Nikmati kemudahan dalam mengelola peternakan ayam Anda dengan aplikasi kami. Didesain untuk meningkatkan produktivitas dan efisiensi, semua yang Anda butuhkan ada di ujung jari Anda",
                fontSize = (screenWidth * 0.04f).value.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Poppins,
                color = Color.Gray,
                textAlign = TextAlign.Justify,
            )

            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .width(screenWidth * 0.8f)
                    .height(screenHeight * 0.08f)
                    .padding(top = screenHeight * 0.03f)
                    .shadow(8.dp, shape = RoundedCornerShape(screenHeight * 0.04f)),
                colors = ButtonDefaults.buttonColors(Orange500),
                shape = RoundedCornerShape(screenHeight * 0.04f)
            ) {
                Text(
                    text = "Mulai",
                    color = Color.White,
                    fontSize = (screenWidth * 0.04f).value.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = Poppins
                )
            }
        }
    }
}
