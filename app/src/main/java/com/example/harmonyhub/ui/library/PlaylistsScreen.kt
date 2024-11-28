package com.example.harmonyhub.ui.library

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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.harmonyhub.R
import com.example.harmonyhub.presentation.viewmodel.DataFetchingState
import com.example.harmonyhub.presentation.viewmodel.UserDataViewModel
import com.example.harmonyhub.ui.components.Playlist
import com.example.harmonyhub.ui.components.PlaylistCard
import com.example.harmonyhub.ui.components.contains
import com.example.harmonyhub.ui.theme.NotoSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistsScreen(
    onBackButtonClicked: () -> Unit,
    onPlaylistClicked: (String) -> Unit,
    userDataViewModel: UserDataViewModel = hiltViewModel()
) {

    val dataFetchingState = userDataViewModel.dataFetchingState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(dataFetchingState.value) {
        when (dataFetchingState.value) {
            is DataFetchingState.Success -> {
                val message = (dataFetchingState.value as DataFetchingState.Success).data
                Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show()
                userDataViewModel.resetDataFetchingState()
            }
            is DataFetchingState.Error -> {

                val message = (dataFetchingState.value as DataFetchingState.Error).message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                userDataViewModel.resetDataFetchingState()
            }
            else -> { }
        }
    }

    var query by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var newPlaylistName by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    val allPlaylists = listOf(
        Playlist("Playlist 1", R.drawable.v),
        Playlist("Playlist 2", R.drawable.v),
        Playlist("Playlist 3", R.drawable.v),
        Playlist("Playlist 4", R.drawable.v),
    )
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
                            onClick = { onPlaylistClicked(playlist.name) },
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
                            userDataViewModel.setAlbums(newPlaylistName)
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

@Preview(showBackground = true)
@Composable
fun PlaylistsScreenPreview() {
    PlaylistsScreen(onBackButtonClicked = {}, onPlaylistClicked = {})
}
