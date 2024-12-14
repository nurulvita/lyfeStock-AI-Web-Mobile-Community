package com.example.applyfestox.screen.profil

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.applyfestox.R
import com.example.applyfestox.ui.theme.Orange500
import com.example.applyfestox.ui.theme.Orange600
import com.example.applyfestox.ui.theme.Yellow50

@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaporanKendalaScreen(navController: NavHostController) {
    var namaPengguna by remember { mutableStateOf("") }
    var alamatEmail by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var nomorPonsel by remember { mutableStateOf("") }
    var kendala by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow50)
            .padding(horizontal = 16.dp)
            .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                focusManager.clearFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Apa yang Dapat Kami Bantu?",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.aa),
                    contentDescription = "Help Icon",
                    modifier = Modifier.size(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Laporkan Kendala",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = namaPengguna,
                    onValueChange = { namaPengguna = it },
                    label = { Text("Nama Pengguna") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Orange500,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = alamatEmail,
                    onValueChange = {
                        alamatEmail = it
                        isEmailValid = it.contains("@")
                    },
                    label = { Text("Alamat Email") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    singleLine = true,
                    isError = !isEmailValid,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Orange500,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Red
                    )
                )

                if (!isEmailValid) {
                    Text(
                        text = "Email tidak valid",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }



                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = nomorPonsel,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) {
                            nomorPonsel = it
                        }
                    },
                    label = { Text("Nomor Ponsel") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Orange500,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )


                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = kendala,
                    onValueChange = { kendala = it },
                    label = { Text("Kendala") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Orange500,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
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
                    Text(text = "Kembali", color = Color.White)
                }

                Button(
                    onClick = { /* Handle confirmation */ },
                    colors = ButtonDefaults.buttonColors(Orange600),
                    shape = RoundedCornerShape(40.dp),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text(text = "Kirim", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
