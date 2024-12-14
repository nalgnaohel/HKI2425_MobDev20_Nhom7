package com.example.harmonyhub.ui.library


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.components.SongCard



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionScreen(
    navController: NavHostController,
    onBackButtonClicked :() -> Unit
) {
    val songs: List<Song> = SongRepository.allSongs
    var query by remember { mutableStateOf("") }
    val selectedSongs = remember { mutableStateMapOf<String, Boolean>() }
    val selectedUrls = remember { mutableStateListOf<String>() }

    val filteredSongs = songs.filter { song ->
        song.name.contains(query, ignoreCase = true) || song.artist.contains(query, ignoreCase = true)
    }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Nút quay lại
        IconButton(
            onClick = { onBackButtonClicked() },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Quay lại")
        }

        // Thanh tìm kiếm
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Tìm kiếm bài hát") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Cyan,
                unfocusedIndicatorColor = Color.Gray
            )
        )

        Text(
            text = "Chọn bài hát yêu thích",
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(filteredSongs) { song ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    SongCard(
                        song = song,
                        more = null, // Không cần nút More
                        onSongClick = {
                            // Toggle trạng thái CheckBox khi nhấn vào SongCard
                            if (selectedUrls.size < 2 || (selectedSongs[song.id] == true)) {
                                selectedSongs[song.id] = !(selectedSongs[song.id] ?: false)
                                if (selectedSongs[song.id] == true) {
                                    selectedUrls.add(song.url) // Thêm URL vào danh sách
                                } else {
                                    selectedUrls.remove(song.url) // Xoá URL khỏi danh sách
                                }
                            }
                        },
                        onMoreClick = {}
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Checkbox(

                        checked = selectedSongs[song.id] ?: false,
                        onCheckedChange = { isChecked ->
                            if (selectedUrls.size >= 2) {
                                selectedUrls.clear()
                            }
                            if (isChecked) {
                                if (selectedUrls.size < 2) { // Kiểm tra nếu số lượng chưa đạt 2
                                    selectedSongs[song.id] = true
                                    selectedUrls.add(song.url)
                                }
                            } else {
                                selectedSongs[song.id] = false
                                selectedUrls.remove(song.url)
                            }
                        }
                    )

                }
            }
        }

        // Nút xác nhận
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (selectedUrls.size == 2) {
                    Log.d("SelectionScreen", "URL 1: ${selectedUrls[0]}, URL 2: ${selectedUrls[1]}")
                    navController.navigate("SplitMusic?url1=${selectedUrls[0]}&url2=${selectedUrls[1]}")
                }
            },
            enabled = selectedUrls.isNotEmpty()
        ) {
            Text(text = "Xác nhận", fontSize = 16.sp)
        }
    }
}




