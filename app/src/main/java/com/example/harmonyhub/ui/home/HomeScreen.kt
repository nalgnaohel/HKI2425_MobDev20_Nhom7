package com.example.harmonyhub.ui.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harmonyhub.R

@Composable
fun HomeScreen(
    onSearchButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        item {
            //Header with avatar, username, and profile button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ProfileImage()
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Thomas", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Row {
                    IconButton(onClick = { /* Notification */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.notification),
                            contentDescription = "Notifications",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(onClick = { /* Settings */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.settings),
                            contentDescription = "Settings",
                            modifier = Modifier.size(24.dp)

                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Top Genres Section
            Text(text = "Genres", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listOf("V-Pop", "K-Pop", "R&B", "Hip Hop")) { genre ->
                    GenreCard(genre)
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Top Mixes Section
            Text(text = "Top Mixes", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listOf("Pop Mix", "Chill Mix", "Dance Mix", "Jazz Mix")) { mix ->
                    MixCard(mix)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Suggestions Section
            Text(text = "Suggestion", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)

            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    listOf(
                        "Faded" to "Alan Walker",
                        "Shape of You" to "Ed Sheeran",
                        "Perfect" to "Ed Sheeran"
                    )
                ) { (suggestion, artistName) ->
                    SuggestionCard(suggestion, artistName)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Charts Section
            Text(text = "Charts", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)

            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    listOf(
                        R.drawable.ic_launcher_background,
                        R.drawable.ic_launcher_background,
                        R.drawable.ic_launcher_background,
                        R.drawable.ic_launcher_background,
                        R.drawable.ic_launcher_background,
                        R.drawable.ic_launcher_background
                    )
                ) { chart ->
                    ChartsCard(chart)
                }
            }
        }
    }

}

@Composable
fun ProfileImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = "Profile",
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
    )
}

@Composable
fun GenreCard(genre: String) {
    Surface(
        modifier = Modifier
            .size(width = 150.dp, height = 150.dp),
        color = Color(0xFF24E2CD), // Replace with the actual color for genre background
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = genre, color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun MixCard(mix: String) {
    Surface(
        modifier = Modifier
            .size(width = 150.dp, height = 150.dp),
        color = Color.DarkGray,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(contentAlignment = Alignment.BottomCenter) {
            // Placeholder for the image
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                color = Color(0xFFB22222), // Bottom strip color for mix type
                shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
            ) {
                Text(
                    text = mix,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SuggestionCard(suggestion: String, artistName: String) {
    Surface(
        modifier = Modifier
            .size(width = 125.dp, height = 170.dp)
    ) {
        Column()
        {
            Box(
                modifier = Modifier.size(width = 125.dp, height = 125.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                text = suggestion,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = Ellipsis
            )
            Text(text = artistName, fontSize = 14.sp, maxLines = 1, overflow = Ellipsis)
        }
    }
}

@Composable
fun ChartsCard(chartImg: Int) {
    Surface(
        modifier = Modifier
            .size(width = 150.dp, height = 150.dp),
        color = Color.DarkGray
    ) {
        Box(contentAlignment = Alignment.BottomCenter) {
            // Placeholder for the image
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}