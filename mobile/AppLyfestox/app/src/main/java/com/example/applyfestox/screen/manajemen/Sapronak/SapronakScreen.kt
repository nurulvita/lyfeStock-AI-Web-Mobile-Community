package com.example.applyfestox.screen.manajemen.Sapronak

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.applyfestox.screen.manajemen.Sapronak.sapronakComponents.SapronakButtonBar


@SuppressLint("RememberReturnType")
@Composable
fun SapronakScreen(navController: NavHostController) {

    // Using Scaffold to wrap the entire screen
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFFFF9C4) // Set background color for the scaffold
    ) { paddingValues -> // This is the content area, where the inner content will go
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues) // Apply the padding from the scaffold
        ) {
            // ButtonBar for navigation
            SapronakButtonBar(
                activeButton = "Pakan",
                onButtonClick = { screen -> navController.navigate(screen.lowercase()) }
            )

            // Content area with NavHost
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                SapronakNavigationGraph(navController)
            }
        }
    }
}


@Composable
fun SapronakNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "pakan" // Default screen
    ) {
        composable("pakan") { SaproPakanContent() }
        composable("doc") { SaproDOCContent() }
        composable("ovk") { SaproOVKContent() }
    }
}

@Preview(showBackground = true)
@Composable
fun SapronakScreenPreview() {
    val navController = rememberNavController() // Tambahkan ini
    SapronakScreen(navController = navController)
}
