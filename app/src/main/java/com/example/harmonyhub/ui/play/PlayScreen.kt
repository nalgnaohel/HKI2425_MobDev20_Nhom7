package com.example.harmonyhub.ui.play

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.harmonyhub.R
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.theme.NotoSans
import kotlinx.coroutines.delay


@Composable
fun PlayScreen(
    index : Int?,
    onBackButtonClicked: () -> Unit,
    onMoreClicked: () -> Unit
) {
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    var playlist by remember { mutableStateOf(SongRepository.currentPLaylist ) }
    var currentSongIndex by remember { mutableIntStateOf(index ?: 0) }
    var isPlaying by remember { mutableStateOf(false) }
    var currentPlayTime by remember { mutableStateOf(0L) }
    var duration by remember { mutableStateOf(0L) }

    if (playlist.size == 0) {
        DisposableEffect(Unit) {
            onDispose {
                exoPlayer.release()
            }
        }
        onBackButtonClicked
    }


    // Load song
    fun loadSong(index: Int) {
        exoPlayer.setMediaItem(MediaItem.fromUri(playlist[index].url))
        exoPlayer.prepare()
    }

    // Play/Pause toggle
    fun togglePlayPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
            isPlaying = false
        } else {
            exoPlayer.play()
            isPlaying = true
        }
    }

    // Next song
    fun nextSong() {
        currentSongIndex = (currentSongIndex + 1) % playlist.size
        loadSong(currentSongIndex)
        exoPlayer.play()
        isPlaying = true
    }

    // Previous song
    fun previousSong() {
        currentSongIndex = if (currentSongIndex - 1 < 0) playlist.size - 1 else currentSongIndex - 1
        loadSong(currentSongIndex)
        exoPlayer.play()
        isPlaying = true
    }

    // Initialize the first song
    LaunchedEffect(Unit) {
        loadSong(currentSongIndex)
    }

    //Update current time
    LaunchedEffect(exoPlayer) {
        while (true) {
            currentPlayTime = exoPlayer.currentPosition
            duration = 195000
            delay(1000)
        }
    }

    if (playlist[currentSongIndex] != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onBackButtonClicked() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icons8_arrow_down_90),
                        contentDescription = "Back",
                        modifier = Modifier.size(24.dp)
                    )
                }

                Button(
                    onClick = { onMoreClicked() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icons8_more_90),
                        contentDescription = "More options",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(playlist[currentSongIndex].imageResId)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = "Photo",

                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(RoundedCornerShape(12.dp)),
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = playlist[currentSongIndex].name,
                        style = TextStyle(
                            fontFamily = NotoSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color.White
                        )
                    )
                    Text(
                        text = playlist[currentSongIndex].artist,
                        style = TextStyle(
                            fontFamily = NotoSans,
                            fontSize = 18.sp,
                            color = Color.Gray
                        )
                    )
                }

                Button(
                    onClick = { /* Add favorite action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icons8_heart_90),
                        contentDescription = "Favorite",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Slider(
                    value = if (duration == 0L) 0f else currentPlayTime.toFloat() / duration.toFloat(),
                    onValueChange = {
                        exoPlayer.seekTo((it * duration).toLong())
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color.White,
                        inactiveTrackColor = Color.Gray
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = formatTime(currentPlayTime), fontSize = 12.sp, color = Color.White)
                    Text(text = formatTime(duration), fontSize = 12.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Previous button
                Button(
                    onClick = { previousSong() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icons8_skip_to_start_90
                        ),
                        contentDescription = "Previous",
                        modifier = Modifier.size(48.dp)
                    )
                }

                // Play/Pause button
                Button(
                    onClick = { togglePlayPause() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = if (isPlaying) R.drawable.icons8_pause_50 else R.drawable.icons8_circled_play_64),
                        contentDescription = "Play/Pause",
                        modifier = Modifier.size(64.dp)
                    )
                }

                // Next button
                Button(
                    onClick = { nextSong() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icons8_next_90),
                        contentDescription = "Next",
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Không tìm thấy bài hát",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release() // Giải phóng tài nguyên
        }
    }
}

//Time format utils
fun formatTime(timeInMs: Long) : String {
    val seconds = timeInMs / 1000
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "${if (minutes < 10) "0$minutes" else minutes }:${if (remainingSeconds < 10) "0$remainingSeconds" else remainingSeconds}"
}
