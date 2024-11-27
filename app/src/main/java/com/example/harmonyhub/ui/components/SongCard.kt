package com.example.harmonyhub.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

data class Song(
    val id: String,
    val name: String,
    val artist: String,
    val imageResId: String,
    val url: String
)

fun Song.contains(query: String, ignoreCase: Boolean = true): Boolean {
    return this.name.contains(query, ignoreCase) || this.artist.contains(query, ignoreCase)
}

@Composable
fun SongCard(
    song: Song,
    more: ImageVector,
    onSongClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onSongClick(song.id) },

        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(song.imageResId)
                .crossfade(true)
                .build(),
            error = painterResource(com.example.harmonyhub.R.drawable.ic_broken_image),
            placeholder = painterResource(id = com.example.harmonyhub.R.drawable.loading_img),
            contentDescription = "Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Tên bài hát
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = song.name,  // song.title là tên bài hát
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = song.artist,  // song.artist là tên nghệ sĩ
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        // Menu hành động (tùy chọn)
        IconButton(onClick = {
            // Xử lý menu hành động tại đây
        }) {
            Icon(
                imageVector = more,
                contentDescription = "More Options",
                tint = if (more == Icons.Default.MoreVert) Color.Gray else Color.White
            )
        }
    }
}