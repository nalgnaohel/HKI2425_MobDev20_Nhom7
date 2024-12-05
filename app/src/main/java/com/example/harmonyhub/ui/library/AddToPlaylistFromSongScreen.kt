package com.example.harmonyhub.ui.library

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.textFieldColors
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.example.harmonyhub.R
import com.example.harmonyhub.presentation.viewmodel.DataFetchingState
import com.example.harmonyhub.presentation.viewmodel.PlaylistSongFetchingState
import com.example.harmonyhub.presentation.viewmodel.PlaylistViewModel
import com.example.harmonyhub.presentation.viewmodel.UserDataViewModel
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.theme.NotoSans
import kotlinx.coroutines.launch
import retrofit2.http.Url

private val gradientBackground = Brush.verticalGradient(
    colors = listOf(Color(0xFF04A8A3), Color(0xFF0A91BD))
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToPlaylistFromSongScreen(
    url: String?,
    onBackButtonClicked: () -> Unit,
    userDataViewModel: UserDataViewModel = hiltViewModel(),
    playlistViewModel: PlaylistViewModel = hiltViewModel(),
    song: Song = Song("1", "Song 1", "Artist 1", "Album 1", "Genre 1")
) {
    val focusManager = LocalFocusManager.current

    var query by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var newPlaylistName by remember { mutableStateOf("") }

    var selectedPlaylists by remember { mutableStateOf(setOf<String>())}

    val albumsFetchingState = userDataViewModel.dataFetchingState.observeAsState()
    val context = LocalContext.current

    val allPlaylists = remember { mutableListOf<String>() }

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        userDataViewModel.getAlbums()
    }

    LaunchedEffect(selectedPlaylists) {
        Log.d("SelectedPlaylists", "Playlist size: ${selectedPlaylists.size}")
    }

    LaunchedEffect(albumsFetchingState.value) {
        when (albumsFetchingState.value) {
            is DataFetchingState.Success -> {
                allPlaylists.clear()
                allPlaylists.addAll((albumsFetchingState.value as DataFetchingState.Success).data as List<String>)
                userDataViewModel.resetDataFetchingState()
            }
            is DataFetchingState.Error -> {
                val message = (albumsFetchingState.value as DataFetchingState.Error).message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                userDataViewModel.resetDataFetchingState()
            }
            else -> {}
        }
    }
    val searchResults = allPlaylists.filter { it.contains(query, ignoreCase = true) }
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
                text = "Thêm vào danh sách phát",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { showDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Ô tìm kiếm
        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = {
                Text(
                    text = "Tìm kiếm danh sách phát...",
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${allPlaylists.size} danh sách phát",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontSize = 20.sp,
                    color = Color.Gray
                )
            )
        }

        // Danh sách playlist
        LazyColumn {
            items(allPlaylists) { playlist ->
                PlaylistItem(
                    playlistName = playlist,
                    songCount = "2 bài hát",
                    onPlaylistClicked = {
                        if (selectedPlaylists.contains(playlist)) {
                            selectedPlaylists -= playlist
                        } else {
                            selectedPlaylists += playlist
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Nút hoàn tất
        Button(
            onClick = {
                // Launch a coroutine to handle the asynchronous operations
                selectedPlaylists.forEach { playlist ->
                    lifecycleOwner.lifecycleScope.launch {
                        playlistViewModel.addSongToPlayList(song, playlist)

                        when (val state = playlistViewModel.dataFetchingState.value) {
                            is PlaylistSongFetchingState.Error -> {
                                val message = state.message
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                playlistViewModel.resetDataFetchingState()
                            }
                            else -> {}
                        }
                    }

            }
//                onBackButtonClicked()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
                .align(Alignment.CenterHorizontally)
                .background(gradientBackground, shape = MaterialTheme.shapes.medium),
        ) {
            Text(
                text = "Xong",
                fontFamily = NotoSans,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
    // Dialog để nhập tên playlist mới
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    "Tạo playlist mới",
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                TextField(
                    value = newPlaylistName,
                    onValueChange = { newPlaylistName = it },
                    placeholder = {
                        Text(
                            "Tên playlist",
                            fontFamily = NotoSans,
                            fontSize = 16.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
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
            },

            confirmButton = {
                TextButton(
                    onClick = {
//                            onPlaylistClicked(newPlaylistName)
                        userDataViewModel.setAlbum(newPlaylistName)
                        showDialog = false
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
                TextButton(onClick = { showDialog = false }) {
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

    @Composable
    fun PlaylistItem(playlistName: String,
                     songCount: String,
                     onPlaylistClicked: () -> Unit = { }
    ) {
        var isSelected by remember { mutableStateOf(false) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.v),
                contentDescription = "Playlist Image",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = playlistName,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    text = songCount,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            RadioButton(
                selected = isSelected,
                onClick = { isSelected = !isSelected
                          onPlaylistClicked()
                          },
                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF0A91BD))
            )
        }
    }
