package com.example.applyfestox.screen.login.component

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.applyfestox.data.sharedpreferences.LoginSessionHelper
import com.example.applyfestox.data.sqlite.DatabaseHelper
import com.example.applyfestox.ui.theme.Orange500
import com.example.applyfestox.ui.theme.Orange600
import com.example.applyfestox.ui.theme.Poppins
import com.example.applyfestox.ui.theme.Yellow50

@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginForm(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val isEmailValid = remember { mutableStateOf(true) }
    val password = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val databaseHelper = DatabaseHelper(context)
    val sessionHelper = LoginSessionHelper(context) // Tambahkan session helper
    var loginError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                focusManager.clearFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Masuk",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Silahkan masuk ke akun anda",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Email Input
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
                isEmailValid.value = it.contains("@")
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
            singleLine = true,
            isError = !isEmailValid.value,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Yellow50,
                focusedIndicatorColor = Orange500,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Red
            )
        )

        if (!isEmailValid.value) {
            Text(
                text = "Email harus mengandung '@'",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Password Input
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Kata Sandi") },
            textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Yellow50,
                focusedIndicatorColor = Orange500,
                unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                val description = if (passwordVisible) "Sembunyikan Kata Sandi" else "Tampilkan Kata Sandi"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = image,
                        contentDescription = description,
                        tint = Orange600
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { /* handle login here */ }
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Lupa Kata Sandi?",
            color = Orange600,
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Login Button
        Button(
            onClick = {
                if (email.value.isNotBlank() && password.value.isNotBlank()) {
                    val isLoginValid = databaseHelper.loginUser(email.value, password.value)
                    if (isLoginValid) {
                        val user = databaseHelper.getUserData(email.value)
                        if (user != null) {
                            sessionHelper.saveLoginSession(user) // Simpan data ke sesi
                            Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show()
                            navController.navigate("main") // Pindah ke layar utama
                        } else {
                            Toast.makeText(context, "Terjadi kesalahan saat memuat data pengguna", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Email atau Kata Sandi Salah", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Isi semua data dengan benar", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(Orange500),
            shape = RoundedCornerShape(40.dp),
            modifier = Modifier
                .width(338.dp)
                .height(45.dp)
                .shadow(8.dp, shape = RoundedCornerShape(40.dp)),
        ) {
            Text(
                text = "Masuk",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = Poppins
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Belum punya akun?",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "Daftar Sekarang",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                color = Orange600,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate("register")
                }
            )
        }
    }
}

