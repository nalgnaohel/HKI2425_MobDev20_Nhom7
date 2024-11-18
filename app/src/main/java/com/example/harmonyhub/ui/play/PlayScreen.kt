package com.example.harmonyhub.ui.play

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.harmonyhub.R


@Composable
fun RoundedImageCard(
    imageResId: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp)) // Bo tròn 4 góc
            .background(Color.Gray) // Thêm màu nền cho dễ kiểm soát
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Song Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Đảm bảo hình ảnh phủ toàn bộ khung
        )
    }
}


@Composable
fun PlayScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }

    var isPlaying by remember { mutableStateOf(false) }
    var currentSongIndex by remember { mutableStateOf(0) }

    // Danh sách bài hát
    val playlist = listOf(
        Pair("Song Title 1", "https://aac.saavncdn.com/948/d52ab14bf31ac4ed166bcd03dacee9e1_96.mp4"),
        Pair("Song Title 2", "https://aac.saavncdn.com/651/b69440a1f0441e569bbf4782299852b2_96.mp4"),
        Pair("Song Title 3", "https://aac.saavncdn.com/386/c69effef37d7f3d7b27ff2cef78c2598_96.mp4")
    )

    // Tải bài hát hiện tại
    fun loadSong(index: Int) {
        exoPlayer.setMediaItem(MediaItem.fromUri(playlist[index].second))
        exoPlayer.prepare()
    }

    // Phát hoặc dừng nhạc
    fun togglePlayPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
            isPlaying = false
        } else {
            exoPlayer.play()
            isPlaying = true
        }
    }

    // Chuyển bài
    fun nextSong() {
        currentSongIndex = (currentSongIndex + 1) % playlist.size
        loadSong(currentSongIndex)
        exoPlayer.play()
        isPlaying = true
    }

    fun previousSong() {
        currentSongIndex = if (currentSongIndex - 1 < 0) playlist.size - 1 else currentSongIndex - 1
        loadSong(currentSongIndex)
        exoPlayer.play()
        isPlaying = true
    }

    // Khởi tạo bài hát đầu tiên
    LaunchedEffect(Unit) {
        loadSong(currentSongIndex)
    }

//giao dien
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        item {
            // Header with dropdown and options
            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "arrowDropDown",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "PLAYING NOW",
                    maxLines = 1,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "moreVert",
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.size(30.dp))

            // Song card with image
            SongCard()

            Spacer(modifier = Modifier.size(16.dp))

            // Song details and controls
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Song Title", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(text = "Artist Name", fontSize = 16.sp, color = Color.Gray)

                Spacer(modifier = Modifier.size(20.dp))

                // Progress bar and timestamp
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Slider(
                        value = 0.3f, // Giá trị giả định cho thanh tiến trình
                        onValueChange = {},
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "1:15")
                        Text(text = "3:45")
                    }
                }

                Spacer(modifier = Modifier.size(20.dp))

                // Playback control buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { previousSong() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Previous",
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    IconButton(onClick = {
                        togglePlayPause()
                    }) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Filled.Lock else Icons.Filled.PlayArrow,
                            contentDescription = "Play/Pause",
                            modifier = Modifier
                                .size(64.dp)
                                .padding(8.dp)
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    IconButton(onClick = { nextSong() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release() // Giải phóng tài nguyên khi giao diện bị hủy
        }
    }
}

@Composable
fun SongCard(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier
            .size(400.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.v),
                contentDescription = "songImage",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayScreenPreview() {
    PlayScreen()
}
