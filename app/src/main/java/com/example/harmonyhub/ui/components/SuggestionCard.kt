package com.example.harmonyhub.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harmonyhub.R
import com.example.harmonyhub.ui.theme.NotoSans

@Composable
fun SuggestionCard(songName: String, artistName: String) {
    Surface(
        modifier = Modifier
            .size(width = 125.dp, height = 180.dp)
            .clickable {  },
        color = Color.Transparent
    ) {
        Column(modifier = Modifier.padding(4.dp))
        {
            Box(
                modifier = Modifier.size(width = 125.dp, height = 125.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.v),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)),
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = songName,
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
                maxLines = 1,
                overflow = Ellipsis
            )
            Text(
                text = artistName,
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontSize = 14.sp
                ),
                color = Color.Gray,
                maxLines = 1, overflow = Ellipsis
            )
        }
    }
}