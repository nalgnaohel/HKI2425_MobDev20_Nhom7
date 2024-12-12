package com.example.harmonyhub.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.harmonyhub.R
import com.example.harmonyhub.ui.theme.NotoSans

data class Friend(
    val name: String,
    val email: String,
    val imageResId: Int,
    val uid: String = ""
)

fun Friend.contains(query: String, ignoreCase: Boolean = true): Boolean {
    return this.name.contains(query, ignoreCase) || this.email.contains(query, ignoreCase)
}

@Composable
fun FriendCard(
    friend: Friend,
    screenType: String,
    onMoreClick: () -> Unit,
    onAcceptClick: () -> Unit,
    onRejectClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(friend.imageResId)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),
            contentDescription = "Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = friend.name,
                fontFamily = NotoSans,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = friend.email,
                fontFamily = NotoSans,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        if (screenType == "Friends") {
            // Menu hành động (tùy chọn)
            IconButton(onClick = { onMoreClick() }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More Options",
                    tint = Color.Gray
                )
            }
        } else {
            IconButton(onClick = { onAcceptClick() }) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Accept",
                    tint = Color(0xFF00FAF2)
                )
            }
            IconButton(onClick = { onRejectClick() }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Reject",
                    tint = Color.LightGray
                )
            }
        }
    }
}
