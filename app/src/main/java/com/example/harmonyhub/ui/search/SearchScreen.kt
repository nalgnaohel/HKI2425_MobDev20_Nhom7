package com.example.harmonyhub.ui.search

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harmonyhub.R
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.components.SongCard
import com.example.harmonyhub.ui.components.contains
import com.example.harmonyhub.ui.theme.NotoSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onSearchQueryChanged: (String) -> Unit,
) {
    var query by remember { mutableStateOf("") }

    //Bo sung: Khi slide thi cung can clearFocus
    val focusManager = LocalFocusManager.current

    val allSongs = listOf(
        Song("Inside Out", "The Chainsmokers, Charlee", R.drawable.v),
        Song("Young", "The Chainsmokers", R.drawable.v),
        Song("Beach House", "The Chainsmokers, Sick", R.drawable.v),
        Song("Kills You Slowly", "The Chainsmokers", R.drawable.v),
        Song("Setting Fires", "The Chainsmokers, XYLO", R.drawable.v),
        Song("The Real Slim Shady", "Eminem", R.drawable.v),
        Song("Lose Yourself", "Eminem", R.drawable.v),
        Song("Bohemian Rhapsody", "Queen", R.drawable.v),
        Song("Shape of You", "Ed Sheeran", R.drawable.v),
        Song("Perfect", "Ed Sheeran", R.drawable.v),
        Song("Thinking Out Loud", "Ed Sheeran", R.drawable.v),
        Song("Photograph", "Ed Sheeran", R.drawable.v),
        Song("Imagine Dragons", "Imagine Dragons", R.drawable.v),
        Song("Believer", "Imagine Dragons", R.drawable.v),
        Song("Radioactive", "Imagine Dragons", R.drawable.v),
        Song("Thunder", "Imagine Dragons", R.drawable.v),
        Song("Demons", "Imagine Dragons", R.drawable.v),
    )

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
        // Thanh tìm kiếm
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
            text = if (query.isEmpty()) "Tìm kiếm gần đây" else "Kết quả tìm kiếm",
            fontFamily = NotoSans,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Hiển thị danh sách kết quả tìm kiếm
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(searchResults) { song ->
                SongCard(name = song.name, artists = song.artists, songImg = song.songImg)
            }
        }
    }
}


