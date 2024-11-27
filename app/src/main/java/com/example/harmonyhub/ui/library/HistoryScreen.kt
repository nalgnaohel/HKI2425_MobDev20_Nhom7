package com.example.harmonyhub.ui.library

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.ui.components.Song

@Composable
fun HistoryScreen(
    onBackButtonClicked: () -> Unit,
) {
    val allSongs: List<Song> = SongRepository.allSongs

    SongList(
        title = "Lịch sử phát",
        more = Icons.Default.MoreVert,
        songs = allSongs,
        onBackButtonClicked = onBackButtonClicked,
    )
}