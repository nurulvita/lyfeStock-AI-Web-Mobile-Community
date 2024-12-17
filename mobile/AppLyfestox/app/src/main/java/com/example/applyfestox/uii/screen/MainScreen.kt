package com.example.applyfestox.uii.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.applyfestox.uii.navigation.BottomNavGraph
import com.example.applyfestox.uii.navigation.NavigationBar
import theme.Orange600
import theme.Yellow50

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        containerColor = Yellow50,
        bottomBar = {
            when (currentRoute) {
                "user_info_screen",
                "help_screen",
                "change_password_screen",
                "article_detail_screen/{articleUrl}",
                "camera_screen/{userId}",
                "Cuaca_Screen",
                "hasilScreen/{predictionClass}/{confidence}/{imageUri}/{userId}",
                "panduan_screen",
                "login_screen",
                "dashboard/{period}/{location}/{duration}/{chickenCount}/{weight}/{farmId}/{feedingSchedule}",
                "tambah_kandang_screen",
                "pemberitahuan" -> { /* Do nothing, no BottomBar */ }

                else -> {
                    BottomBar(navController = navController)
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Yellow50)
        ) {
            BottomNavGraph(navController = navController)
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        NavigationBar.Beranda,
        NavigationBar.Manajemen,
        NavigationBar.Deteksi,
        NavigationBar.Profil
    )

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = -20.dp)
            .height(70.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(Yellow50)
            .shadow(elevation = 50.dp, shape = RoundedCornerShape(100.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun AddItem(
    screen: NavigationBar,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    val backgroundColor = if (selected) Orange600 else Color.Transparent
    val contentColor = if (selected) Color.White else Orange600

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(id = if (selected) screen.icon_focused else screen.icon),
                contentDescription = screen.title,
                tint = contentColor
            )
            AnimatedVisibility(visible = selected) {
                Text(
                    text = screen.title,
                    color = contentColor
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BottomNavPreview() {
    MainScreen()
}
