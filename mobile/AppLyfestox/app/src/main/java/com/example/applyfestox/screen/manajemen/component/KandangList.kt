package com.example.applyfestox.screen.manajemen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.applyfestox.R
import com.example.applyfestox.model.KandangData
import com.example.applyfestox.ui.theme.Orange500
import com.example.applyfestox.ui.theme.Poppins

@Composable
fun KandangList(kandangList: List<KandangData>, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        items(kandangList) { kandang ->
            KandangCard(
                id = kandang.id,
                name = kandang.name,
                period = kandang.period,
                location = kandang.location,
                duration = kandang.duration,
                chickenCount = kandang.chickenCount,
                weight = kandang.weight,
                imageResId = kandang.imageResId,
                feedingSchedule = kandang.feedingSchedule,
                onCardClick = {
                    navController.navigate(
                        "dashboard/${kandang.period}/${kandang.location}/${kandang.duration}/${kandang.chickenCount}/${kandang.weight}/${kandang.id}/${kandang.feedingSchedule}"
                    )
                }
            )
            Spacer(modifier = Modifier.height(8.dp)) // Adds spacing between cards
        }
    }
}

@Composable
fun KandangCard(
    id: Int,
    name: String,
    period: String,
    location: String,
    duration: String,
    chickenCount: String,
    weight: String,
    imageResId: Int,
    feedingSchedule: String,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable { onCardClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "Kandang Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(
                            color = Orange500.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins,
                        fontSize = 14.sp
                    )
                }
            }

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = period,
                    color = Orange500,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = location,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins,
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(1.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.calender),
                        contentDescription = "Calendar Icon"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(duration, fontFamily = Poppins, fontSize = 12.sp)

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ayam),
                        contentDescription = "Chicken Icon"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(chickenCount, fontFamily = Poppins, fontSize = 12.sp)

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ayam),
                        contentDescription = "Weight Icon"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(weight, fontFamily = Poppins, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(5.dp))

                // Jadwal Pemberian Pakan with yellow box
                Box(
                    modifier = Modifier
                        .background(
                            color = Orange500.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Jadwal Pemberian Pakan: $feedingSchedule",
                        fontFamily = Poppins,
                        fontSize = 12.sp,
                        color = Orange500,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun NoKandangMessage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Tidak Ada Kandang",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            fontFamily = Poppins
        )
    }
}