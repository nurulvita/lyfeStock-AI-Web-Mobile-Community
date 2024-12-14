package com.example.applyfestox.screen.manajemen.Sapronak.sapronakComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SapronakButtonBar(
    buttons: List<String> = listOf("Pakan", "DOC", "OVK"),
    activeButton: String? = null,
    onButtonClick: ((String) -> Unit)? = null
) {
    val (selectedButton, setSelectedButton) = remember {
        mutableStateOf(activeButton ?: buttons.first())
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        buttons.forEach { button ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(
                        if (button == selectedButton) Color(0xFFFFC107) else Color.Gray,
                        shape = MaterialTheme.shapes.small
                    )
                    .clickable {
                        setSelectedButton(button)
                        onButtonClick?.invoke(button)
                    }
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = button,
                    color = if (button == selectedButton) Color.Black else Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SapronakButtonBarPreview() {
    SapronakButtonBar()
}
