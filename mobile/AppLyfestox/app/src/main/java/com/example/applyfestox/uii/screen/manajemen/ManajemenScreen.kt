package com.example.applyfestox.screen.manajemen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.applyfestox.R
import com.example.applyfestox.data.dummy.KandangDummyData
import com.example.applyfestox.uii.screen.manajemen.component.KandangList
import com.example.applyfestox.uii.screen.manajemen.component.NoKandangMessage
import com.example.applyfestox.uii.screen.manajemen.component.TabSection
import theme.Orange500
import theme.Poppins
import theme.Yellow50


@Composable
fun TitleScreen() {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Kandang Kamu",
            color = Orange500,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManajemenScreen(navController: NavController) {

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Aktif", "Rehat")

    val aktifKandangList = KandangDummyData.aktifKandangList
    val rehatKandangList = KandangDummyData.rehatKandangList


    val shouldShowFAB = when (selectedTabIndex) {
        0 -> aktifKandangList.isNotEmpty()
        else -> rehatKandangList.isNotEmpty()
    }

    Scaffold(
        topBar = {
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
        },
        floatingActionButton = {
            if (shouldShowFAB) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("tambah_kandang_screen")
                    },
                    containerColor = Orange500,
                    modifier = Modifier.offset(y = (-25).dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tambah Kandang",
                        tint = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Yellow50)
        ) {
            TitleScreen()
            TabSection(selectedTabIndex, tabs) { index -> selectedTabIndex = index }

            val kandangList = if (selectedTabIndex == 0) aktifKandangList else rehatKandangList
            if (kandangList.isEmpty()) {
                if (selectedTabIndex == 0) {
                    AddKandang()
                } else {
                    NoKandangMessage()
                    AddKandang()
                }
            } else {
                KandangList(kandangList = aktifKandangList, navController = navController)
            }
        }
    }
}

@Composable
fun AddKandang() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFF5E1))
            .padding(16.dp)
            .offset(y = (-16).dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(id = R.drawable.ayamkandang),
            contentDescription = "Chicken Coop",
            modifier = Modifier.size(300.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                // TODO: Handle button click for adding kandang
            },
            colors = ButtonDefaults.buttonColors(Orange500),
            shape = RoundedCornerShape(40.dp),
            modifier = Modifier.width(338.dp).height(45.dp)
        ) {
            Text(
                text = "+  Tambah Kandang",
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = Poppins
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun KandangKosongPreview() {
    ManajemenScreen(navController = rememberNavController())
}