package com.example.harmonyhub.ui.library

import androidx.compose.runtime.Composable
import com.example.harmonyhub.R
import com.example.harmonyhub.SongRepository
import com.example.harmonyhub.ui.components.Song

@Composable
fun DownloadScreen(onBackButtonClicked: () -> Unit)
{
    val allSongs: List<Song> = SongRepository.allSongs

    SongList(
        title = "Tải xuống",
        songs = allSongs,
        onBackButtonClicked = onBackButtonClicked,
    )
}