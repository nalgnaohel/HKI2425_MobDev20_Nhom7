package com.example.harmonyhub.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.harmonyhub.R
import com.example.harmonyhub.presentation.viewmodel.FavoriteSongsViewModel
import com.example.harmonyhub.presentation.viewmodel.PlaylistViewModel
import com.example.harmonyhub.ui.theme.NotoSans

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
    more: ImageVector?,
    onSongClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onSongClick() },

        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(song.imageResId)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),
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
        IconButton(onClick = { onMoreClick() }) {
            if (more != null) {
                Icon(
                    imageVector = more,
                    contentDescription = "More Options",
                    tint = if (more == Icons.Default.MoreVert) Color.Gray else Color.White
                )
            }
        }
    }
}

@Composable
fun BottomSheetContent(
    onDismiss: () -> Unit,
    selectedSong: Song?,
    screenType: String,
    onAddToPlaylistClicked: () -> Unit,
    onAddToFavoriteClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onDownloadClicked: () -> Unit,
    favoriteSongsViewModel: FavoriteSongsViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        selectedSong?.let { song ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(song.imageResId),
                    contentDescription = "Song Image",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = song.name,
                        fontFamily = NotoSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 1
                    )
                    Text(
                        text = song.artist,
                        fontFamily = NotoSans,
                        fontSize = 16.sp,
                        color = Color.Gray,
                        maxLines = 1
                    )
                }
            }
        }
        HorizontalDivider(color = Color.DarkGray, thickness = 0.5.dp)
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onDismiss()
                    onAddToPlaylistClicked()
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.add_48),
                contentDescription = "Add",
                tint = Color.Gray,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "Thêm vào danh sách phát",
                modifier = Modifier.padding(vertical = 8.dp),
                fontFamily = NotoSans, fontSize = 16.sp
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (selectedSong != null) {
                        favoriteSongsViewModel?.addFavoriteSong(selectedSong)
                    }
                    onDismiss()
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.favorite),
                contentDescription = "Favorite",
                tint = Color.LightGray,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "Thêm vào yêu thích", modifier = Modifier.padding(vertical = 8.dp),
                fontFamily = NotoSans, fontSize = 16.sp
            )
        }

        if (screenType != "SearchScreen" && screenType != "ArtistScreen") {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onDismiss()
                        onDeleteClicked()
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.minus),
                    contentDescription = "Delete",
                    tint = Color.Gray,
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    "Xóa khỏi danh sách này", modifier = Modifier.padding(vertical = 8.dp),
                    fontFamily = NotoSans, fontSize = 16.sp
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onDismiss()
                    onDownloadClicked() }
        ) {
            Icon(
                painter = painterResource(R.drawable.download_for_offline),
                contentDescription = "Download",
                tint = Color.LightGray,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "Tải về", modifier = Modifier.padding(vertical = 8.dp),
                fontFamily = NotoSans, fontSize = 16.sp
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onDismiss()
                    onShareClicked() }
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = Color.Gray,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "Chia sẻ", modifier = Modifier.padding(vertical = 8.dp),
                fontFamily = NotoSans, fontSize = 16.sp
            )
        }

    }
}