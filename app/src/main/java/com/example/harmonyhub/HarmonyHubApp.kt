package com.example.harmonyhub

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.harmonyhub.ui.home.HomeScreen
import com.example.harmonyhub.ui.search.SearchScreen

enum class HarmonyHubScreen(@StringRes val title: Int) {
    Home(title = R.string.home),
    Search(title = R.string.search)
}

@Composable
fun HarmonyHubApp() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = HarmonyHubScreen.valueOf(
        backStackEntry.value?.destination?.route ?: HarmonyHubScreen.Home.name
    )

    Scaffold { innerPadding ->

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
//                    onPlayButtonClicked = {
//                        navController.navigate(HarmonyHubScreen.Play.name)
//                    },
//                    onLibraryButtonClicked = {
//                        navController.navigate(HarmonyHubScreen.Library.name)
//                    },
//                    onSettingsButtonClicked = {
//                        navController.navigate(HarmonyHubScreen.Settings.name)
//                    },
//                    onProfileButtonClicked = {
//                        navController.navigate(HarmonyHubScreen.Profile.name)
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

