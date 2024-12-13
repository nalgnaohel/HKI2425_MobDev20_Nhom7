package com.example.harmonyhub

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.harmonyhub.presentation.viewmodel.AuthenticationViewModel
import com.example.harmonyhub.ui.home.HomeScreen
import com.example.harmonyhub.ui.library.ArtistsFollowingScreen
import com.example.harmonyhub.ui.library.DownloadScreen
import com.example.harmonyhub.ui.library.FavoriteScreen
import com.example.harmonyhub.ui.library.HistoryScreen
import com.example.harmonyhub.ui.library.LibraryScreen
import com.example.harmonyhub.ui.library.PlaylistsScreen
import com.example.harmonyhub.ui.account.ForgotPasswordScreen
import com.example.harmonyhub.ui.account.LoginScreen
import com.example.harmonyhub.ui.account.NewPasswordScreen
import com.example.harmonyhub.ui.account.RegisterScreen
import com.example.harmonyhub.ui.account.VerificationScreen
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.library.AddSongToPlaylistScreen
import com.example.harmonyhub.ui.library.AddToPlaylistFromSongScreen
import com.example.harmonyhub.ui.library.AlbumScreen
import com.example.harmonyhub.ui.library.ArtistScreen
import com.example.harmonyhub.ui.library.ChartsScreen
import com.example.harmonyhub.ui.library.PlaylistSongListScreen
import com.example.harmonyhub.ui.library.SelectionScreen
import com.example.harmonyhub.ui.library.SplitMusicScreen
import com.example.harmonyhub.ui.play.NowPlayingBar
import com.example.harmonyhub.ui.play.PlayScreen
import com.example.harmonyhub.ui.profile.FavoriteFriendScreen
import com.example.harmonyhub.ui.profile.FriendsScreen
import com.example.harmonyhub.ui.profile.ProfileScreen
import com.example.harmonyhub.ui.search.SearchScreen
import com.example.harmonyhub.ui.settings.SettingsScreen

enum class HarmonyHubScreen(@StringRes val title: Int, val icon: ImageVector) {
    Login(title = R.string.login, icon = Icons.Filled.AccountBox),
    Register(title = R.string.register, icon = Icons.Filled.AccountBox),
    Home(title = R.string.home, icon = Icons.Filled.Home),
    Search(title = R.string.search, icon = Icons.Filled.Search),
    Play(title = R.string.play, icon = Icons.Filled.PlayArrow),
    Library(title = R.string.library, icon = Icons.Filled.AccountBox),
    Settings(title = R.string.settings, icon = Icons.Filled.Settings),
    Profile(title = R.string.profile, icon = Icons.Filled.AccountBox),
    History(title = R.string.history, icon = Icons.Default.Refresh),
    Favorite(title = R.string.favorite, icon = Icons.Default.Favorite),
    Download(title = R.string.download, icon = Icons.Default.KeyboardArrowDown),
    Playlist(title = R.string.playlist, icon = Icons.Default.AccountBox),
    ArtistsFollowing(title = R.string.artists_following, icon = Icons.Default.Person),
    ForgotPassword(title = R.string.forgot_password, icon = Icons.Default.Info),
    Verification(title = R.string.verification, icon = Icons.Default.Info),
    NewPassword(title = R.string.new_password, icon = Icons.Default.Lock),
    PlaylistSongList(title = R.string.playlist_song_list, icon = Icons.Default.AccountBox),
    AddSongToPlaylist(title = R.string.add_song_to_playlist, icon = Icons.Default.AccountBox),
    Artist(title = R.string.artist, icon = Icons.Default.Person),
    AddToPlaylistFromSong(
        title = R.string.add_to_playlist_from_song,
        icon = Icons.Default.AccountBox
    ),
    Friends(title = R.string.friends, icon = Icons.Default.AccountBox),
    Album(title = R.string.album, icon = Icons.Default.AccountBox),
    FavoriteFriend(title = R.string.favorite, icon = Icons.Default.AccountBox),
    Selection(title = R.string.favorite, icon = Icons.Default.AccountBox)
}

object CurrentSong {
    var currentSong: Song? = null
    fun get(): Song? {
        return currentSong
    }

    fun set(tmp: Song?) {
        currentSong = tmp
    }

}

private val gradientBackground = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF252525),
        Color.Black
    )
)

