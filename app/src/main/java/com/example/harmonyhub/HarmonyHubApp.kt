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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
import com.example.harmonyhub.ui.library.AddSongToPlaylistScreen
import com.example.harmonyhub.ui.library.PlaylistSongListScreen
import com.example.harmonyhub.ui.play.NowPlayingBar
import com.example.harmonyhub.ui.play.PlayScreen
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
    ArtistsFollowing(title = R.string.artistsFollowing, icon = Icons.Default.Person),
    ForgotPassword(title = R.string.forgotPassword, icon = Icons.Default.Info),
    Verification(title = R.string.verification, icon = Icons.Default.Info),
    NewPassword(title = R.string.newPassword, icon = Icons.Default.Lock),
    PlaylistSongList(title = R.string.playlistSongList, icon = Icons.Default.AccountBox),
    AddSongToPlaylist(title = R.string.addSongToPlaylist, icon = Icons.Default.AccountBox),
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
                    HarmonyHubScreen.Play
                )
            ) {
                Column {
                    NowPlayingBar(
                        songName = "Closer",
                        artistName = "The Chainsmokers, Halsey",
                        isPlaying = true,
                        onPlayPauseClick = { /* Handle play/pause logic */ },
                        onNextClick = { /* Handle next song logic */ },
                        onPreviousClick = { /* Handle previous song logic */ },
                        onBarClick = { navController.navigate(HarmonyHubScreen.Play.name) }
                    )
                    BottomNavigationBar(navController = navController)
                }
            }
        }
    )  { innerPadding ->
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
                    HomeScreen(
                        onSearchButtonClicked = {
                            navController.navigate(HarmonyHubScreen.Search.name)
                        },
                        onProfileButtonClicked = {
                            navController.navigate(HarmonyHubScreen.Profile.name)
                        },
                        onPlayButtonClicked = {
                            navController.navigate(HarmonyHubScreen.Play.name)
                        },
                        onLibraryButtonClicked = {
                            navController.navigate(HarmonyHubScreen.Library.name)
                        },
//                    onSettingsButtonClicked = {
//                        navController.navigate(HarmonyHubScreen.Settings.name)
//                    },
//                    onAboutButtonClicked = {
//                        navController.navigate(HarmonyHubScreen.About.name)
//                    },
//                    onFeedbackButtonClicked = {
//                        navController.navigate(HarmonyHubScreen.Feedback.name)
//                    },
//                    onShareButtonClicked = {
//                        navController.navigate(HarmonyHubScreen.Share.name)
//                    },
//                    onSupportButtonClicked = {
//                        navController.navigate(HarmonyHubScreen.Support.name)
//                    },
//                    onPrivacyButtonClicked = {
//                        navController.navigate(HarmonyHubScreen.Privacy.name)
//                    },
                        onLogoutButtonClicked = {
                            authenticationMainViewModel.signOut()
                            navController.navigate(HarmonyHubScreen.Login.name)
                        },
                        onSettingsButtonClicked = {
                            navController.navigate(HarmonyHubScreen.Settings.name)
                        }
                    )
                }
                composable(route = HarmonyHubScreen.Search.name) {
                    SearchScreen(
                        onSearchQueryChanged = { /* Handle search query change */ },
                    )
                }
                composable(route = HarmonyHubScreen.Play.name) {
                    PlayScreen(
                        onBackButtonClicked = { navController.popBackStack() }
                    )
                }

                composable(route = HarmonyHubScreen.Library.name) {
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
                        onPlaySongClicked = {
                            navController.navigate(HarmonyHubScreen.Play.name)
                        },
                        onLogoutButtonClicked = {
                            authenticationMainViewModel.signOut()
                            navController.navigate(HarmonyHubScreen.Login.name)
                        },
                        onSettingsButtonClicked = {
                            navController.navigate(HarmonyHubScreen.Settings.name)
                        }


                    )
                }
                composable(route = HarmonyHubScreen.Profile.name) {
                    ProfileScreen(
                        onBackButtonClicked = { navController.popBackStack() }
                    )
                }
                composable(route = HarmonyHubScreen.History.name) {
                    HistoryScreen(
                        onBackButtonClicked = { navController.popBackStack() },


                        )
                }
                composable(route = HarmonyHubScreen.Favorite.name) {
                    FavoriteScreen(
                        onBackButtonClicked = { navController.popBackStack() },

                        )
                }
                composable(route = HarmonyHubScreen.Download.name) {
                    DownloadScreen(
                        onBackButtonClicked = { navController.popBackStack() },

                        )
                }
                composable(route = HarmonyHubScreen.Playlist.name) {
                    PlaylistsScreen(
                        onBackButtonClicked = { navController.popBackStack() },
                        onPlaylistClicked = {
                            navController.navigate(HarmonyHubScreen.PlaylistSongList.name)
                        },
                    )
                }
                composable(route = HarmonyHubScreen.ArtistsFollowing.name) {
                    ArtistsFollowingScreen(
                        onBackButtonClicked = { navController.popBackStack() }
                    )
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
                composable(route = HarmonyHubScreen.PlaylistSongList.name){
                    PlaylistSongListScreen(
                        playlistName = "Playlist 1",
                        onBackButtonClicked = { navController.popBackStack() },
                        onAddButtonClicked = { navController.navigate(HarmonyHubScreen.AddSongToPlaylist.name) }
                    )
                }
                composable(route = HarmonyHubScreen.AddSongToPlaylist.name) {
                    AddSongToPlaylistScreen(
                        playlistName = "Playlist 1",
                        onBackButtonClicked = { navController.popBackStack() }
                    )
                }


            }
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