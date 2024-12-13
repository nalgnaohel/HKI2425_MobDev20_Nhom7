package com.example.harmonyhub.ui.profile

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.harmonyhub.presentation.viewmodel.FavoriteSongsViewModel
import com.example.harmonyhub.presentation.viewmodel.FriendListFetchingState
import com.example.harmonyhub.presentation.viewmodel.FriendListViewModel
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.library.SongList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun FavoriteFriendScreen (
    title: String,
    onBackButtonClicked: () -> Unit,
    uid: String? = "BXbBfmXQcneF5aqaXnYjiXc9pZ12",
    friendSongsViewModel: FriendListViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val allSongs = remember {mutableListOf<Song>()}

    val friendSongsFetchingState = friendSongsViewModel.dataFetchingState.observeAsState()

    LaunchedEffect(Unit) {
        friendSongsViewModel.getFriendSongs(uid.toString())
    }

    LaunchedEffect(friendSongsFetchingState.value) {
        when (friendSongsFetchingState.value) {
            is FriendListFetchingState.Success -> {
                val songs = (friendSongsFetchingState.value as FriendListFetchingState.Success).data as List<Song>
                allSongs.clear()
                allSongs.addAll(songs)
            }
            is FriendListFetchingState.Error -> {
                val error = (friendSongsFetchingState.value as FriendListFetchingState.Error).message
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

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