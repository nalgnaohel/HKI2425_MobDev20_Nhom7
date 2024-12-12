package com.example.harmonyhub.ui.library

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.harmonyhub.ui.components.contains
import com.example.harmonyhub.ui.theme.NotoSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSongToPlaylistScreen(
    playlistName: String,
    onBackButtonClicked: () -> Unit,
) {
    val allSongs: List<Song> = SongRepository.allSongs

    var query by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    // Lọc danh sách bài hát theo từ khóa
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
                    text = "Thêm bài hát vào playlist",
                    style = TextStyle(
                        fontFamily = NotoSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = {
                /* Handle saving playlist when clicked */
                onBackButtonClicked()
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Done",
                    tint = Color(0xFF00FAF2),
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
        Text(
            text = if (query.isEmpty()) "Phát gần đây" else "Kết quả tìm kiếm",
            fontFamily = NotoSans,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(searchResults) { song ->
                SongCard(
                    song = song,
                    more = Icons.Default.AddCircle,
                    onSongClick = {},
                    onMoreClick = {}
                )
            }
        }
    }

}