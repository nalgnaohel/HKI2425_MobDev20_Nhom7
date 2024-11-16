package com.example.harmonyhub.ui.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harmonyhub.ui.theme.NotoSans

@Composable
fun ProfileScreen(
    onHomeButtonClicked: () -> Unit,
) {
    Text(
        text = "Profile t",
        style = TextStyle(
            fontFamily = NotoSans,
            fontSize = 100.sp
        ),
        modifier = Modifier.padding(16.dp)
    )
}