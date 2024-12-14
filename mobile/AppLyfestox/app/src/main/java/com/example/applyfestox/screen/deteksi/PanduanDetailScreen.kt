
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.applyfestox.ui.theme.Yellow50

@Composable
fun GuideScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow50)
    ) {
        TopBar(navController = navController)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                GuideSection(
                    title = "Persiapan dan Isolasi Ayam",
                    steps = listOf(
                        Step("1", "Identifikasi Ayam yang Kurang Sehat", "Amati ayam di kandang untuk melihat tanda-tanda kesehatan yang kurang baik..."),
                        Step("2", "Pisahkan Ayam yang Terlihat Kurang Sehat", "Pisahkan ayam yang menunjukkan tanda-tanda kesehatan yang kurang baik untuk isolasi.")
                    )
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                GuideSection(
                    title = "Pengambilan Foto Feses",
                    steps = listOf(
                        Step("3", "Siapkan Perangkat dan Pencahayaan", "Siapkan perangkat dengan kamera yang baik dan pastikan pencahayaan cukup."),
                        Step("4", "Posisikan Kamera dengan Tepat", "Posisikan kamera sejajar dengan feses dan hindari bayangan.")
                    )
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                GuideSection(
                    title = "Unggah Foto dan Analisis Hasil",
                    steps = listOf(
                        Step("5", "Unggah Foto ke Aplikasi", "Pilih menu 'Ambil Foto' dan unggah foto untuk analisis.")
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "Panduan Teknis",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFFE2AC66))
    )
}


@Composable
fun GuideSection(title: String, steps: List<Step>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            steps.forEach { step ->
                StepItem(step = step)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun StepItem(step: Step) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(Color(0xFFE2AC66), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = step.number,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = step.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = step.description,
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Justify
            )
        }
    }
}

data class Step(
    val number: String,
    val title: String,
    val description: String
)

@Composable
fun PanduanDetailScreen(navController: NavController) {
    GuideScreen(navController = navController)
}
