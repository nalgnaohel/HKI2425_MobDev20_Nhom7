package com.example.harmonyhub.ui.profile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.harmonyhub.presentation.viewmodel.FavoriteSongsViewModel
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.library.SongList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun FavoriteFriendScreen (
    title: String,
    onBackButtonClicked: () -> Unit,
    favoriteSongsViewModel: FavoriteSongsViewModel = hiltViewModel()
) {
    val allSongs = remember {mutableListOf<Song>()}

    SongList(
        title = title,
        more = Icons.Default.MoreVert,
        songs = allSongs,
        onBackButtonClicked = onBackButtonClicked,
        screenType = "FavoriteFriendScreen",
        onAddToPlaylistClicked = {},
        onAddToFavoriteClicked = {},
        onDeleteClicked = {},
        onShareClicked = {},
        onDownloadClicked = {},
        onDeleteAllClicked = {}
    )
}