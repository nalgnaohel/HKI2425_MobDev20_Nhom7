package com.example.harmonyhub

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.harmonyhub.ui.home.HomeScreen
import com.example.harmonyhub.ui.library.LibraryScreen
import com.example.harmonyhub.ui.play.PlayScreen
import com.example.harmonyhub.ui.search.SearchScreen

enum class HarmonyHubScreen(@StringRes val title: Int, val icon: ImageVector) {
    Home(title = R.string.home, icon = Icons.Filled.Home),
    Search(title = R.string.search, icon = Icons.Filled.Search),
    Play(title = R.string.play, icon = Icons.Filled.PlayArrow),
    Library(title = R.string.library, icon = Icons.Filled.AccountBox),
    Settings(title = R.string.settings, icon = Icons.Filled.Settings),
    Profile(title = R.string.profile, icon = Icons.Filled.AccountBox),

}

@Composable
fun HarmonyHubApp() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = HarmonyHubScreen.valueOf(
        backStackEntry.value?.destination?.route ?: HarmonyHubScreen.Home.name
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController) },
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = HarmonyHubScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
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
//                    onLogoutButtonClicked = {
//                        navController.navigate(HarmonyHubScreen.Logout.name)
//                    },
//                    onLoginButtonClicked = {
//                        navController.navigate(HarmonyHubScreen.Login.name)
//                    },
//                    onRegisterButtonClicked = {
//                        navController.navigate(HarmonyHubScreen.Register.name)
//                    },
//                    onForgotPasswordButtonClicked = {
//                        navController.navigate(HarmonyHubScreen.ForgotPassword.name)
//                    },
//                    onResetPasswordButtonClicked = {
//                        navController.navigate(HarmonyHubScreen.ResetPassword.name)
//                    }
                )
            }
            composable(route = HarmonyHubScreen.Search.name) {
                SearchScreen(
                    onSearchQueryChanged = { /* Handle search query change */ },
                    onSearchButtonClicked = { /* Handle search button click */ }
                )
            }
            composable(route = HarmonyHubScreen.Play.name) {
                // Play screen content
                PlayScreen()
            }
            composable(route = HarmonyHubScreen.Library.name) {
                // Library screen content
                LibraryScreen()
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
fun BottomNavigationBar(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val screens =
        listOf(HarmonyHubScreen.Home, HarmonyHubScreen.Search, HarmonyHubScreen.Library)

    NavigationBar {
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


