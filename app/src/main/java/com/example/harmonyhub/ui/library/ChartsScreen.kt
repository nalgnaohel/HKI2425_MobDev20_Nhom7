package com.example.harmonyhub.ui.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.harmonyhub.R
import com.example.harmonyhub.ui.components.BottomSheetContent
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.components.SongCard
import com.example.harmonyhub.ui.theme.NotoSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartsScreen(
    idCharts: String?,
    onSongClick: () -> Unit,
    onBackButtonClicked: () -> Unit,
    onAddToPlaylistClicked: () -> Unit,
    onAddToFavoriteClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onDownloadClicked: () -> Unit
) {
    // Dữ liệu giả lập
    val albumName = "Top Songs - Global"
    val songs = listOf(
        Song("1", "Die With A Smile", "Lady Gaga, Bruno Mars", "https://i.scdn.co/image/ab67616d00001e0282ea2e9e1858aa012c57cd45", "https://example.com/song_a.jpg"),
        Song("2", "APT.", "ROSÉ, Bruno Mars", "https://i.scdn.co/image/ab67616d00001e025074bd0894cb1340b8d8a678", "https://example.com/song_b.jpg"),
        Song("3", "All I Want for Christmas Is You", "Mariah Carey", "https://i.scdn.co/image/ab67616d00001e024246e3158421f5abb75abc4f", "https://example.com/song_c.jpg"),
        Song("4", "Last Christmas", "Wham!", "https://i.scdn.co/image/ab67616d00001e02f2d2adaa21ad616df6241e7d", "https://example.com/song_c.jpg"),
        Song("5", "BIRDS OF A FEATHER", "Billie Eilish", "https://i.scdn.co/image/ab67616d00001e0271d62ea7ea8a5be92d3c1f62", "https://example.com/song_c.jpg"),
        Song("6", "Rockin' Around The Christmas Tree", "Brenda Lee", "https://i.scdn.co/image/ab67616d00001e027845f74d6db14b400fa61cd3", "https://example.com/song_c.jpg"),
        Song("7", "That’s So True", "Gracie Abrams", "https://i.scdn.co/image/ab67616d00001e021dac3694b3289cd903cb3acf", "https://example.com/song_c.jpg"),
        Song("3", "luther (with sza)", "Kendrick Lamar, SZA", "https://i.scdn.co/image/ab67616d00001e0209d6ed214f03fbb663e46531", "https://example.com/song_c.jpg"),
        Song("3", "Jingle Bell Rock", "Bobby Helms", "https://i.scdn.co/image/ab67616d00001e02fd56f3c7a294f5cfe51c7b17", "https://example.com/song_c.jpg"),
        Song("3", "Who", "Jimin", "https://i.scdn.co/image/ab67616d00001e02f02c451189a709b9a952aaec", "https://example.com/song_c.jpg")
    )
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var selectedSong by remember { mutableStateOf<Song?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data("https://charts-images.scdn.co/assets/locale_en/regional/weekly/region_global_default.jpg")
                    .crossfade(true)
                    .build(),
                error = painterResource(com.example.harmonyhub.R.drawable.ic_broken_image),
                placeholder = painterResource(id = com.example.harmonyhub.R.drawable.loading_img),
                contentDescription = "Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
            // Gradient overlay để tăng độ rõ của chữ
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.4f)
                    )
            )

            IconButton(
                onClick = onBackButtonClicked,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(25.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .testTag("Album Name")
            ) {
                Text(
                    text = albumName,
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .border(1.dp, Color.White, RoundedCornerShape(16.dp))
                    .padding(8.dp)
                    .clickable { }
            ) {
                Text(
                    text = "Follow",
                    color = Color.White,
                    fontFamily = NotoSans,
                    fontSize = 16.sp
                )
            }
            Row {
                IconButton(onClick = { /* Play Action */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icons8_circled_play_64),
                        contentDescription = "Play",
                        tint = Color(0xFF00FAF2),
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (songs.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .testTag("Song List")
            ) {
                Text(
                    text = "Popular tracks",
                    color = Color.White,
                    fontFamily = NotoSans,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(songs) { song ->
                        SongCard(
                            song = song,
                            more = Icons.Default.MoreVert,
                            onSongClick = onSongClick,
                            onMoreClick = {
                                selectedSong = song
                                isBottomSheetVisible = true
                            }
                        )
                    }
                }
            }
        } else {
            Text(
                text = "No songs available",
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { isBottomSheetVisible = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            BottomSheetContent(
                onDismiss = { isBottomSheetVisible = false },
                selectedSong = selectedSong,
                screenType = "AlbumScreen",
                onAddToPlaylistClicked = onAddToPlaylistClicked,
                onAddToFavoriteClicked = onAddToFavoriteClicked,
                onShareClicked = onShareClicked,
                onDownloadClicked = onDownloadClicked,
                onDeleteClicked = {},)
        }
    }
}