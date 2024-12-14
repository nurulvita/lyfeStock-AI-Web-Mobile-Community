package com.example.applyfestox.screen.manajemen

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun AddScheduleBox(onSaveClick: (String, String) -> Unit, onCancelClick: () -> Unit) {
    val context = LocalContext.current
    val morningTime = remember { mutableStateOf("09:00") }
    val eveningTime = remember { mutableStateOf("16:00") }

    val openTimePicker: (String, (String) -> Unit) -> Unit = { title, onTimeSet ->
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                val time = String.format("%02d:%02d", hourOfDay, minute)
                onTimeSet(time)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).apply {
            setTitle(title)
        }.show()
    }

    // Kotak Pop-up
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f) // Lebar 90% layar
            .background(Color.White, shape = RoundedCornerShape(16.dp)) // Latar putih dan rounded corners
            .padding(16.dp), // Padding di dalam pop-up
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Judul di tengah kotak pop-up
        Text(
            text = "Jadwal Pakan",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Input Waktu Pagi
        CustomTimeInput(
            label = "Jadwal Pakan Pagi",
            time = morningTime.value,
            onClick = { openTimePicker("Pilih Waktu Pagi") { morningTime.value = it } }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Input Waktu Sore
        CustomTimeInput(
            label = "Jadwal Pakan Sore",
            time = eveningTime.value,
            onClick = { openTimePicker("Pilih Waktu Sore") { eveningTime.value = it } }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tombol Simpan
        Button(
            onClick = { onSaveClick(morningTime.value, eveningTime.value) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Simpan",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        // Tombol Batalkan
        Button(
            onClick = onCancelClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(
                text = "Batalkan",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Composable
fun CustomTimeInput(label: String, time: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = time,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
                .fillMaxWidth()
                .background(Color.Transparent)
                .clickable(onClick = onClick)
        )
    }
}


@Preview(showBackground = false)
@Composable
fun AddScheduleBoxPreview() {
    AddScheduleBox(
        onSaveClick = { morning, evening ->
            // Handle save click
        },
        onCancelClick =     {
            // Handle cancel click
        }
    )
}
