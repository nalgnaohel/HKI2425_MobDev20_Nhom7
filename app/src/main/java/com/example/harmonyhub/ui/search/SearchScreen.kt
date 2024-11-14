package com.example.harmonyhub.ui.search

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harmonyhub.R

@Composable
fun SearchScreen(
    onSearchQueryChanged: (String) -> Unit,
    onSearchButtonClicked: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    val searchResults = listOf(
        "Shape of You - Ed Sheeran",
        "Blinding Lights - The Weeknd",
        "Levitating - Dua Lipa",
        "Stay - Justin Bieber",
        "As It Was - Harry Styles"
    ).filter { it.contains(query, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        // Thanh tìm kiếm
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Tìm kiếm bài hát, nghệ sĩ...") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Tìm kiếm")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        // Hiển thị danh sách kết quả tìm kiếm
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    text = if (query.isEmpty()) "Tìm kiếm gần đây" else "Kết quả tìm kiếm",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(searchResults) { result ->
                SearchResultItem(result)
            }
        }
    }
}

@Composable
fun SearchResultItem(result: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hình ảnh đại diện của bài hát hoặc nghệ sĩ
        Image(
            painter = painterResource(id = R.drawable.v),
            contentDescription = "Ảnh bài hát",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Tiêu đề
        Text(
            text = result,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        // Thêm các nút hoặc hành động khác
        IconButton(onClick = { /* Xử lý hành động phát bài hát */ }) {
            Icon(imageVector = Icons.Default.Clear, contentDescription = "Phát")
        }
    }
}

