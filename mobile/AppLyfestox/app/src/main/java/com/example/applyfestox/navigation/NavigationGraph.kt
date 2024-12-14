package com.example.applyfestox.navigation

import PanduanDetailScreen
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.applyfestox.data.sqlite.UserViewModel
import com.example.applyfestox.data.sqlite.UserViewModelFactory
import com.example.applyfestox.screen.Register.RegisterScreen
import com.example.applyfestox.screen.beranda.ArticleDetailScreen
import com.example.applyfestox.screen.beranda.BerandaScreen
import com.example.applyfestox.screen.beranda.PemberitahuanScreen
import com.example.applyfestox.screen.deteksi.CameraScreen
import com.example.applyfestox.screen.deteksi.DeteksiScreen
import com.example.applyfestox.screen.deteksi.HasilScreen
//import com.example.applyfestox.screen.deteksi.PanduanDetailScreen
import com.example.applyfestox.screen.login.LoginScreen
import com.example.applyfestox.screen.manajemen.DashboardKandangScreen
import com.example.applyfestox.screen.manajemen.ManajemenScreen
import com.example.applyfestox.screen.manajemen.TambahKandangScreen
import com.example.applyfestox.screen.manajemen.TambahPakanForm
import com.example.applyfestox.screen.profil.LaporanKendalaScreen
import com.example.applyfestox.screen.profil.ProfileScreen
import com.example.applyfestox.screen.profil.UbahPassword
import com.example.applyfestox.screen.profil.UserInfoScreen


@Composable
fun BottomNavGraph(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController,
        startDestination = NavigationBar.Beranda.route
    ) {
        composable(route = NavigationBar.Beranda.route) {
            val context = LocalContext.current
            BerandaScreen(navController = navController, context = context)
        }
        // Manajemen Screen
        composable("manajemen") {
            val context = LocalContext.current
            // Inisialisasi UserViewModel dengan factory dan passing ke ManajemenScreen
            val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(context))
            ManajemenScreen(navController = navController)
        }

        composable(
            route = "dashboard/{period}/{location}/{duration}/{chickenCount}/{weight}/{farmId}/{feedingSchedule}"
        ) { backStackEntry ->
            DashboardKandangScreen(
                period = backStackEntry.arguments?.getString("period") ?: "",
                location = backStackEntry.arguments?.getString("location") ?: "",
                duration = backStackEntry.arguments?.getString("duration") ?: "",
                chickenCount = backStackEntry.arguments?.getString("chickenCount") ?: "",
                weight = backStackEntry.arguments?.getString("weight") ?: "",
                farmName = backStackEntry.arguments?.getString("farmName") ?: "",
                farmId = backStackEntry.arguments?.getString("farmId") ?: "",
                feedingSchedule = backStackEntry.arguments?.getString("feedingSchedule") ?: "",
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(route = NavigationBar.Deteksi.route) {
            DeteksiScreen(navController)
        }

        // Camera Screen
        composable(route = "camera_screen") {
            CameraScreen(navController)
        }

        // Hasil Screen with optional parameters
        composable("hasilScreen?result={result}&uri={uri}") { backStackEntry ->
            val result = backStackEntry.arguments?.getString("result") ?: "Tidak ada hasil"
            val encodedUri = backStackEntry.arguments?.getString("uri")
            val imageUri = encodedUri?.let { Uri.decode(it) }?.let { Uri.parse(it) }

            HasilScreen(
                navController = navController,
                result = result,
                imageUri = imageUri
            )
        }
        composable(route = NavigationBar.Profil.route) {
            ProfileScreen(
                onNavigateToUserInfo = { navController.navigate("user_info_screen") },
                onNavigateToChangePassword = { navController.navigate("change_password_screen") },
                onNavigateToHelp = { navController.navigate("help_screen") },
                onLogout = {
                    // Implementasi logout di sini, misalnya:
                    navController.navigate("login") {
                        popUpTo(0) // Menghapus semua stack untuk logout
                    }
                }
            )
        }



        //rute tambah kandang
        composable(route = "tambah_kandang_screen"){
            TambahKandangScreen(navController = navController)
        }
        //rute screen manajemen
        composable(route = "manajemen_screen"){
            TambahKandangScreen(navController = navController)
        }

        //rute screen TambahPakanForm
        composable(route = "form_pakan_masuk_screen"){
            TambahPakanForm(navController = navController)
        }

        composable(route = "panduan_screen") {
            PanduanDetailScreen(navController = navController)
        }

        composable(route = "user_info_screen") {
            UserInfoScreen(navController = navController)
        }
        composable(route = "change_password_screen") {
            UbahPassword(navController = navController)
        }
        composable(route = "help_screen") {
            LaporanKendalaScreen(navController = navController)
        }

        composable(route = "pemberitahuan") {
            PemberitahuanScreen(navController = navController)
        }

        composable(route = "register") {
            RegisterScreen(navController = navController)
        }
        composable(route = "Login_Screen") {
            LoginScreen(navController = navController)
        }
        composable("article_detail_screen/{articleUrl}") { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("articleUrl")
            val articleUrl = encodedUrl?.let { Uri.decode(it) }
            articleUrl?.let {
                ArticleDetailScreen(articleUrl = it, navController = navController)
            }
        }
    }
}
