package com.example.harmonyhub.ui.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.harmonyhub.R
import com.example.harmonyhub.ui.components.BottomSheetContent
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.components.SongCard
import com.example.harmonyhub.ui.theme.NotoSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumScreen(
    idAlbum: String?,
    onSongClick: () -> Unit,
    onBackButtonClicked: () -> Unit,
    onAddToPlaylistClicked: () -> Unit,
    onAddToFavoriteClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onDownloadClicked: () -> Unit
) {
    // Dữ liệu giả lập
    val albumName = "CHROMAKOPIA"
    val songs = listOf(
        Song("1", "St. Chroma (feat. Daniel Caesar)", "Tyler, The Creator, Daniel Caesar", "https://i.scdn.co/image/ab67616d0000b273124e9249fada4ff3c3a0739c", "https://example.com/song_a.jpg"),
        Song("2", "Rah Tah Tah", "Tyler, The Creator", "https://i.scdn.co/image/ab67616d0000b273124e9249fada4ff3c3a0739c", "https://example.com/song_b.jpg"),
        Song("2", "Darling, I (feat. Teezo Touchdown)", "Tyler, The Creator, Teezo Touchdown", "https://i.scdn.co/image/ab67616d0000b273124e9249fada4ff3c3a0739c", "https://example.com/song_b.jpg"),
        Song("2", "Hey Jane", "Tyler, The Creator", "https://i.scdn.co/image/ab67616d0000b273124e9249fada4ff3c3a0739c", "https://example.com/song_b.jpg"),
        Song("2", "I Killed You", "Tyler, The Creator", "https://i.scdn.co/image/ab67616d0000b273124e9249fada4ff3c3a0739c", "https://example.com/song_b.jpg"),
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
                    .data("https://i.scdn.co/image/ab67616d0000b273124e9249fada4ff3c3a0739c")
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
                onDeleteClicked = {},
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AlbumScreenPreview() {
//    AlbumScreen(
//        onSongClick = {},
//        onBackButtonClicked = {},
//        onAddToPlaylistClicked = {},
//        onAddToFavoriteClicked = {},
//        onShareClicked = {},
//        onDownloadClicked = {}
//    )
//}
