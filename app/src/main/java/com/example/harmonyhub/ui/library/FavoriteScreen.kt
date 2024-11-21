package com.example.harmonyhub.ui.library

import androidx.compose.runtime.Composable
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.ui.components.Song

@Composable
fun FavoriteScreen(
    onBackButtonClicked: () -> Unit,
) {
    val allSongs: List<Song> = SongRepository.allSongs

    SongList(
        title = "Yêu thích",
        songs = allSongs,
        onBackButtonClicked = onBackButtonClicked,
    )
}