@Composable
fun HarmonyHubApp(
    authenticationMainViewModel: AuthenticationViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = HarmonyHubScreen.valueOf(
        backStackEntry.value?.destination?.route ?: HarmonyHubScreen.Home.name
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentScreen !in listOf(
                    HarmonyHubScreen.Login,
                    HarmonyHubScreen.Register,
                    HarmonyHubScreen.ForgotPassword,
                    HarmonyHubScreen.Verification,
                    HarmonyHubScreen.NewPassword,
                    HarmonyHubScreen.Profile,
                    HarmonyHubScreen.Settings,
                    HarmonyHubScreen.Play,
                    HarmonyHubScreen.Friends,
                    HarmonyHubScreen.AddToPlaylistFromSong,
                    HarmonyHubScreen.FavoriteFriend
                )
            ) {
                Column {
                    // Kiểm tra nếu CurrentSong.currentSong không null
//                    CurrentSong.currentSong?.let { currentSong ->
//                        NowPlayingBar(
//                            song = currentSong,
//                            isPlaying = true,
//                            onPlayPauseClick = { /* Handle play/pause logic */ },
//                            onNextClick = { /* Handle next song logic */ },
//                            onPreviousClick = { /* Handle previous song logic */ },
//                            onBarClick = { /* Handle bar click logic */ }
//                        )
//                    }
                    BottomNavigationBar(navController = navController)
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBackground),
        ) {
            NavHost(
                navController = navController,
                startDestination = HarmonyHubScreen.Login.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = HarmonyHubScreen.Login.name) {
                    LoginScreen(
                        onLoginButtonClicked = {
                            navController.navigate(HarmonyHubScreen.Home.name)
                        },
                        onRegisterButtonClicked = {
                            navController.navigate(HarmonyHubScreen.Register.name)
                        },
                        onForgotPasswordButtonClicked = {
                            navController.navigate(HarmonyHubScreen.ForgotPassword.name)
                        }
                    )
                }
                composable(route = HarmonyHubScreen.Register.name) {
                    RegisterScreen(
                        onRegisterButtonClicked = {
                            navController.navigate(HarmonyHubScreen.Login.name)
                        },
                        onLoginButtonClicked = {
                            navController.navigate(HarmonyHubScreen.Login.name)
                        }
                    )
                }
                composable(route = HarmonyHubScreen.Home.name) {
                    Nav3(navController, authenticationMainViewModel)
                }
                composable(route = HarmonyHubScreen.Search.name) {
                    val searchNavController = rememberNavController()
                    NavHost(
                        navController = searchNavController,
                        startDestination = "Search"
                    ) {
                        composable(route = "Search") {
                            SearchScreen(
                                onSearchQueryChanged = { /* Handle search query change */ },

                                onAddToPlaylistClicked = {
                                    navController.navigate(HarmonyHubScreen.AddToPlaylistFromSong.name)
                                },
                                onAddToFavoriteClicked = { /* Handle add to favorite logic */ },
                                onShareClicked = { /* Handle share logic */ },
                                onDownloadClicked = { /* Handle download logic */ },
                                navController = searchNavController
                            )
                        }
                        composable(
                            route = "AddToPlaylistFromSong?name={selectedSong.url}",
                            arguments = listOf(
                                navArgument(name = "selectedSong.url") {
                                    type = NavType.StringType
                                    nullable = true
                                }
                            )
                        ) { backStackEntry ->
                            AddToPlaylistFromSongScreen(
                                url = backStackEntry.arguments?.getString("selectedSong.url"),
                                onBackButtonClicked = { searchNavController.popBackStack() }
                            )
                        }
                        composable(
                            route = "Play?index={SongRepository.currentPLaylist.indexOf(CurrentSong.currentSong)}",
                            arguments = listOf(
                                navArgument(name = "SongRepository.currentPLaylist.indexOf(CurrentSong.currentSong)") {
                                    type = NavType.IntType
                                    defaultValue = 0
                                }
                            )
                        ) { backStackEntry ->
                            PlayScreen(
                                index = backStackEntry.arguments?.getInt("SongRepository.currentPLaylist.indexOf(CurrentSong.currentSong)"),
                                onBackButtonClicked = { searchNavController.popBackStack() }
                            )
                        }
                    }
                }
                composable(route = HarmonyHubScreen.Library.name) {
                    val libNavController = rememberNavController()
                    NavHost(
                        navController = libNavController,
                        startDestination = "Library"
                    ) {
                        composable(route = "Library") {
                            LibraryScreen(
                                onProfileButtonClicked = {
                                    navController.navigate(HarmonyHubScreen.Profile.name)
                                },
                                onViewAllRecentCLicked = {
                                    navController.navigate(HarmonyHubScreen.History.name)
                                },
                                onFavoriteButtonClicked = {
                                    navController.navigate(HarmonyHubScreen.Favorite.name)
                                },
                                onDownloadButtonClicked = {
                                    navController.navigate(HarmonyHubScreen.Download.name)
                                },
                                onPlaylistButtonClicked = {
                                    navController.navigate(HarmonyHubScreen.Playlist.name)
                                },
                                onArtistsFollowingButtonClicked = {
                                    navController.navigate(HarmonyHubScreen.ArtistsFollowing.name)
                                },
                                onLogoutButtonClicked = {
                                    authenticationMainViewModel.signOut()
                                    navController.navigate(HarmonyHubScreen.Login.name)
                                },
                                onSplitButtonClicked = {
                                    navController.navigate(HarmonyHubScreen.Selection.name)
                                },
                                onPlaySongClicked = {
                                    navController.navigate(HarmonyHubScreen.Play.name)
                                },
                                onAddToPlaylistClicked = {
                                    navController.navigate(HarmonyHubScreen.AddToPlaylistFromSong.name)
                                },
                                onAddToFavoriteClicked = { /* Handle add to favorite logic */ },
                                onShareClicked = { /* Handle share logic */ },
                                onDownloadClicked = { /* Handle download logic */ },
                                onDeleteClicked = { /* Handle delete logic */ },
                                navController = libNavController
                            )
                        }
                        composable(
                            route = "Play?index={SongRepository.currentPLaylist.indexOf(CurrentSong.currentSong)}",
                            arguments = listOf(
                                navArgument(name = "SongRepository.currentPLaylist.indexOf(CurrentSong.currentSong)") {
                                    type = NavType.IntType
                                    defaultValue = 0
                                }
                            )
                        ) { backStackEntry ->
                            PlayScreen(
                                index = backStackEntry.arguments?.getInt("SongRepository.currentPLaylist.indexOf(CurrentSong.currentSong)"),
                                onBackButtonClicked = { navController.popBackStack() }
                            )
                        }
                    }
                }
//                composable(route = HarmonyHubScreen.Play.name) {
//                    PlayScreen(
//                        onBackButtonClicked = { navController.popBackStack() }
//                    )
//                }

//                composable(route = HarmonyHubScreen.Library.name) {
//                    LibraryScreen(
//                        onProfileButtonClicked = {
//                            navController.navigate(HarmonyHubScreen.Profile.name)
//                        },
//                        onViewAllRecentCLicked = {
//                            navController.navigate(HarmonyHubScreen.History.name)
//                        },
//                        onFavoriteButtonClicked = {
//                            navController.navigate(HarmonyHubScreen.Favorite.name)
//                        },
//                        onDownloadButtonClicked = {
//                            navController.navigate(HarmonyHubScreen.Download.name)
//                        },
//                        onPlaylistButtonClicked = {
//                            navController.navigate(HarmonyHubScreen.Playlist.name)
//                        },
//                        onArtistsFollowingButtonClicked = {
//                            navController.navigate(HarmonyHubScreen.ArtistsFollowing.name)
//                        },
//                        onLogoutButtonClicked = {
//                            authenticationMainViewModel.signOut()
//                            navController.navigate(HarmonyHubScreen.Login.name)
//                        },
//                        onSettingsButtonClicked = {
//                            navController.navigate(HarmonyHubScreen.Settings.name)
//                        },
//                        onPlaySongClicked = {
//                            navController.navigate(HarmonyHubScreen.Play.name)
//                        },
//                        onAddToPlaylistClicked = {
//                            navController.navigate(HarmonyHubScreen.AddToPlaylistFromSong.name)
//                        },
//                        onAddToFavoriteClicked = { /* Handle add to favorite logic */ },
//                        onShareClicked = { /* Handle share logic */ },
//                        onDownloadClicked = { /* Handle download logic */ },
//                        onDeleteClicked = { /* Handle delete logic */ }
//                    )
//                }
                composable(route = HarmonyHubScreen.Profile.name) {
                    ProfileScreen(
                        onBackButtonClicked = { navController.popBackStack() },
                        onFriendsButtonClicked = {
                            navController.navigate(HarmonyHubScreen.Friends.name)
                        }
                    )
                }
                composable(route = HarmonyHubScreen.History.name) {
                    HistoryScreen(
                        onBackButtonClicked = { navController.popBackStack() },
                        onAddToPlaylistClicked = {
                            navController.navigate(HarmonyHubScreen.AddToPlaylistFromSong.name)
                        },
                        onAddToFavoriteClicked = { /* Handle add to favorite logic */ },
                        onDeleteClicked = { /* Handle delete logic */ },
                        onShareClicked = { /* Handle share logic */ },
                        onDownloadClicked = { /* Handle download logic */ }
                    )
                }
                composable(route = HarmonyHubScreen.Favorite.name) {
                    FavoriteScreen(
                        onBackButtonClicked = { navController.popBackStack() },
                        onAddToPlaylistClicked = {
                            navController.navigate(HarmonyHubScreen.AddToPlaylistFromSong.name)
                        },
                        onDeleteClicked = { /* Handle delete logic */ },
                        onShareClicked = { /* Handle share logic */ },
                        onDownloadClicked = { /* Handle download logic */ }
                    )
                }
                composable(route = HarmonyHubScreen.Download.name) {
                    DownloadScreen(
                        onBackButtonClicked = { navController.popBackStack() },

                        onAddToPlaylistClicked = {
                            navController.navigate(HarmonyHubScreen.AddToPlaylistFromSong.name)
                        },
                        onAddToFavoriteClicked = { /* Handle add to favorite logic */ },
                        onShareClicked = { /* Handle share logic */ },
                        onDeleteClicked = { /* Handle delete logic */ }
                    )
                }
                composable(route = HarmonyHubScreen.ArtistsFollowing.name) {
                    Nav(parentNavController = navController)
                }

                composable(route = HarmonyHubScreen.Playlist.name) {
                    Nav2(parentNavController = navController)
                }



                composable(route = HarmonyHubScreen.ForgotPassword.name) {
                    ForgotPasswordScreen(
                        onBackButtonClicked = { navController.popBackStack() },
                        onVerifyButtonClicked = {
                            navController.navigate(HarmonyHubScreen.Login.name)
                        },
                        onRegisterButtonClicked = {
                            navController.navigate(HarmonyHubScreen.Register.name)
                        }
                    )
                }
                composable(route = HarmonyHubScreen.Verification.name) {
                    VerificationScreen(
                        onBackButtonClicked = { navController.popBackStack() },
                        onVerifyCodeButtonClicked = {
                            navController.navigate(HarmonyHubScreen.NewPassword.name)
                        },
                        onResendButtonClicked = { /* Handle resend code button click */ }
                    )
                }
                composable(route = HarmonyHubScreen.NewPassword.name) {
                    NewPasswordScreen(
                        onVerifyButtonClicked = {
                            navController.navigate(HarmonyHubScreen.Login.name)
                        }
                    )
                }
                composable(route = HarmonyHubScreen.Settings.name) {
                    SettingsScreen(
                        onBackButtonClicked = { navController.popBackStack() }
                    )
                }
                composable(route = HarmonyHubScreen.PlaylistSongList.name) {
                    PlaylistSongListScreen(
                        playlistName = "Playlist 1",
                        onBackButtonClicked = { navController.popBackStack() },
                        onAddButtonClicked = { navController.navigate(HarmonyHubScreen.AddSongToPlaylist.name) },
                        onPlaySongClicked = { navController.navigate(HarmonyHubScreen.Play.name) },
                        onAddToPlaylistClicked = { navController.navigate(HarmonyHubScreen.AddToPlaylistFromSong.name) },
                        onAddToFavoriteClicked = { /* Handle add to favorite logic */ },
                        onDeleteClicked = { /* Handle delete logic */ },
                        onShareClicked = { /* Handle share logic */ },
                        onDownloadClicked = { /* Handle download logic */ }

                    )
                }
                composable(route = HarmonyHubScreen.AddSongToPlaylist.name) {
                    AddSongToPlaylistScreen(
                        playlistName = "Playlist 1",
                        onBackButtonClicked = { navController.popBackStack() }
                    )
                }
//                composable(route = HarmonyHubScreen.AddToPlaylistFromSong.name) {
//                    AddToPlaylistFromSongScreen(
//                        onBackButtonClicked = { navController.popBackStack() }
//                    )
//                }

                composable(route = HarmonyHubScreen.Friends.name) {
                    FriendsScreen(
                        onBackButtonClicked = { navController.popBackStack() },
                        onAddButtonClicked = { },
                        onWatchFavoriteClicked = { navController.navigate(HarmonyHubScreen.FavoriteFriend.name) },
                    )

                }
//                composable(route = HarmonyHubScreen.Album.name) {
//                    AlbumScreen(
//                        idAlbum ="",
//                        onShareClicked = {},
//                        onDownloadClicked = {},
//                        onSongClick = {
//                        },
//                        onBackButtonClicked = {},
//                        onAddToPlaylistClicked = {},
//                        onAddToFavoriteClicked = {}
//                    )
//
//                }
                composable(route = HarmonyHubScreen.FavoriteFriend.name) {
                    FavoriteFriendScreen(
                        title = "Bài hát yêu thích của bạn bè",
                        onBackButtonClicked = { navController.popBackStack() }
                    )
                }
                composable(route = HarmonyHubScreen.Selection.name) {
                    Nav5(navController)
                }

            }
        }
    }
}

