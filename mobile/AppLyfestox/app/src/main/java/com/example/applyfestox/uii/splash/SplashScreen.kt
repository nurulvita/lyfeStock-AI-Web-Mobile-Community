package com.example.applyfestox.uii.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.applyfestox.R
import theme.Yellow50

@Composable
fun SplashScreen() {
    val logoAnimation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        logoAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2000)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow50),
        contentAlignment = Alignment.Center
    ) {
        val splashImage: Painter = painterResource(id = R.drawable.splash1) // Ganti dengan nama gambar splash1 Anda
        Image(
            painter = splashImage,
            contentDescription = "Splash Image",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )

        val logo: Painter = painterResource(id = R.drawable.logo)
        Image(
            painter = logo,
            contentDescription = "App Logo",
            modifier = Modifier
                .size(200.dp)
                .alpha(logoAnimation.value)
        )
    }
}
