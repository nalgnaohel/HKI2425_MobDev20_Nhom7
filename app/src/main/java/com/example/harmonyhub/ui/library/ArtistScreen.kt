package com.example.harmonyhub.ui.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.harmonyhub.ui.components.Song

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.harmonyhub.data.SongRepository

@Composable
fun ArtistScreen(
    artist: String,
    onSongClick: (Song) -> Unit = {}
) {
    val songs = SongRepository.allSongs.filter { it.artist == artist }
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

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 0f
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = artistName,
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Artist",
                    color = Color.White,
                    fontSize = 16.sp
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
            Text(
                text = "${songs.size} songs",
                color = Color.White,
                fontSize = 18.sp
            )
            Row {
                IconButton(onClick = { /* Favourite Action */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icons8_heart_90),
                        contentDescription = "Share",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { /* Play Action */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icons8_circled_play_64),
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier.size(42.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (songs.isNotEmpty()) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Popular releases",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(songs) { song ->
                        PopularReleaseItem(song = song, onClick = { onSongClick(song) })
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
}

@Composable
fun PopularReleaseItem(song: Song, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = song.imageResId),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = song.name,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = song.artist,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
        IconButton(onClick = { /* Open song menu */ }) {
            Icon(
                painter = painterResource(id = R.drawable.icons8_more_90),
                contentDescription = "More options",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ArtistScreenPreview() {
    ArtistScreen(
        artist = "The Chainsmokers",
    )
}
