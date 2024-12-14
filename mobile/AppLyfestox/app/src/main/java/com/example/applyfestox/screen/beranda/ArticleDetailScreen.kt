package com.example.applyfestox.screen.beranda

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.applyfestox.ui.theme.Orange600
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArticleDetailScreen(
    articleUrl: String,
    navController: NavHostController
) {
    val webViewState = rememberWebViewState(url = articleUrl)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Artikel") },
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
        }

    ) { contentPadding ->
        WebView(
            state = webViewState,
            modifier = Modifier.padding(contentPadding)
        )
    }
}