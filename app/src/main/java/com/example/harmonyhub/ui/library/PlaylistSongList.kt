package com.example.harmonyhub.ui.library

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.textFieldColors
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.harmonyhub.R
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.presentation.viewmodel.FavoriteSongsViewModel
import com.example.harmonyhub.presentation.viewmodel.PlaylistSongFetchingState
import com.example.harmonyhub.presentation.viewmodel.PlaylistViewModel
import com.example.harmonyhub.ui.components.BottomSheetContent
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.components.SongCard
import com.example.harmonyhub.ui.components.contains
import com.example.harmonyhub.ui.theme.NotoSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistSongListScreen(
    playlistName: String?,
    onPlaySongClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    onAddButtonClicked: () -> Unit,
    onAddToPlaylistClicked: () -> Unit,
    onAddToFavoriteClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onDownloadClicked: () -> Unit,
    playlistViewModel: PlaylistViewModel = hiltViewModel(),
    favoriteSongsViewModel: FavoriteSongsViewModel = hiltViewModel()
    ) {

    val dataFetchingState = playlistViewModel.dataFetchingState.observeAsState()
    val allSongs = remember {mutableListOf<Song>()}

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        playlistViewModel.getPlaylistSongs(playlistName ?: "")
    }

    LaunchedEffect(dataFetchingState.value) {
        when (val state = dataFetchingState.value) {
            is PlaylistSongFetchingState.Success -> {
                when (val data = (dataFetchingState.value as PlaylistSongFetchingState.Success).data) {
                    is List<*> -> {
                        val songs = data as List<Song>
                        allSongs.clear()
                        allSongs.addAll(songs)
                        playlistViewModel.resetDataFetchingState()
                    }
                    is String -> {
                        val message = data as String
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        playlistViewModel.resetDataFetchingState()
                        playlistViewModel.getPlaylistSongs(playlistName ?: "")
                    }
                }
            }

            is PlaylistSongFetchingState.Error -> {
                val message = (dataFetchingState.value as PlaylistSongFetchingState.Error).message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                playlistViewModel.resetDataFetchingState()
            }

            else -> {}
        }
    }

    var query by remember { mutableStateOf("") }

    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var isBottomTitleSheetVisible by remember { mutableStateOf(false) }
    var selectedSong by remember { mutableStateOf<Song?>(null) }
    var titleBottomSheet by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    var showDialog by remember { mutableStateOf(false) }
    val searchResults = allSongs.filter { it.contains(query, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Thanh tiêu đề
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onBackButtonClicked() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = playlistName ?:"playlist",
                    style = TextStyle(
                        fontFamily = NotoSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row {
                IconButton(onClick = { onAddButtonClicked() }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(onClick = {
                    titleBottomSheet = playlistName ?:"playlist"
                    isBottomTitleSheetVisible = true
                }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = {
                Text(
                    text = "Tìm kiếm bài hát...",
                    style = TextStyle(fontFamily = NotoSans, fontSize = 16.sp)
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .clip(RoundedCornerShape(16.dp)),
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(fontFamily = NotoSans, fontSize = 20.sp),
            colors = textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                containerColor = Color.Gray.copy(alpha = 0.2f)
            ),
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
        )

        // Thông tin số bài hát
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${searchResults.size} bài hát",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontSize = 20.sp,
                    color = Color.Gray
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Row {
                IconButton(onClick = { /* Play Action */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icons8_circled_play_64),
                        contentDescription = "Play",
                        tint = Color(0xFF00FAF2),
                        modifier = Modifier.size(50.dp)
                    )
                }
                IconButton(onClick = { /* Share Action */ }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color.White,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }

        // Danh sách bài hát
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(searchResults) { song ->
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
    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { isBottomSheetVisible = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            BottomSheetContent(
                onDismiss = { isBottomSheetVisible = false },
                selectedSong = selectedSong,
                screenType = "PlaylistSongListScreen",
                onAddToPlaylistClicked = onAddToPlaylistClicked,
                onAddToFavoriteClicked = onAddToFavoriteClicked,
                onDeleteClicked = {
                    selectedSong?.let {
                        playlistViewModel.removeSongFromPlayList(it, playlistName ?: "")
                    }
                },
                onShareClicked = onShareClicked,
                onDownloadClicked = onDownloadClicked,
                favoriteSongsViewModel = favoriteSongsViewModel
            )
        }
    }
    if (isBottomTitleSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { isBottomTitleSheetVisible = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            BottomSheetTitleContent(
                onDismiss = { isBottomTitleSheetVisible = false },
                title = titleBottomSheet,
                playlistName = playlistName ?: "",
                onBackButtonClicked = onBackButtonClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetTitleContent(onDismiss: () -> Unit,
                                    title: String,
                                    playlistName: String,
                                    onBackButtonClicked: () -> Unit,
                                    playlistViewModel: PlaylistViewModel = hiltViewModel(),
) {
    var showRenameDialog by remember { mutableStateOf(false) }
    var newPlaylistName by remember { mutableStateOf(title) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontFamily = NotoSans,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = Color.DarkGray, thickness = 0.3.dp)
        Spacer(modifier = Modifier.height(8.dp))

//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable { showRenameDialog = true }
//        ) {
//            Icon(
//                imageVector = Icons.Default.Edit,
//                contentDescription = "Edit",
//                tint = Color.Gray,
//                modifier = Modifier.size(23.dp)
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            Text(
//                "Đổi tên playlist", modifier = Modifier.padding(vertical = 8.dp),
//                fontFamily = NotoSans, fontSize = 16.sp
//            )
//        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    playlistViewModel.deletePlayList(playlistName)
                    onBackButtonClicked()
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.remove),
                contentDescription = "Remove",
                tint = Color.Gray,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "Xóa playlist", modifier = Modifier.padding(vertical = 8.dp),
                fontFamily = NotoSans, fontSize = 16.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /*Todo*/ }
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = Color.Gray,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "Chia sẻ", modifier = Modifier.padding(vertical = 8.dp),
                fontFamily = NotoSans, fontSize = 16.sp
            )
        }

    }
    if (showRenameDialog) {
        AlertDialog(
            onDismissRequest = { showRenameDialog = false },
            title = {
                Text(
                    text = "Đổi tên Playlist",
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = newPlaylistName,
                        onValueChange = { newPlaylistName = it },
                        label = { Text("Tên playlist mới") },
                        singleLine = true,
                        maxLines = 1,
                        textStyle = TextStyle(fontFamily = NotoSans, fontSize = 16.sp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF00FAF2),
                            focusedLabelColor = Color(0xFF00FAF2),
                        ),
                        trailingIcon = {
                            // Hiển thị icon xóa nếu TextField có dữ liệu
                            if (newPlaylistName.isNotEmpty()) {
                                IconButton(onClick = { newPlaylistName = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear"
                                    )
                                }
                            }
                        },
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showRenameDialog = false
                        onDismiss()
                    },
                    enabled = newPlaylistName.isNotBlank()
                ) {
                    Text(
                        "OK",
                        fontFamily = NotoSans,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (newPlaylistName.isNotBlank()) Color(0xFF00FAF2) else Color.Gray
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showRenameDialog = false
                    onDismiss()
                }) {
                    Text(
                        "Hủy",
                        fontFamily = NotoSans,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00FAF2)
                    )
                }
            }
        )
    }
}
