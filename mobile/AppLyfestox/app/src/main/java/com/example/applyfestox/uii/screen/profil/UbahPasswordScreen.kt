package com.example.applyfestox.uii.screen.profil

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.applyfestox.data.sharedpreferences.LoginSessionHelper
import com.example.applyfestox.data.sqlite.DatabaseHelper
import theme.Orange500
import theme.Orange600
import theme.Yellow50

@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UbahPassword(navController: NavHostController, databaseHelper: DatabaseHelper, loginSessionHelper: LoginSessionHelper) {
    val currentUser = loginSessionHelper.getLoggedInUser()
    if (currentUser == null) {
        Text("Pengguna belum login")
        return
    }

    var sandiLama by remember { mutableStateOf("") }
    var sandiBaru by remember { mutableStateOf("") }
    var sandiBaruUlangi by remember { mutableStateOf("") }
    var isPasswordVisibleLama by remember { mutableStateOf(false) }
    var isPasswordVisibleBaru by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf("") }
    var passwordUpdated by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow50)
            .padding(horizontal = 16.dp)
            .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                focusManager.clearFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ubah Kata Sandi",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Form untuk input kata sandi lama dan baru
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD39A))
        ) {
            Column(
                modifier = Modifier.padding(30.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = sandiLama,
                    onValueChange = { sandiLama = it },
                    label = { Text("Masukkan Kata Sandi Lama") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (isPasswordVisibleLama) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisibleLama = !isPasswordVisibleLama }) {
                            Icon(
                                imageVector = if (isPasswordVisibleLama) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (isPasswordVisibleLama) "Sembunyikan Kata Sandi" else "Tampilkan Kata Sandi",
                                tint = Orange600
                            )
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Orange500,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = sandiBaru,
                    onValueChange = { sandiBaru = it },
                    label = { Text("Masukkan Kata Sandi Baru") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (isPasswordVisibleBaru) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisibleBaru = !isPasswordVisibleBaru }) {
                            Icon(
                                imageVector = if (isPasswordVisibleBaru) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (isPasswordVisibleBaru) "Sembunyikan Kata Sandi" else "Tampilkan Kata Sandi",
                                tint = Orange600
                            )
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Orange500,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = sandiBaruUlangi,
                    onValueChange = { sandiBaruUlangi = it },
                    label = { Text("Ulangi Kata Sandi Baru") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (isPasswordVisibleBaru) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisibleBaru = !isPasswordVisibleBaru }) {
                            Icon(
                                imageVector = if (isPasswordVisibleBaru) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (isPasswordVisibleBaru) "Sembunyikan Kata Sandi" else "Tampilkan Kata Sandi",
                                tint = Orange600
                            )
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Orange500,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                if (passwordError.isNotEmpty()) {
                    Text(
                        text = passwordError,
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(Orange600),
                shape = RoundedCornerShape(40.dp),
                modifier = Modifier.width(120.dp)
            ) {
                Text(text = "Batal", color = Color.White)
            }

            Button(
                onClick = {
                    if (sandiLama.isNotEmpty()) {
                        val userId = databaseHelper.loginUser(currentUser.email, sandiLama)
                        if (userId != null) {
                            if (sandiBaru == sandiBaruUlangi) {
                                val isUpdated = databaseHelper.updateUserPassword(currentUser.id, sandiBaru)
                                if (isUpdated) {
                                    passwordUpdated = true
                                    Toast.makeText(context, "Kata sandi berhasil diperbarui", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()

                                } else {
                                    passwordError = "Gagal mengubah kata sandi"
                                }
                            } else {
                                passwordError = "Kata sandi baru tidak cocok"
                            }
                        } else {
                            passwordError = "Kata sandi lama salah"
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(Orange600),
                shape = RoundedCornerShape(40.dp),
                modifier = Modifier.width(120.dp)
            ) {
                Text(text = "Simpan", color = Color.White)
            }
        }
    }
}
