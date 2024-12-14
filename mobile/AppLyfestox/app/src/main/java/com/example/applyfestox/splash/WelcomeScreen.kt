package com.example.applyfestox.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.example.applyfestox.screen.manajemen.Poppins
import com.example.applyfestox.ui.theme.Orange500
import com.example.applyfestox.ui.theme.Yellow50

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow50),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 100.dp, height = 116.85.dp)
                    .padding(top = 10.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.peternakan),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 380.dp, height = 330.dp)
                    .padding(top = 10.dp)
            )

            Text(
                text = "Selamat Datang",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = Poppins,
                modifier = Modifier.padding(top = 20.dp)
            )

            Text(
                text = "Nikmati kemudahan dalam mengelola peternakan ayam Anda dengan aplikasi kami. Didesain untuk meningkatkan produktivitas dan efisiensi, semua yang Anda butuhkan ada di ujung jari Anda",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Poppins,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 5.dp, start = 20.dp, end = 20.dp)
            )

            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .width(338.dp)
                    .height(70.dp)
                    .padding(top = 20.dp)
                    .shadow(8.dp, shape = RoundedCornerShape(40.dp)),
                colors = ButtonDefaults.buttonColors(Orange500),
                shape = RoundedCornerShape(40.dp)
            ) {
                Text(
                    text = "Mulai",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = Poppins
                )
            }
        }
    }
}
