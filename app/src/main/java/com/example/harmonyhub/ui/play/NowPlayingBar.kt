package com.example.harmonyhub.ui.play


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.harmonyhub.R
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.theme.NotoSans

@Composable
fun NowPlayingBar(
    song: Song?,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onBarClick: () -> Unit
) {
    val gradientBackground = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF1D2671).copy(alpha = 0.7f),
            Color(0xFFC33764).copy(alpha = 0.7f)
        )
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(gradientBackground)
            .clickable { onBarClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            if (song != null) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(song.imageResId)
                        .crossfade(true)
                        .build(),
                    error = painterResource(com.example.harmonyhub.R.drawable.ic_broken_image),
                    placeholder = painterResource(id = com.example.harmonyhub.R.drawable.loading_img),
                    contentDescription = "Photo",
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(8.dp)),
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                if (song != null) {
                    Text(
                        text = song.name,
                        color = Color.White,
                        fontSize = 16.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        fontFamily = NotoSans
                    )
                }
                if (song != null) {
                    Text(
                        text = song.artist,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        maxLines = 1,
                        fontFamily = NotoSans
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(onClick = { onPreviousClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_skip_to_start_90),
                    contentDescription = "Previous",
                    tint = Color.White
                )
            }
            IconButton(onClick = { onPlayPauseClick() }) {
                Icon(
                    painter = painterResource(
                        id = if (isPlaying) R.drawable.icons8_pause_50 else R.drawable.icons8_circled_play_64
                    ),
                    contentDescription = "Play/Pause",
                    tint = Color.White,
                    modifier = Modifier.size(if (isPlaying) 38.dp else 32.dp)
                )
            }
            IconButton(onClick = { onNextClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_next_90),
                    contentDescription = "Next",
                    tint = Color.White
                )
            }
        }
    }
}

