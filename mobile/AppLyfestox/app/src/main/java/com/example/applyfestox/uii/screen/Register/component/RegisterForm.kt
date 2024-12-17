package com.example.applyfestox.uii.screen.Register.component

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.applyfestox.data.sqlite.DatabaseHelper
import theme.Orange600
import theme.Yellow50
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun RegisterForm(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val birthDate = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }
    val confirmPasswordVisible = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val isEmailValid = remember { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current

    val databaseHelper = DatabaseHelper(context)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                focusManager.clearFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Daftar",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Isi formulir di bawah untuk membuat akun baru",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            OutlinedTextField(
                value = email.value,
                onValueChange = {
                    email.value = it
                    isEmailValid.value = it.contains("@")
                },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Yellow50),
                isError = !isEmailValid.value,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Orange600,
                    cursorColor = Orange600
                )
            )

            if (!isEmailValid.value) {
                Text(
                    text = "Email harus mengandung '@'",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text("Nama Pengguna") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Yellow50),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Orange600,
                    cursorColor = Orange600
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            BirthDateField(
                birthDate = birthDate
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Kata Sandi") },
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(
                            imageVector = if (passwordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = Orange600
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Yellow50),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Orange600,
                    cursorColor = Orange600
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            OutlinedTextField(
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it },
                label = { Text("Konfirmasi Kata Sandi") },
                visualTransformation = if (confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible.value = !confirmPasswordVisible.value }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = Orange600
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Yellow50),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Orange600,
                    cursorColor = Orange600
                )
            )

            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            Button(
                onClick = {
                    if (email.value.isNotBlank() && username.value.isNotBlank() &&
                        birthDate.value.isNotBlank() && password.value == confirmPassword.value
                    ) {
                        val success = databaseHelper.registerUser(
                            email = email.value,
                            username = username.value,
                            birthDate = birthDate.value,
                            password = password.value,
                            photo = "default_photo.jpg"
                        )

                        if (success) {
                            Toast.makeText(context, "Registrasi berhasil!", Toast.LENGTH_LONG).show()
                            navController.navigate("login")
                        } else {
                            Toast.makeText(context, "Registrasi gagal.", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(context, "Isi semua data dengan benar.", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange600,
                    contentColor = Color.White
                )
            ) {
                Text("Daftar")
            }
        }
        item {
            Text(
                text = buildAnnotatedString {
                    append("Sudah punya akun? ")
                    withStyle(style = SpanStyle(color = Orange600, fontWeight = FontWeight.Bold)) {
                        append("Masuk")
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .clickable { navController.navigate("login") }
                    .padding(vertical = 8.dp)
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDateField(birthDate: MutableState<String>) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            birthDate.value = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    OutlinedTextField(
        value = birthDate.value,
        onValueChange = { },
        label = { Text("Tanggal Lahir") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = Orange600
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Yellow50),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Orange600,
            cursorColor = Orange600
        )
    )
}




