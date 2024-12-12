package com.example.harmonyhub.ui.search

import android.widget.MediaController
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.harmonyhub.CurrentSong
import com.example.harmonyhub.R
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.presentation.viewmodel.FavoriteSongFetchingState
import com.example.harmonyhub.presentation.viewmodel.FavoriteSongsViewModel
import com.example.harmonyhub.ui.components.BottomSheetContent
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.components.SongCard
import com.example.harmonyhub.ui.components.contains
import com.example.harmonyhub.ui.theme.NotoSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    onSearchQueryChanged: (String) -> Unit,
    onAddToPlaylistClicked: () -> Unit,
    onAddToFavoriteClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onDownloadClicked: () -> Unit,
    favoriteSongsViewModel: FavoriteSongsViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var selectedSong by remember { mutableStateOf<Song?>(null) }

    val dataFetchingState = favoriteSongsViewModel.dataFetchingState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
//        favoriteSongsViewModel.getFavoriteSongs()
    }

    LaunchedEffect(dataFetchingState.value) {
        if (dataFetchingState.value is FavoriteSongFetchingState.Success) {
            // Show toast
            Toast.makeText(context, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show()
            favoriteSongsViewModel.resetDataFetchingState()
        }
        if (dataFetchingState.value is FavoriteSongFetchingState.Error) {
            // Show toast
            val message = (dataFetchingState.value as FavoriteSongFetchingState.Error).message
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            favoriteSongsViewModel.resetDataFetchingState()
        }
    }

    val focusManager = LocalFocusManager.current

    val allSongs: List<Song> = SongRepository.allSongs

    val searchResults = allSongs.filter { it.contains(query, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }

    ) {
        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = {
                Text(
                    text = "Tìm kiếm bài hát, nghệ sĩ...",
                    style = TextStyle(fontFamily = NotoSans, fontSize = 16.sp)
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(16.dp)),
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(fontFamily = NotoSans, fontSize = 20.sp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                containerColor = Color.Gray.copy(alpha = 0.2f)
            ),
            trailingIcon = {
                // Hiển thị icon xóa nếu TextField có dữ liệu
                if (query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
        )

        Text(
            text = if (query.isEmpty()) "Phát gần đây" else "Kết quả tìm kiếm",
            fontFamily = NotoSans,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(searchResults) { song ->
                SongCard(
                    song = song,
                    more = Icons.Default.MoreVert,
                    onSongClick = { CurrentSong.set(song)
                        navController.navigate("Play?index=${SongRepository.currentPLaylist.indexOf(
                        CurrentSong.currentSong)}")},
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
                screenType = "SearchScreen",
                onAddToPlaylistClicked = {selectedSong?.let {
                    navController.navigate("AddToPlaylistFromSong?name=${it.url}")
                }},
                onAddToFavoriteClicked = onAddToFavoriteClicked,
                onDeleteClicked = {},
                onShareClicked = onShareClicked,
                onDownloadClicked = onDownloadClicked,
                favoriteSongsViewModel = favoriteSongsViewModel
            )
        }
    }
}


