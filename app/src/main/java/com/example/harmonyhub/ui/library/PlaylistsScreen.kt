package com.example.harmonyhub.ui.library

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.harmonyhub.R
import com.example.harmonyhub.data.repository.FirebasePlaylist
import com.example.harmonyhub.presentation.viewmodel.DataFetchingState
import com.example.harmonyhub.presentation.viewmodel.PlaylistViewModel
import com.example.harmonyhub.presentation.viewmodel.UserDataViewModel
import com.example.harmonyhub.ui.components.Playlist
import com.example.harmonyhub.ui.components.PlaylistCard
import com.example.harmonyhub.ui.components.contains
import com.example.harmonyhub.ui.theme.NotoSans
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistsScreen(
    onBackButtonClicked: () -> Unit,
    navController: NavHostController,
    userDataViewModel: UserDataViewModel = hiltViewModel(),
    playlistViewModel: PlaylistViewModel = hiltViewModel()
) {

    val dataFetchingState = userDataViewModel.dataFetchingState.observeAsState()
    val context = LocalContext.current

    val allPlaylists = remember { mutableListOf<Playlist>() }

    LaunchedEffect(Unit) {
        userDataViewModel.getAlbums()
    }

    LaunchedEffect(allPlaylists.size) {
        userDataViewModel.getAlbums()
    }

    LaunchedEffect(dataFetchingState.value) {
        when (dataFetchingState.value) {
            is DataFetchingState.Success -> {
                allPlaylists.clear()
                val albums =
                    (dataFetchingState.value as DataFetchingState.Success).data as List<FirebasePlaylist?>
                albums.forEach { album ->
                    if (album != null) {
                        allPlaylists.add(Playlist(album.name, R.drawable.v))
                        Log.d("Album", "Album name: $album.name")
                    }
                }
                userDataViewModel.resetDataFetchingState()
            }

            is DataFetchingState.Error -> {
                val message = (dataFetchingState.value as DataFetchingState.Error).message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                userDataViewModel.resetDataFetchingState()
            }

            else -> {}
        }
    }

    var query by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var newPlaylistName by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    // Lọc danh sách bài hát theo từ khóa
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
                text = "Danh sách phát của tôi",
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
                text = "${searchResults.size} danh sách phát",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontSize = 20.sp,
                    color = Color.Gray
                )
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Xóa tất cả",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontSize = 16.sp,
                    color = Color(0xFF00FAF2),
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.clickable {
                    runBlocking {
                        launch {
                            allPlaylists.forEach {
                                playlistViewModel.deletePlayList(it.name)
                            }
                            userDataViewModel.getAlbums()
                        }
                    }
                }
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(searchResults.chunked(2)) { playlistPair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    playlistPair.forEach { playlist ->
                        PlaylistCard(
                            playlist = playlist,
                            onPlaylistClicked = { navController.navigate("PlaylistSongList?name=${playlist.name}") },
                        )
                    }
                    // Nếu hàng có lẻ số nghệ sĩ, bạn có thể thêm một khoảng trống để cân đối
                    if (playlistPair.size < 2) {
                        Surface(
                            modifier = Modifier
                                .size(width = 155.dp, height = 200.dp)
                                .clickable { },
                            color = Color.Transparent
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(width = 155.dp, height = 145.dp)
                                        .clickable { showDialog = true },
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.add),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape),
                                        tint = Color.White

                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Tạo playlist mới",
                                    style = TextStyle(
                                        fontFamily = NotoSans,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp
                                    ),
                                    maxLines = 1,
                                    overflow = Ellipsis,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                            }
                        }
                    }
                }
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
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                text = {
                    Column {
                        OutlinedTextField(
                            value = newPlaylistName,
                            onValueChange = { newPlaylistName = it },
                            label = { Text("Tên playlist mới")},
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
}