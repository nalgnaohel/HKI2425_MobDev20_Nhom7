package com.example.harmonyhub.ui.library

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.ui.components.Song

@Composable
fun DownloadScreen(onBackButtonClicked: () -> Unit)
{
    val allSongs: List<Song> = SongRepository.allSongs

    SongList(
        title = "Tải xuống",
        more = Icons.Default.MoreVert,
        songs = allSongs,
        onBackButtonClicked = onBackButtonClicked,
    )
}