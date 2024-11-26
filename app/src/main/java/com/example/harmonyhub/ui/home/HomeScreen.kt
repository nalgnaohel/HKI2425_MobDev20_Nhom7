package com.example.harmonyhub.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harmonyhub.R
import com.example.harmonyhub.ui.components.AppScaffoldWithDrawer
import com.example.harmonyhub.ui.components.ArtistsCard
import com.example.harmonyhub.ui.theme.NotoSans
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.harmonyhub.data.network.ArtistOut
import com.example.harmonyhub.data.network.ResponseHomeScreenData


private val gradientBackground = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF00FAF2),
        Color(0xFF1E3264)
    )
)

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.Blue)
    }
}

@Composable
fun ErrorScreen(
    onRefreshContent: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_connection_error),
                contentDescription = stringResource(R.string.connection_error)
            )
            IconButton(onClick = onRefreshContent) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(R.string.refresh)
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    onSearchButtonClicked: () -> Unit,
    onPlayButtonClicked: () -> Unit,
    onLibraryButtonClicked: () -> Unit,
    onProfileButtonClicked: () -> Unit,
    onLogoutButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {

    //add view model
    val homeUiState = viewModel.state.collectAsStateWithLifecycle().value
    when (homeUiState) {
        is HomeUIState.Loading -> {
            LoadingScreen()
        }
        is HomeUIState.Error -> {
            ErrorScreen(
                onRefreshContent = { viewModel.fetchHomePageData() }
            )
        }
        is HomeUIState.Success -> {
            // Truy cập vào thuộc tính popularItem khi trạng thái là Success
            val popularItems = homeUiState.popularItem
            MainHomeScreen(
                onSearchButtonClicked,
                onPlayButtonClicked,
                onLibraryButtonClicked,
                onProfileButtonClicked,
                onLogoutButtonClicked,
                modifier,
                popularItems
            )
        }
    }

}

@Composable
fun MainHomeScreen(
    onSearchButtonClicked: () -> Unit,
    onPlayButtonClicked: () -> Unit,
    onLibraryButtonClicked: () -> Unit,
    onProfileButtonClicked: () -> Unit,
    onLogoutButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    resPopularItem: ResponseHomeScreenData
) {
    //Main UI
    AppScaffoldWithDrawer(
        onProfileClicked = onProfileButtonClicked,
        onSettingsClicked = {},
        onLogoutClicked = onLogoutButtonClicked
    ) { onOpenDrawer ->
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onOpenDrawer() }) {
                        Image(
                            painter = painterResource(id = R.drawable.hip),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Thomas",
                        style = TextStyle(
                            fontFamily = NotoSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    )
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    //Header with avatar, username, and profile button


                    Spacer(modifier = Modifier.height(16.dp))

                    //Top Genres Section
                    Text(
                        text = "Thể loại",
                        style = TextStyle(
                            fontFamily = NotoSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    )
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
                    Text(
                        text = "Nghệ sĩ",
                        style = TextStyle(
                            fontFamily = NotoSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    )

                    LazyRowArtist(resPopularItem.listPopularArtist)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Suggestions Section
                    Text(
                        text = "Đề xuất cho bạn",
                        style = TextStyle(
                            fontFamily = NotoSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    )

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
                    Text(
                        text = "Bảng xếp hạng",
                        style = TextStyle(
                            fontFamily = NotoSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    )

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
}


@Composable
fun GenreCard(genre: String) {
    Surface(
        modifier = Modifier
            .size(width = 150.dp, height = 150.dp)
            .padding(4.dp)
            .clickable {  },
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(gradientBackground),
            contentAlignment = Alignment.Center) {
            Text(
                text = genre,
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            )
        }
    }
}

@Composable
fun SuggestionCard(songName: String, artistName: String) {
    Surface(
        modifier = Modifier
            .size(width = 125.dp, height = 180.dp)
            .clickable {  },
        color = Color.Transparent
    ) {
        Column(modifier = Modifier.padding(4.dp))
        {
            Box(
                modifier = Modifier.size(width = 125.dp, height = 125.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.v),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)),
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = songName,
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
                maxLines = 1,
                overflow = Ellipsis
            )
            Text(
                text = artistName,
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontSize = 14.sp
                ),
                color = Color.Gray,
                maxLines = 1, overflow = Ellipsis
            )
        }
    }
}

@Composable
fun ChartsCard(chartImg: Int) {
    Surface(
        modifier = Modifier
            .size(width = 150.dp, height = 150.dp)
            .clickable {  },
        color = Color.Transparent
    ) {
        Box(contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.padding(4.dp)) {
            // Placeholder for the image
            Image(
                painter = painterResource(id = chartImg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp))
            )
        }
    }
}


@Composable
fun LazyRowArtist(temple: MutableList<ArtistOut>?) {
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val listArtist: MutableList<ArtistOut>? = temple
        if (listArtist != null) {
            Log.d("CheckMDKMWKAMD", "${listArtist.size}")
            items(listArtist) { artist ->
                // Lấy dữ liệu từ mỗi item và truyền vào ArtistsCard
                ArtistsCard(
                    artist.name,  // Tên nghệ sĩ
                    artist.image,  // URL ảnh
                    artist.id  // ID nghệ sĩ
                )
            }
        }

    }

}

