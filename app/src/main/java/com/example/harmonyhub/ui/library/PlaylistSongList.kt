package com.example.harmonyhub.ui.library

import androidx.compose.runtime.Composable
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.ui.components.Song

@Composable
fun PlaylistSongListScreen(
    playlistName: String,
    onBackButtonClicked: () -> Unit,
) {
    val allSongs: List<Song> = SongRepository.allSongs

    SongList(
        title = playlistName,
        songs = allSongs,
        onBackButtonClicked = onBackButtonClicked,
    )
}