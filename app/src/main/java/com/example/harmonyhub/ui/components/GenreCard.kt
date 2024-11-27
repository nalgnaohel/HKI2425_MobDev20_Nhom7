package com.example.harmonyhub.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harmonyhub.ui.theme.NotoSans

private val gradientBackground = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF00FAF2),
        Color(0xFF1E3264)
    )
)

@Composable
fun GenreCard(genre: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier
            .size(width = 150.dp, height = 150.dp)
            .padding(4.dp)
            .clickable {  },
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(gradientBackground),
            contentAlignment = Alignment.Center) {
            Text(
                text = genre,
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            )
        }
    }
}