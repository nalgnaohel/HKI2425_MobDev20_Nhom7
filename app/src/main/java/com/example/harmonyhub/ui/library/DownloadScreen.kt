package com.example.harmonyhub.ui.library

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.presentation.viewmodel.FavoriteSongsViewModel
import com.example.harmonyhub.ui.components.Song

@Composable
fun DownloadScreen(
    onBackButtonClicked: () -> Unit,
    onAddToPlaylistClicked: () -> Unit,
    onAddToFavoriteClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onShareClicked: () -> Unit,
    favoriteSongsViewModel: FavoriteSongsViewModel = hiltViewModel()

) {
    val allSongs: List<Song> = SongRepository.allSongs

    SongList(
        title = "Tải xuống",
        more = Icons.Default.MoreVert,
        songs = allSongs,
        onBackButtonClicked = onBackButtonClicked,
        screenType = "DownloadScreen",
        onAddToPlaylistClicked = {},
        onAddToFavoriteClicked = {},
        onDeleteClicked = onDeleteClicked,
        onShareClicked = {},
        onDownloadClicked = {},
        favoriteSongsViewModel = null
        )
}