@Composable
fun Nav(
    parentNavController: NavHostController
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "ArtistsFollowing") {
        composable(route = "ArtistsFollowing") {
            ArtistsFollowingScreen(
                onBackButtonClicked = {
                    parentNavController.popBackStack()
                },
                navController
            )
        }
        composable(
            route = "Artist?name={artist.name}",
            arguments = listOf(
                navArgument(name = "artist.name") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            ArtistScreen(
                myArtist = backStackEntry.arguments?.getString("artist.name"),
                onSongClick = {},
                onBackButtonClicked = {
                    navController.popBackStack() // Quay lại ArtistsFollowing
                },
                onAddToPlaylistClicked = {
                    parentNavController.navigate(HarmonyHubScreen.AddSongToPlaylist.name)
                },
                onAddToFavoriteClicked = {
                    // Xử lý logic thêm vào yêu thích
                },
                onShareClicked = {
                    // Xử lý logic chia sẻ
                },
                onDownloadClicked = {
                    // Xử lý logic tải về
                },
                onAlbumClicked = {
                    parentNavController.navigate(HarmonyHubScreen.Album.name)
                }
            )
        }
    }
}


@Composable
fun Nav2(
    parentNavController: NavHostController
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Playlist") {
        composable(route = "Playlist") {
            PlaylistsScreen(
                onBackButtonClicked = {
                    // Quay lại màn hình cha (nếu có)
                    parentNavController.popBackStack()
                },
                navController
            )
        }
        composable(
            route = "PlaylistSongList?name={playlist.name}",
            arguments = listOf(
                navArgument(name = "playlist.name") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            PlaylistSongListScreen(
                playlistName = backStackEntry.arguments?.getString("playlist.name"),
                onBackButtonClicked = {
                    navController.popBackStack() // Quay lại Playlist
                },
                onAddButtonClicked = {
                    parentNavController.navigate(HarmonyHubScreen.AddSongToPlaylist.name)
                },
                onShareClicked = {
                    // Xử lý logic chia sẻ playlist
                },
                onDownloadClicked = {
                    // Xử lý logic tải về playlist
                },
                onAddToFavoriteClicked = {
                    // Xử lý logic thêm playlist vào yêu thích
                },
                onPlaySongClicked = {
                    // Xử lý logic phát bài hát
                },
                onAddToPlaylistClicked = {
                    // Xử lý logic thêm bài hát vào playlist khác
                },
                onDeleteClicked = {
                    // Xử lý logic xóa playlist hoặc bài hát
                }
            )
        }
    }
}

