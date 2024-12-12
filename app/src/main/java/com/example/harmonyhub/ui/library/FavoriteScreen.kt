package com.example.harmonyhub.ui.library

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.harmonyhub.presentation.viewmodel.FavoriteSongFetchingState
import com.example.harmonyhub.presentation.viewmodel.FavoriteSongsViewModel
import com.example.harmonyhub.ui.components.Song
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun FavoriteScreen(
    onBackButtonClicked: () -> Unit,
    onAddToPlaylistClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onDownloadClicked: () -> Unit,
    favoriteSongsViewModel: FavoriteSongsViewModel = hiltViewModel()
) {

    val dataFetchingState = favoriteSongsViewModel.dataFetchingState.observeAsState()
    val context = LocalContext.current

    val allSongs = remember {mutableListOf<Song>()}

    LaunchedEffect(Unit) {
        favoriteSongsViewModel.getFavoriteSongs()
    }

    LaunchedEffect(dataFetchingState.value) {
        when (dataFetchingState.value) {
            is FavoriteSongFetchingState.Success -> {

                when (val data = (dataFetchingState.value as FavoriteSongFetchingState.Success).data) {
                    is List<*> -> {
                        val songs = data as List<Song>
                        allSongs.clear()
                        allSongs.addAll(songs)
                        favoriteSongsViewModel.resetDataFetchingState()
                    }
                    is String -> {
                        val message = data as String
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        favoriteSongsViewModel.resetDataFetchingState()
                        favoriteSongsViewModel.getFavoriteSongs()
                    }
                }
            }

            is FavoriteSongFetchingState.Error -> {
                val message = (dataFetchingState.value as FavoriteSongFetchingState.Error).message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                favoriteSongsViewModel.resetDataFetchingState()
            }

            else -> {}
        }
    }

    SongList(
        title = "Yêu thích",
        more = Icons.Default.MoreVert,
        songs = allSongs,
        onBackButtonClicked = onBackButtonClicked,
        screenType = "FavoriteScreen",
        onAddToPlaylistClicked = onAddToPlaylistClicked,
        onAddToFavoriteClicked = {},
        onDeleteClicked = onDeleteClicked,
        onShareClicked = onShareClicked,
        onDownloadClicked = onDownloadClicked,
        onDeleteAllClicked = {
            runBlocking {
                launch {
                    allSongs.forEach {
                        favoriteSongsViewModel.removeFavoriteSong(it)
                    }
                    favoriteSongsViewModel.getFavoriteSongs()
                }
            }
        }
    )
}