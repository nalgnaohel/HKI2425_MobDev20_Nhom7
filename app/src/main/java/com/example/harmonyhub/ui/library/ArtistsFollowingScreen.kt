package com.example.harmonyhub.ui.library

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harmonyhub.R
import com.example.harmonyhub.ui.components.Artist
import com.example.harmonyhub.ui.components.ArtistsCard
import com.example.harmonyhub.ui.components.contains
import com.example.harmonyhub.ui.theme.NotoSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsFollowingScreen(
    onBackButtonClicked: () -> Unit,
) {
    var query by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val allArtists = listOf(
        Artist("The Chainsmokers", R.drawable.v),
        Artist("Sia", R.drawable.v),
        Artist("Adele", R.drawable.v)
    )
    // Lọc danh sách bài hát theo từ khóa
    val searchResults = allArtists.filter { it.contains(query, ignoreCase = true) }
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
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Nghệ sĩ đang theo dõi",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /* Handle settings button click */ }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(24.dp)
                )
            }
        }


        // Ô tìm kiếm
        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = {
                Text(
                    text = "Tìm kiếm nghệ sĩ...",
                    style = TextStyle(fontFamily = NotoSans, fontSize = 16.sp)
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
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
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${searchResults.size} nghệ sĩ",
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
            modifier = Modifier.fillMaxSize().padding(top = 8.dp)
        ) {
            items(searchResults.chunked(2)) { artistPair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    artistPair.forEach { artist ->
                        ArtistsCard(
                            artistName = artist.name,
                            artistImg = artist.img,
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                        )
                    }
                    // Nếu hàng có lẻ số nghệ sĩ, bạn có thể thêm một khoảng trống để cân đối
                    if (artistPair.size < 2) {
                        Surface(
                            modifier = Modifier
                                .size(width = 130.dp, height = 170.dp)
                                .clickable {  },
                            color = Color.Transparent
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier.size(width = 130.dp, height = 130.dp),
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
                                    text = "Thêm nghệ sĩ",
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
    }

}