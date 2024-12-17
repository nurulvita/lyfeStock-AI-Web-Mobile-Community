package com.example.applyfestox.uii.screen.profil

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.applyfestox.R
import com.example.applyfestox.data.sharedpreferences.LoginSessionHelper
import com.example.applyfestox.data.sqlite.DatabaseHelper
import com.example.applyfestox.data.sqlite.User
import kotlinx.coroutines.launch
import theme.Orange500
import theme.Orange600
import theme.Yellow50
import java.util.Calendar

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun UserInfoScreen(
    navController: NavHostController,
    databaseHelper: DatabaseHelper,
    loginSessionHelper: LoginSessionHelper
) {
    val currentUser = loginSessionHelper.getLoggedInUser()
    if (currentUser == null) {
        Text("User not logged in")
        return
    }
    val context = LocalContext.current

    // Data awal dari sesi login atau database
    var namaPengguna by remember { mutableStateOf(currentUser.name) }
    var alamatSurel by remember { mutableStateOf(currentUser.email) }
    var tanggalLahir by remember {
        mutableStateOf(databaseHelper.getUserData(currentUser.email)?.birthDate ?: "")
    }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    var emailError by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    // Ambil data terbaru saat halaman dimuat
    LaunchedEffect(Unit) {
        val updatedUser = databaseHelper.getUserData(currentUser.email)
        if (updatedUser != null) {
            // Simpan kembali ke sesi login jika ada perubahan
            loginSessionHelper.saveLoginSession(updatedUser)
            profileImageUri = updatedUser.photo?.let { Uri.parse(it) }
        } else {
            profileImageUri = currentUser.photo?.let { Uri.parse(it) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow50)
            .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                focusManager.clearFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(
            userName = namaPengguna,
            navController = navController,
            profileImageUri = profileImageUri,
            onImagePicked = { uri ->
                if (uri != null) {
                    // Simpan gambar ke penyimpanan internal
                    val bitmap = uriToBitmap(context, uri)
                    val imagePath = databaseHelper.savePhotoToInternalStorage(context, "profile_${currentUser.id}", bitmap)

                    if (imagePath != null) {
                        profileImageUri = Uri.parse(imagePath)

                        // Update foto di database
                        databaseHelper.updateUserPhoto(currentUser.id, imagePath)

                        // Simpan foto baru ke sesi login
                        loginSessionHelper.saveLoginSession(
                            User(
                                id = currentUser.id,
                                email = currentUser.email,
                                name = namaPengguna,
                                birthDate = tanggalLahir,
                                photo = imagePath
                            )
                        )
                    }
                }
            }

        )
        Spacer(modifier = Modifier.height(50.dp))

        UserInfoField(label = "Nama Pengguna", value = namaPengguna) { namaPengguna = it }
        Spacer(modifier = Modifier.height(8.dp))

        UserInfoField(label = "Alamat Surel", value = alamatSurel, onValueChange = { newEmail ->
            alamatSurel = newEmail
            emailError = ""
        })

        if (emailError.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(Color.Red.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = emailError,
                    color = Color.Red,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        DatePickerField(label = "Tanggal Lahir", value = tanggalLahir) { tanggalLahir = it }
        Spacer(modifier = Modifier.height(20.dp))

        UpdateButton(
            onUpdate = {
                val existingUser = databaseHelper.getUserData(alamatSurel)
                if (existingUser != null && existingUser.email != currentUser.email) {
                    emailError = "Email sudah terdaftar. Silakan gunakan alamat email yang lain."
                } else {
                    if (databaseHelper.updateUser(
                            userId = currentUser.id,
                            email = alamatSurel,
                            name = namaPengguna,
                            birthDate = tanggalLahir,
                            photo = profileImageUri?.toString()
                        )
                    ) {
                        loginSessionHelper.saveLoginSession(
                            User(
                                id = currentUser.id,
                                email = alamatSurel,
                                name = namaPengguna,
                                birthDate = tanggalLahir,
                                photo = profileImageUri?.toString()
                            )
                        )
                    }
                }
            }
        )
    }
}

fun uriToBitmap(context: Context, uri: Uri): Bitmap {
    return context.contentResolver.openInputStream(uri)?.use { inputStream ->
        BitmapFactory.decodeStream(inputStream)
    } ?: throw IllegalArgumentException("Cannot decode bitmap from URI")
}


@Composable
fun TopBar(
    userName: String,
    navController: NavHostController,
    profileImageUri: Uri?,
    onImagePicked: (Uri?) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            onImagePicked(uri)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(
                color = Orange600,
                shape = RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp)
            )
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = if (profileImageUri != null) {
                        rememberAsyncImagePainter(profileImageUri) // Ambil dari path
                    } else {
                        painterResource(id = R.drawable.avatars) // Default gambar
                    },
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(Color.White, shape = CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile",
                    tint = Orange600,
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.White, shape = CircleShape)
                        .clip(CircleShape)
                        .padding(4.dp)
                        .align(Alignment.BottomEnd)
                )
            }
        }
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(label: String, value: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { onDateSelected(it) },
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Orange500,
                unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.calender),
                        contentDescription = "Calendar Icon",
                        tint = Orange600
                    )
                }
            }
        )
    }
}

@Composable
fun UpdateButton(onUpdate: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                onUpdate()
                scope.launch {
                    snackbarHostState.showSnackbar("Data berhasil diperbarui")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                Orange600
            ),
            shape = RoundedCornerShape(40.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.pena),
                    contentDescription = "Pena Icon",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ubah Data",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
            snackbar = { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = Orange600,
                    contentColor = Color.White
                )
            }
        )
    }
}



