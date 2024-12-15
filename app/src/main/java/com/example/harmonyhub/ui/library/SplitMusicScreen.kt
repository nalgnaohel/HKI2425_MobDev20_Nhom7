package com.example.harmonyhub.ui.library

import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.enableLiveLiterals
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.components.SongCard
import com.example.harmonyhub.ui.theme.NotoSans

@Composable
fun SplitMusicScreen(
    id1: String?,
    id2: String?,
    onBackButtonClicked: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    // Chuyển đổi URL thành đối tượng Song
    val song1 = id1?.let { findSongByUrl(it) }
    val song2 = id2?.let { findSongByUrl(it) }

    // Dùng một map để giữ trạng thái của từng checkbox cho mỗi bài hát
    val checkedStatesSong1 = remember { mutableStateOf(mapOf<String, Boolean>()) }
    val checkedStatesSong2 = remember { mutableStateOf(mapOf<String, Boolean>()) }

    // Quản lý trạng thái của nút (Phát/Dừng)
    val isPlaying = remember { mutableStateOf(false) }

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
            modifier = Modifier.fillMaxWidth(),
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
                text = "Bản tách nhạc",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            item {
                Text(
                    text = "Bài hát gốc",
                    fontFamily = NotoSans,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(listOf(song1, song2)) { song ->
                if (song != null) {
                    SongCard(
                        song = song,
                        more = null,
                        onSongClick = {},
                        onMoreClick = {}
                    )
                }
            }

            item {
                Text(
                    text = "Các bản tách",
                    fontFamily = NotoSans,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Duyệt qua danh sách các phần cho từng bài hát (Bass, Drum, Beat, Vocal)
            items(
                listOf(
                    Pair(song1, checkedStatesSong1),
                    Pair(song2, checkedStatesSong2)
                )
            ) { (song, checkedStates) ->
                // Duyệt qua các phần của bài hát
                val parts = listOf("Bass", "Drum", "Beat", "Vocal")
                parts.forEach { part ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Hiển thị tên phần (Bass, Drum, Beat, Vocal)
                        Text(
                            text = "$part ${if (song == song1) 1 else 2}",
                            fontFamily = NotoSans,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            color = Color.White
                        )

                        // Checkbox ở cuối mỗi phần (Chỉ thay đổi khi isPlaying là false)
                        val isChecked = checkedStates.value[part] ?: false
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { isChecked ->
                                // Cập nhật trạng thái checkbox khi người dùng thay đổi (Chỉ khi isPlaying là false)
                                if (!isPlaying.value) {
                                    checkedStates.value = checkedStates.value.toMutableMap().apply {
                                        put(part, isChecked)
                                    }
                                }
                            },
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF00FAF2), uncheckedColor = Color.Gray)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Kiểm tra trạng thái checkbox để quyết định màu nút
        val isAnyChecked =
            (checkedStatesSong1.value.values.any { it }) || (checkedStatesSong2.value.values.any { it })

        Button(
            onClick = {
                // Thực hiện hành động khi nhấn nút
                isPlaying.value = !isPlaying.value
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isAnyChecked) Color(0xFF00FAF2) else Color.Gray,
                contentColor = Color.DarkGray
            ),
            enabled = isAnyChecked, // Disable button nếu không có checkbox nào được chọn
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = if (isPlaying.value) "Dừng" else "Phát",
                fontFamily = NotoSans,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
fun findSongByUrl(id: String): Song? {
    return SongRepository.allSongs.find { it.id == id }
}




