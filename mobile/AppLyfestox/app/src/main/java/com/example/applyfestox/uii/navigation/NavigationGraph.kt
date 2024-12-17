package com.example.applyfestox.uii.navigation

import PanduanDetailScreen
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.applyfestox.data.sharedpreferences.LoginSessionHelper
import com.example.applyfestox.data.sqlite.DatabaseHelper
import com.example.applyfestox.data.sqlite.UserViewModel
import com.example.applyfestox.data.sqlite.UserViewModelFactory
import com.example.applyfestox.screen.deteksi.CameraScreen
import com.example.applyfestox.screen.hasil.HasilScreen
import com.example.applyfestox.screen.manajemen.DashboardKandangScreen
import com.example.applyfestox.screen.manajemen.ManajemenScreen
import com.example.applyfestox.uii.screen.Register.RegisterScreen
import com.example.applyfestox.uii.screen.beranda.ArticleDetailScreen
import com.example.applyfestox.uii.screen.beranda.BerandaScreen
import com.example.applyfestox.uii.screen.beranda.CuacaScreen
import com.example.applyfestox.uii.screen.beranda.PemberitahuanScreen
import com.example.applyfestox.uii.screen.deteksi.DeteksiScreen
import com.example.applyfestox.uii.screen.deteksi.DiagnosaScreen
import com.example.applyfestox.uii.screen.login.LoginScreen
import com.example.applyfestox.uii.screen.manajemen.TambahKandangScreen
import com.example.applyfestox.uii.screen.profil.LaporanKendalaScreen
import com.example.applyfestox.uii.screen.profil.ProfileScreen
import com.example.applyfestox.uii.screen.profil.UbahPassword
import com.example.applyfestox.uii.screen.profil.UserInfoScreen


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
                onBackClick = { navController.popBackStack() },
                farmId = backStackEntry.arguments?.getString("farmId") ?: "",
                feedingSchedule = backStackEntry.arguments?.getString("feedingSchedule") ?: "",
                navController = navController // Pass the existing navController
                )
           }
        composable(
            route = "deteksiScreen/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            DeteksiScreen(navController = navController, userId = userId)
        }


        // Camera Screen
        composable("camera_screen/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            CameraScreen(navController = navController, userId = userId)
        }
        // Hasil Screen with optional parameters
        composable(
            "hasilScreen/{predictionClass}/{confidence}/{imageUri}/{userId}",
            arguments = listOf(
                navArgument("predictionClass") { type = NavType.StringType },
                navArgument("confidence") { type = NavType.FloatType },
                navArgument("imageUri") { type = NavType.StringType },
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val predictionClass = backStackEntry.arguments?.getString("predictionClass") ?: ""
            val confidence = backStackEntry.arguments?.getFloat("confidence") ?: 0f
            val imageUri = backStackEntry.arguments?.getString("imageUri") ?: ""
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0 // Perbaikan di sini

            HasilScreen(
                navController = navController,
                predictionClass = predictionClass,
                confidence = confidence,
                imageUri = imageUri,
                userId = userId
            )
        }


        composable(route = NavigationBar.Profil.route) {
            val context = LocalContext.current
            val loginSessionHelper = LoginSessionHelper(context)
            val loggedInUser = loginSessionHelper.getLoggedInUser()

            if (loggedInUser != null) {
                ProfileScreen(
                    user = loggedInUser, // Kirim data user
                    onNavigateToUserInfo = { navController.navigate("user_info_screen") },
                    onNavigateToChangePassword = { navController.navigate("change_password_screen") },
                    onNavigateToHelp = { navController.navigate("help_screen") },
                    onLogout = {
                        loginSessionHelper.clearLoginSession() // Hapus sesi login
                        navController.navigate("login_screen") {
                            popUpTo(0) { inclusive = true } // Hapus semua layar dari stack
                            launchSingleTop = true
                        }
                    }
                )
            } else {
                // Jika sesi tidak ada, arahkan ke login_screen
                LaunchedEffect(Unit) {
                    navController.navigate("login_screen") {
                        popUpTo(0) { inclusive = true } // Reset navigasi sepenuhnya
                        launchSingleTop = true
                    }
                }
            }
        }
// Tambah Kandang Screen
        composable("tambah_kandang_screen") {
            TambahKandangScreen(navController = navController)
        }


        composable(route = "panduan_screen") {
            PanduanDetailScreen(navController = navController)
        }

        composable(
            "diagnosaScreen/{userId}",
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            DiagnosaScreen(userId = userId)
        }

        composable(route = "user_info_screen") {
            val context = LocalContext.current
            val databaseHelper = DatabaseHelper(context)
            val loginSessionHelper = LoginSessionHelper(context)

            UserInfoScreen(
                navController = navController,
                databaseHelper = databaseHelper,
                loginSessionHelper = loginSessionHelper
            )
        }

        composable(route = "change_password_screen") {
            val context = LocalContext.current
            val loginSessionHelper = LoginSessionHelper(context)
            val databaseHelper = DatabaseHelper(context)
            UbahPassword(
                navController = navController,
                loginSessionHelper = loginSessionHelper,
                databaseHelper = databaseHelper
            )
        }

        composable(route = "help_screen") {

            LaporanKendalaScreen(navController = navController)
        }
        composable(route = "beranda_Screen") {
            val context = LocalContext.current
            BerandaScreen(navController = navController, context = context)
        }

        composable(route = "pemberitahuan") {
            PemberitahuanScreen(navController = navController)
        }

        composable(route = "register") {
            RegisterScreen(navController = navController)
        }

        composable("article_detail_screen/{articleUrl}") { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("articleUrl")
            val articleUrl = encodedUrl?.let { Uri.decode(it) }
            articleUrl?.let {
                ArticleDetailScreen(articleUrl = it, navController = navController)
            }
        }
        composable(route = "login_screen") {
            LoginScreen(navController = navController)
        }

        composable(route = "Cuaca_Screen") {
            CuacaScreen(navController = navController, viewModel())
        }

        composable("deteksi/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            DeteksiScreen(navController = navController, userId = userId)
        }


    }
}