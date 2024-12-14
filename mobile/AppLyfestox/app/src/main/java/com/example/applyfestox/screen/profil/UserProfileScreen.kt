package com.example.applyfestox.screen.profil

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.applyfestox.R
import com.example.applyfestox.ui.theme.Orange500
import com.example.applyfestox.ui.theme.Orange600
import com.example.applyfestox.ui.theme.Yellow50
import java.util.Calendar

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun UserInfoScreen(navController: NavHostController) {

    var namaPengguna by remember { mutableStateOf("nurul") }
    var alamatSurel by remember { mutableStateOf("nurul@gmail.com") }
    var tanggalLahir by remember { mutableStateOf("31/01/2002") }

    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow50)
            .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                focusManager.clearFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(navController = navController)
        Spacer(modifier = Modifier.height(50.dp))

        UserInfoField(label = "Nama Pengguna", value = namaPengguna) { namaPengguna = it }
        Spacer(modifier = Modifier.height(8.dp))
        UserInfoField(label = "Alamat Surel", value = alamatSurel) { alamatSurel = it }
        Spacer(modifier = Modifier.height(8.dp))
        DatePickerField(label = "Tanggal Lahir", value = tanggalLahir) { tanggalLahir = it }
        Spacer(modifier = Modifier.height(20.dp))

        UpdateButton()
    }
}

@Composable
fun TopBar(navController: NavHostController) {
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
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
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier
                    .size(90.dp)
            ) {

                Image(
                    painter = if (profileImageUri != null) {
                        rememberAsyncImagePainter(profileImageUri)
                    } else {
                        painterResource(id = R.drawable.avatars)
                    },
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(Color.White, shape = CircleShape)
                        .clickable { launcher.launch("image/*") }
                        .border(2.dp, Color.White, CircleShape)
                )

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile",
                    tint = Orange600,
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.White, shape = CircleShape)
                        .padding(4.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = 4.dp, y = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Nurul",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
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
fun UpdateButton() {
    Button(
        onClick = { /* Handle update data action */ },
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
}