@Composable
fun Nav3(
    parentNavController: NavHostController,
    authenticationMainViewModel: AuthenticationViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home") {
        composable(route = "Home") {
            HomeScreen(
                onSearchButtonClicked = {
                    parentNavController.navigate(HarmonyHubScreen.Search.name)
                },
                onProfileButtonClicked = {
                    parentNavController.navigate(HarmonyHubScreen.Profile.name)
                },
                onPlayButtonClicked = {
                    parentNavController.navigate(HarmonyHubScreen.Play.name)
                },
                onLibraryButtonClicked = {
                    parentNavController.navigate(HarmonyHubScreen.Library.name)
                },
                onLogoutButtonClicked = {
                    authenticationMainViewModel.signOut()
                    parentNavController.navigate(HarmonyHubScreen.Login.name)
                },
                onSplitButtonClicked = {
                    parentNavController.navigate(HarmonyHubScreen.Selection.name)
                },
                navController = navController
            )
        }
        composable(
            route = "Artist?name={artist.id}",
            arguments = listOf(
                navArgument(name = "artist.id") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            ArtistScreen(
                myArtist = backStackEntry.arguments?.getString("artist.id"),
                onSongClick = {},
                onBackButtonClicked = {
                    navController.popBackStack() // Quay lại ArtistsFollowing
                },
                onAddToPlaylistClicked = {
                    parentNavController.navigate(HarmonyHubScreen.AddSongToPlaylist.name)
                },
                onAddToFavoriteClicked = {
                    // Xử lý logic thêm vào yêu thích
                },
                onShareClicked = {
                    // Xử lý logic chia sẻ
                },
                onDownloadClicked = {
                    // Xử lý logic tải về
                },
                onAlbumClicked = {
                    parentNavController.navigate(HarmonyHubScreen.Album.name)
                }
            )
        }
        composable(
            route = "Album?name={album.id}",
            arguments = listOf(
                navArgument(name = "album.id") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            AlbumScreen(
                idAlbum = backStackEntry.arguments?.getString("album.id"),
                onShareClicked = {},
                onDownloadClicked = {},
                onSongClick = {
                },
                onBackButtonClicked = { navController.popBackStack() },
                onAddToPlaylistClicked = {},
                onAddToFavoriteClicked = {}
            )
        }
        composable(
            route = "Charts?name={chart.id}",
            arguments = listOf(
                navArgument(name = "chart.id") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            ChartsScreen(
                idCharts = backStackEntry.arguments?.getString("chart.id"),
                onShareClicked = {},
                onDownloadClicked = {},
                onSongClick = {
                },
                onBackButtonClicked = { navController.popBackStack() },
                onAddToPlaylistClicked = {},
                onAddToFavoriteClicked = {}
            )
        }

        composable(
            route = "Play?index={SongRepository.currentPLaylist.indexOf(CurrentSong.currentSong)}",
            arguments = listOf(
                navArgument(name = "SongRepository.currentPLaylist.indexOf(CurrentSong.currentSong)") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            PlayScreen(
                index = backStackEntry.arguments?.getInt("SongRepository.currentPLaylist.indexOf(CurrentSong.currentSong)"),
                onBackButtonClicked = { navController.popBackStack() }
            )
        }
    }
}
@Composable
fun Nav5(
    parentNavController: NavHostController
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HarmonyHubScreen.Selection.name) {
        composable(route = HarmonyHubScreen.Selection.name) {
            SelectionScreen(
                navController,
                onBackButtonClicked = {parentNavController.popBackStack()}
            )
        }
        composable(
            route = "SplitMusic?url1={selectedUrls[0]}&url2={selectedUrls[1]}",
            arguments = listOf(
                navArgument(name = "selectedUrls[0]") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(name = "selectedUrls[1]") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            SplitMusicScreen(
                url1 = backStackEntry.arguments?.getString("selectedUrls[0]"),
                url2 = backStackEntry.arguments?.getString("selectedUrls[1]"),
                onBackButtonClicked = {navController.popBackStack()}
            )
        }
    }
}
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HarmonyHubAppbar(
//    @StringRes currentScreen: Int,
//    canNavigateBack: Boolean,
//    navigateUp: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    TopAppBar(
//        title = {Text(stringResource(currentScreen))},
//        colors = TopAppBarDefaults.mediumTopAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer
//        )
//    )
//}

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val screens =
        listOf(HarmonyHubScreen.Home, HarmonyHubScreen.Search, HarmonyHubScreen.Library)

    NavigationBar(
        containerColor = Color.Transparent
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.title)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.name } == true,
                onClick = {
                    navController.navigate(screen.name) {
                        // Avoid multiple copies of the same destination on the back stack
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        // Restore state when reselecting a previously selected item
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
