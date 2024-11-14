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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harmonyhub.R
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onSearchButtonClicked: () -> Unit,
    onPlayButtonClicked: () -> Unit,
    onLibraryButtonClicked: () -> Unit,
    onProfileButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ProfileDrawerContent(
                onProfileButtonClicked = onProfileButtonClicked,
            )
        },
        scrimColor = Color.Black.copy(alpha = 0.9f),
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
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.hip),
                                contentDescription = "Profile",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Thomas", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                    Row {
                        IconButton(onClick = { /* Notification */ }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        IconButton(onClick = { /* Settings */ }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
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

                // Artists Section
                Text(text = "Artists", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                LazyRow(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        listOf(
                            "Alan Walker" to R.drawable.v,
                            "Ed Sheeran" to R.drawable.v,
                            "The Weeknd" to R.drawable.v,
                            "Adele" to R.drawable.v,
                            "Taylor Swift" to R.drawable.v
                        )
                    ) {
                        ArtistsCard(it.first, it.second)
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
                            "Perfect" to "Ed Sheeran",
                            "Blinding Lights" to "The Weeknd",
                            "Thinking Out Loud" to "Ed Sheeran"
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
                            R.drawable.top100,
                            R.drawable.top100,
                            R.drawable.top100,
                            R.drawable.top100,
                            R.drawable.top100,
                            R.drawable.top100,

                            )
                    ) { chart ->
                        ChartsCard(chart)
                    }
                }
            }
        }

    }
}

@Composable
fun ProfileImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.hip),
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
        color = MaterialTheme.colorScheme.primary, // Replace with the actual color for genre background
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = genre, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ArtistsCard(artistName: String, artistImg: Int) {
    Surface(
        modifier = Modifier
            .size(width = 150.dp, height = 175.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.size(width = 150.dp, height = 150.dp),
                ) {
                Image(
                    painter = painterResource(id = artistImg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = artistName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = Ellipsis,
            )

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
                    painter = painterResource(id = R.drawable.v),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
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
                painter = painterResource(id = chartImg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun ProfileDrawerContent(
    onProfileButtonClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.hip),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Thomas",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Tài khoản miễn phí",
                    fontSize = 16.sp,
                    color = Color.LightGray
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .padding(end = 32.dp)
        )

        TextButton(onClick = onProfileButtonClicked) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Hồ sơ",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Hồ sơ", fontSize = 20.sp, color = Color.White)
            }
        }

        TextButton(onClick = { /* Nâng cấp lên VIP */ }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Nâng cấp lên VIP",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Nâng cấp lên VIP", fontSize = 20.sp, color = Color.White)
            }
        }
        TextButton(onClick = { /* Cài đặt */ }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Cài đặt",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Cài đặt", fontSize = 20.sp, color = Color.White)
            }
        }
        TextButton(onClick = { /* Đăng xuất */ }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Đăng xuất",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Đăng xuất", fontSize = 20.sp, color = Color.White)
            }
        }


    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        onSearchButtonClicked = {},
        onPlayButtonClicked = {},
        onLibraryButtonClicked = {},
        onProfileButtonClicked = {},
    )
}