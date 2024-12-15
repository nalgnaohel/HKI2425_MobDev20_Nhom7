package com.example.harmonyhub.ui.library


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.example.harmonyhub.CurrentSong
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.presentation.viewmodel.PlaylistSongFetchingState
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.components.SongCard
import com.example.harmonyhub.ui.theme.NotoSans
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionScreen(
    navController: NavHostController,
    onBackButtonClicked: () -> Unit
) {
    val songs: List<Song> = SongRepository.allSongs
    var query by remember { mutableStateOf("") }
    val selectedSongs = remember { mutableStateMapOf<String, Boolean>() }
    val selectedUrls = remember { mutableStateListOf<String>() }

    val focusManager = LocalFocusManager.current

    val filteredSongs = songs.filter { song ->
        song.name.contains(query, ignoreCase = true) || song.artist.contains(
            query,
            ignoreCase = true
        )
    }
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
                text = "Tách nhạc",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = {
                Text(
                    text = "Tìm kiếm bài hát",
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
            text = "Chọn bài hát muốn tách",
            fontFamily = NotoSans,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f)
        ) {
            items(filteredSongs) { song ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    SongCard(
                        song = song,
                        more = null,
                        onSongClick = {
                            // Toggle trạng thái CheckBox khi nhấn vào SongCard
                            if (selectedUrls.size < 2 || (selectedSongs[song.id] == true)) {
                                selectedSongs[song.id] = !(selectedSongs[song.id] ?: false)
                                if (selectedSongs[song.id] == true) {
                                    selectedUrls.add(song.id) // Thêm URL vào danh sách
                                } else {
                                    selectedUrls.remove(song.id) // Xoá URL khỏi danh sách
                                }
                            }
                        },
                        onMoreClick = {}
                    )
                    Checkbox(
                        checked = selectedSongs[song.id] ?: false,
                        onCheckedChange = { isChecked ->
                            if (selectedUrls.size >= 2) {
                                selectedUrls.clear()
                            }
                            if (isChecked) {
                                if (selectedUrls.size < 2) { // Kiểm tra nếu số lượng chưa đạt 2
                                    selectedSongs[song.id] = true
                                    selectedUrls.add(song.id)
                                }
                            } else {
                                selectedSongs[song.id] = false
                                selectedUrls.remove(song.id)
                            }
                        },
                        colors = androidx.compose.material3.CheckboxDefaults.colors(Color(0xFF00FAF2))
                    )
                }
            }
        }

        // Nút hoàn tất
        Button(
            onClick = {
                if (selectedUrls.size == 2) {
                    Log.d("SelectionScreen", "URL 1: ${selectedUrls[0]}, URL 2: ${selectedUrls[1]}")
                    navController.navigate("SplitMusic?url1=${selectedUrls[0]}&url2=${selectedUrls[1]}")
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedUrls.size == 2) Color(0xFF00FAF2) else Color.Gray,
                contentColor = Color.DarkGray
            ),
            enabled = selectedUrls.size == 2,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Xong",
                fontFamily = NotoSans,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}