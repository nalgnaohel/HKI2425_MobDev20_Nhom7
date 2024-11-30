package com.example.harmonyhub.ui.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.harmonyhub.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.components.SongCard
import com.example.harmonyhub.ui.theme.NotoSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistScreen(
    myArtist: String?,
    onSongClick: () -> Unit,
    onBackButtonClicked: () -> Unit,
    onAddToPlaylistClicked: () -> Unit,
    onAddToFavoriteClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onDownloadClicked: () -> Unit
) {
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var selectedSong by remember { mutableStateOf<Song?>(null) }

    val songs = SongRepository.allSongs.filter { it.artist == myArtist }
    val artistName = songs.firstOrNull()?.artist ?: "Unknown Artist"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.v),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )

            // Gradient overlay to darken the image and improve text visibility
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
                    .testTag("Song Name")
            ) {
                Text(
                    text = artistName,
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
                IconButton(onClick = { /* Share Action */ }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color.White,
                        modifier = Modifier.size(25.dp)
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
                    text = "Popular releases",
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
                onAddToPlaylistClicked = onAddToPlaylistClicked,
                onAddToFavoriteClicked = onAddToFavoriteClicked,
                onShareClicked = onShareClicked,
                onDownloadClicked = onDownloadClicked
            )
        }
    }
}


@Composable
private fun BottomSheetContent(
    onDismiss: () -> Unit,
    selectedSong: Song?,
    onAddToPlaylistClicked: () -> Unit = {},
    onAddToFavoriteClicked: () -> Unit = {},
    onShareClicked: () -> Unit = {},
    onDownloadClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        selectedSong?.let { song ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(song.imageResId),
                    contentDescription = "Song Image",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = song.name,
                        fontFamily = NotoSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 1
                    )
                    Text(
                        text = song.artist,
                        fontFamily = NotoSans,
                        fontSize = 16.sp,
                        color = Color.Gray,
                        maxLines = 1
                    )
                }
            }
        }
        HorizontalDivider(color = Color.DarkGray, thickness = 0.3.dp)
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onAddToPlaylistClicked() }
        ) {
            Icon(
                painter = painterResource(R.drawable.add_48),
                contentDescription = "Add",
                tint = Color.Gray,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "Thêm vào danh sách phát", modifier = Modifier.padding(vertical = 8.dp),
                fontFamily = NotoSans, fontSize = 16.sp
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onAddToFavoriteClicked() }
        ) {
            Icon(
                painter = painterResource(R.drawable.favorite),
                contentDescription = "Favorite",
                tint = Color.LightGray,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "Thêm vào yêu thích", modifier = Modifier.padding(vertical = 8.dp),
                fontFamily = NotoSans, fontSize = 16.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDownloadClicked() }
        ) {
            Icon(
                painter = painterResource(R.drawable.download_for_offline),
                contentDescription = "Download",
                tint = Color.LightGray,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "Tải về", modifier = Modifier.padding(vertical = 8.dp),
                fontFamily = NotoSans, fontSize = 16.sp
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onShareClicked() }
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = Color.Gray,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "Chia sẻ", modifier = Modifier.padding(vertical = 8.dp),
                fontFamily = NotoSans, fontSize = 16.sp
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ArtistScreenPreview() {
    ArtistScreen(
        myArtist = "Jack - J97",
        onSongClick = {},
        onBackButtonClicked = {},
        onAddToPlaylistClicked = {},
        onAddToFavoriteClicked = {},
        onShareClicked = {},
        onDownloadClicked = {}
    )
}

