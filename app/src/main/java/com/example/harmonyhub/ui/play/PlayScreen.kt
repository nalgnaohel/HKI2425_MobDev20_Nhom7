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
import com.example.harmonyhub.R
import com.example.harmonyhub.SongRepository
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.theme.NotoSans

@Composable
fun RoundedImageCard(
    imageResId: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Gray)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Song Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}




@Composable
fun PlayScreen(
    onBackButtonClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    val songId = "1"
    val song = SongRepository.allSongs.find { it.id == songId }

    var isPlaying by remember { mutableStateOf(false) }
    var currentSongIndex by remember { mutableStateOf(0) }

    // Playlist
    val playlist = listOf(
        Pair("Song Title 1", "https://aac.saavncdn.com/948/d52ab14bf31ac4ed166bcd03dacee9e1_96.mp4"),
        Pair("Song Title 2", "https://aac.saavncdn.com/651/b69440a1f0441e569bbf4782299852b2_96.mp4"),
        Pair("Song Title 3", "https://aac.saavncdn.com/386/c69effef37d7f3d7b27ff2cef78c2598_96.mp4")
    )

    // Load song
    fun loadSong(index: Int) {
        exoPlayer.setMediaItem(MediaItem.fromUri(playlist[index].second))
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

    if (song != null) {
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
                    onClick = { /* More options */ },
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

            RoundedImageCard(
                imageResId = song.imageResId,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
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
                        text = song.name,
                        style = TextStyle(
                            fontFamily = NotoSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color.White
                        )
                    )
                    Text(
                        text = song.artist,
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
                    value = 0.3f,
                    onValueChange = {},
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
                    Text(text = "0:25", fontSize = 12.sp, color = Color.White)
                    Text(text = "3:15", fontSize = 12.sp, color = Color.White)
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
