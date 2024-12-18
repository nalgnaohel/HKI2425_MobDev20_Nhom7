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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.harmonyhub.CurrentSong
import com.example.harmonyhub.R
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.data.network.AlbumOut
import com.example.harmonyhub.data.network.ArtistOut
import com.example.harmonyhub.data.network.ChartOut
import com.example.harmonyhub.data.network.ResponseHomeScreenData
import com.example.harmonyhub.presentation.viewmodel.UserDataViewModel
import com.example.harmonyhub.ui.components.AlbumCard
import com.example.harmonyhub.ui.components.AppScaffoldWithDrawer
import com.example.harmonyhub.ui.components.ArtistsCard
import com.example.harmonyhub.ui.components.ChartCard
import com.example.harmonyhub.ui.components.GenreCard
import com.example.harmonyhub.ui.components.SuggestionCard
import com.example.harmonyhub.ui.theme.NotoSans

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize().testTag("Circular Progress Indicator"),
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
        modifier = Modifier.fillMaxSize().testTag("Error"),
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
    navController: NavHostController,
    onSearchButtonClicked: () -> Unit,
    onPlayButtonClicked: () -> Unit,
    onLibraryButtonClicked: () -> Unit,
    onProfileButtonClicked: () -> Unit,
    onLogoutButtonClicked: () -> Unit,
    onSplitButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    userDataViewModel: UserDataViewModel = hiltViewModel(),
) {

    //add view model
    val homeUiState = viewModel.state.collectAsStateWithLifecycle().value
    val username = userDataViewModel.userName.observeAsState()
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
                navController,
                onSearchButtonClicked,
                onPlayButtonClicked,
                onLibraryButtonClicked,
                onProfileButtonClicked,
                onLogoutButtonClicked,
                onSplitButtonClicked,
                modifier,
                username.value.toString(),
                popularItems,

                )
        }
    }

}
@Composable
fun MainHomeScreen(
    navController: NavHostController,
    onSearchButtonClicked: () -> Unit,
    onPlayButtonClicked: () -> Unit,
    onLibraryButtonClicked: () -> Unit,
    onProfileButtonClicked: () -> Unit,
    onLogoutButtonClicked: () -> Unit,
    onSplitButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    nameUser : String,
    resPopularItem: ResponseHomeScreenData
) {
    //Main UI
    AppScaffoldWithDrawer(
        onProfileClicked = onProfileButtonClicked,
        onSplitClicked = onSplitButtonClicked,
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
                        modifier = Modifier.testTag("Username"),
                        text = nameUser,
                        style = TextStyle(
                            fontFamily = NotoSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    )
                }
                Row {
                    IconButton(onClick = { onSplitButtonClicked() }) {
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
                    Spacer(modifier = Modifier.height(16.dp))
                    //Header with avatar, username, and profile button
                    // Charts Section
                    Text(
                        text = "Bảng xếp hạng",
                        style = TextStyle(
                            fontFamily = NotoSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    )

                    LazyRowChart(resPopularItem.listChart, navController)





                    Spacer(modifier = Modifier.height(16.dp))

                    // Artists Section
                    Text(
                        text = "Nghệ sĩ phổ biến",
                        style = TextStyle(
                            fontFamily = NotoSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    )

                    LazyRowArtist(resPopularItem.listPopularArtist,navController)

                    Spacer(modifier = Modifier.height(16.dp))
                    // Album Section
                    Text(
                        text = "Album phổ biến",
                        style = TextStyle(
                            fontFamily = NotoSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    )

                    LazyRowAlbum(resPopularItem.listPopularAlbums,navController)

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
                            SongRepository.allSongs
                        ) { song ->
                            SuggestionCard(song.name, song.artist, song.id, song.imageResId, onSongClicked = {
                                CurrentSong.set(song)
                                navController.navigate("Play?index=${SongRepository.currentPLaylist.indexOf(
                                    CurrentSong.currentSong)}")} )
                        }
                    }



                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
@Composable
fun LazyRowArtist(temple: MutableList<ArtistOut>?,
                  navController : NavHostController) {
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val listArtist: MutableList<ArtistOut>? = temple
        if (listArtist != null) {
            items(listArtist) { artist ->
                ArtistsCard(
                    artist.name,  // Tên nghệ sĩ
                    artist.image,  // URL ảnh
                    artist.id, // ID nghệ sĩ
                    onArtistCardClick = {navController.navigate("Artist?name=${artist.id}")}
                )
            }
        }
    }
}

@Composable
fun LazyRowAlbum(temple: MutableList<AlbumOut>?,
                 navController: NavHostController) {
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val listAlbum: MutableList<AlbumOut>? = temple
        if (listAlbum != null) {
            items(listAlbum) { album ->
                // Lấy dữ liệu từ mỗi item và truyền vào ArtistsCard
                AlbumCard(
                    album.name,  // Tên nghệ sĩ
                    album.image,  // URL ảnh
                    album.id,  // ID nghệ sĩ
                    album.listArtist,
                    onAlbumCardClick = {navController.navigate("Album?name=${album.id}")}
                )
            }
        }
    }
}

@Composable
fun LazyRowChart(temple: MutableList<ChartOut>?,
                 navController: NavHostController) {
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val listChart: MutableList<ChartOut>? = temple
        if (listChart != null) {
            items(listChart) { chart ->
                // Lấy dữ liệu từ mỗi item và truyền vào ArtistsCard
                ChartCard(
                    chart.image,
                    chart.name,
                    chart.id,
                    onChartClicked = {navController.navigate("Charts?name=${chart.id}")}
                )
            }
        }
    }
}






