package com.example.harmonyhub.ui.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.harmonyhub.R
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.presentation.viewmodel.DataFetchingState
import com.example.harmonyhub.presentation.viewmodel.FavoriteSongFetchingState
import com.example.harmonyhub.presentation.viewmodel.FavoriteSongsViewModel
import com.example.harmonyhub.presentation.viewmodel.UserDataViewModel
import com.example.harmonyhub.ui.components.AppScaffoldWithDrawer
import com.example.harmonyhub.ui.components.BottomSheetContent
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.components.SongCard
import com.example.harmonyhub.ui.theme.NotoSans

private val gradientBackground = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF00FAF2),
        Color(0xFF1E3264)
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    onPlaySongClicked: () -> Unit,
    onProfileButtonClicked: () -> Unit,
    onViewAllRecentCLicked: () -> Unit,
    onFavoriteButtonClicked: () -> Unit,
    onDownloadButtonClicked: () -> Unit,
    onPlaylistButtonClicked: () -> Unit,
    onArtistsFollowingButtonClicked: () -> Unit,
    onLogoutButtonClicked: () -> Unit,
    onSettingsButtonClicked: () -> Unit,
    favoriteSongsViewModel: FavoriteSongsViewModel = hiltViewModel(),
    userViewModel: UserDataViewModel = hiltViewModel(),
    onAddToPlaylistClicked: () -> Unit,
    onAddToFavoriteClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onDownloadClicked: () -> Unit,
) {
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var selectedSong by remember { mutableStateOf<Song?>(null) }

    val favoriteSongsFetchingState = favoriteSongsViewModel.dataFetchingState.observeAsState()
    val playlistsFetchingState = userViewModel.dataFetchingState.observeAsState()

    LaunchedEffect(Unit) {
        favoriteSongsViewModel.getFavoriteSongs()
        userViewModel.getAlbums()
    }

    val favorites = when (val state = favoriteSongsFetchingState.value) {
        is FavoriteSongFetchingState.Success -> {
            (state.data as List<Song>)
        }

        else -> emptyList()
    }

    val playlists = when (val state = playlistsFetchingState.value) {
        is DataFetchingState.Success -> {
            (state.data as List<String?>)
        }

        else -> emptyList()
    }

    AppScaffoldWithDrawer(
        onProfileClicked = onProfileButtonClicked,
        onSettingsClicked = onSettingsButtonClicked,
        onLogoutClicked = onLogoutButtonClicked
    ) { onOpenDrawer ->
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onOpenDrawer() }) {
                        Image(
                            painter = painterResource(id = R.drawable.hip),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Thư viện",
                        style = TextStyle(
                            fontFamily = NotoSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Top
            ) {
                // Phần nội dung đầu tiên
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(0.48f)
                        ) {
                            LibraryCard(
                                icon = R.drawable.favorite,
                                title = "Đã thích",
                                count = favorites.size,
                                type = "bài hát",
                                onCardClicked = onFavoriteButtonClicked,
                            )
                        }
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            LibraryCard(
                                icon = R.drawable.download_for_offline,
                                title = "Tải xuống",
                                count = 20,
                                type = "bài hát",
                                onCardClicked = onDownloadButtonClicked,
                            )
                        }
                    }
//                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(0.48f)
                        ) {
                            LibraryCard(
                                icon = R.drawable.queue_music,
                                title = "Playlists",
                                count = playlists.size,
                                type = "playlists",
                                onCardClicked = onPlaylistButtonClicked,
                            )
                        }
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            LibraryCard(
                                icon = R.drawable.mdi_account_music_outline,
                                title = "Nghệ sĩ",
                                count = 3,
                                type = "nghệ sĩ",
                                onCardClicked = onArtistsFollowingButtonClicked,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Nghe gần đây",
                            style = TextStyle(
                                fontFamily = NotoSans,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        )
                        Text(
                            text = "Xem tất cả",
                            style = TextStyle(
                                fontFamily = NotoSans,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00FAF2)
                            ),
                            modifier = Modifier.clickable {
                                onViewAllRecentCLicked()
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Hiển thị 5 bài hát gần đây từ SongRepository
                items(SongRepository.allSongs.take(5)) { song ->
                    SongCard(
                        song = song,
                        more = Icons.Default.MoreVert,
                        onSongClick = { onPlaySongClicked() },
                        onMoreClick = {
                            selectedSong = song
                            isBottomSheetVisible = true
                        }
                    )
                }
            }
        }
    }
    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { isBottomSheetVisible = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            BottomSheetContent(
                onDismiss = { isBottomSheetVisible = false },
                selectedSong = selectedSong,
                screenType = "LibraryScreen",
                onAddToPlaylistClicked = onAddToPlaylistClicked,
                onAddToFavoriteClicked = onAddToFavoriteClicked,
                onDeleteClicked = onDeleteClicked,
                onShareClicked = onShareClicked,
                onDownloadClicked = onDownloadClicked,
                favoriteSongsViewModel = favoriteSongsViewModel
            )
        }
    }
}


@Composable
fun LibraryCard(
    icon: Int,
    title: String,
    count: Int,
    type: String,
    onCardClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { onCardClicked() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBackground)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(30.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$count $type",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
            )
        }
    }
}