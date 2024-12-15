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
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.data.network.AlbumOut
import com.example.harmonyhub.presentation.viewmodel.FavoriteSongsViewModel
import com.example.harmonyhub.ui.components.BottomSheetContent
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
    onDownloadClicked: () -> Unit,
    onAlbumClicked:() -> Unit
) {
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var selectedSong by remember { mutableStateOf<Song?>(null) }

    val songs = SongRepository.allSongs.filter { it.artist == myArtist }
    val artistName = songs.firstOrNull()?.artist ?: "Kendrick Lamar"
    val songs2 : MutableList<Song> = mutableListOf(
        Song("1", "wacced out murals", "Kendrick Lamar", "https://i.scdn.co/image/ab67616d00001e02d9985092cd88bffd97653b58","https://p.scdn.co/mp3-preview/7cb4f6fbd7ec470550d430dd3b057d8b8f110218?cid=d8a5ed958d274c2e8ee717e6a4b0971d"),
        Song("2", "luther (with sza)", "Kendrick Lamar", "https://i.scdn.co/image/ab67616d00001e02d9985092cd88bffd97653b58","https://p.scdn.co/mp3-preview/c4ad75d1d4e58be0ecb8fd704c2b1421512eef67?cid=d8a5ed958d274c2e8ee717e6a4b0971d"),
        Song("3", "man at the garden", "Kendrick Lamar", "https://i.scdn.co/image/ab67616d00001e02d9985092cd88bffd97653b58","https://p.scdn.co/mp3-preview/ccfc8e9ed715423ad9e1cc869a392c4f24ef968b?cid=d8a5ed958d274c2e8ee717e6a4b0971d"),
    )
    // Thêm danh sách album giả lập
    val albums = listOf(
        "The Best Hits" to R.drawable.v,
        "Acoustic Vibes" to R.drawable.v,
        "Live Performances" to R.drawable.v,
        "New Era" to R.drawable.v
    )
    val albums2 : MutableList<AlbumOut> = mutableListOf(
        AlbumOut("1","GNX","https://i.scdn.co/image/ab67616d00001e02d9985092cd88bffd97653b58",
            listOf()),
        AlbumOut("2","The Big Steppers","https://i.scdn.co/image/ab67616d00001e022e02117d76426a08ac7c174f",
            listOf()),
        AlbumOut("3","DAMN. COLLECTORS EDITION.","https://i.scdn.co/image/ab67616d00001e022fad9364b7dfca0c2ede0da4",
            listOf()),
        AlbumOut("2","To Pimp A Butterfly","https://i.scdn.co/image/ab67616d00001e02cdb645498cd3d8a2db4d05e1",
            listOf()),
        AlbumOut("2","Section.80","https://i.scdn.co/image/ab67616d00001e02eddb2639b74ac6c202032ebe",
            listOf()),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data("https://i.scdn.co/image/ab6761610000e5eb39ba6dcd4355c03de0b50918")
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

        if (songs2.isNotEmpty()) {
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
                    items(songs2) { song ->
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
                fontFamily = NotoSans,
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Hiển thị danh sách album dưới dạng LazyRow
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Albums",
                color = Color.White,
                fontFamily = NotoSans,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(albums2) { album ->
                    Card(
                        modifier = Modifier
                            .size(120.dp) // Kích thước hình vuông
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.DarkGray)
                            .clickable { onAlbumClicked() },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context = LocalContext.current)
                                    .data(album.image)
                                    .crossfade(true)
                                    .build(),
                                error = painterResource(com.example.harmonyhub.R.drawable.ic_broken_image),
                                placeholder = painterResource(id = com.example.harmonyhub.R.drawable.loading_img),
                                contentDescription = "Photo",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp))
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.3f))
                            )

                            Text(
                                text = album.name,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
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
                screenType = "ArtistScreen",
                onAddToPlaylistClicked = onAddToPlaylistClicked,
                onAddToFavoriteClicked = onAddToFavoriteClicked,
                onShareClicked = onShareClicked,
                onDownloadClicked = onDownloadClicked,
                onDeleteClicked = {},
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
        onDownloadClicked = {},
        onAlbumClicked = {}
    )
}

