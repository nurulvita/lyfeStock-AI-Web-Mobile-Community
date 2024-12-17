package com.example.applyfestox

//import com.example.applyfestox.data.sharedpreferences.SharedPreferencesManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.applyfestox.uii.screen.MainScreen
import com.example.applyfestox.uii.screen.Register.RegisterScreen
import com.example.applyfestox.uii.screen.login.LoginScreen
import com.example.applyfestox.uii.splash.SplashScreen
import com.example.applyfestox.uii.splash.WelcomeScreen
import kotlinx.coroutines.delay
import theme.AppLyfestoxTheme

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Izin kamera diberikan.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Izin kamera diperlukan untuk menggunakan fitur ini.", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppLyfestoxTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppEntryPoint()
                }
            }
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        } else {
            Toast.makeText(this, "Izin kamera sudah diberikan.", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun AppEntryPoint() {
    val navController = rememberNavController()
    val context = LocalContext.current
//    val sharedPreferencesManager = SharedPreferencesManager(context)

    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate("welcome")
    }

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen()
        }
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("main") {
            MainScreen()
        }
    }

}

