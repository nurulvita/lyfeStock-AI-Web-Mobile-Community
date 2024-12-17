package com.example.applyfestox.uii.screen.login.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.applyfestox.R
import theme.Yellow50

@Composable
fun LogoSection(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Yellow50)
                .fillMaxHeight(0.30f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = "Background Form",
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
                    .graphicsLayer {
                        alpha = 0.2f
                    }
                    .background(Yellow50)
            )

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Login Logo",
                modifier = Modifier
                    .size(110.dp)
                    .align(Alignment.Center)
            )
        }
    }
}