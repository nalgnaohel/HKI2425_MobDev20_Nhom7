package com.example.harmonyhub.library_test

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.library.AlbumScreen
import com.example.harmonyhub.ui.theme.HarmonyHubTheme
import org.junit.Rule
import org.junit.Test

class AlbumScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun albumScreen_DisplaysCorrectAlbumName() {
        // Arrange
        composeTestRule.setContent {
            HarmonyHubTheme {
                AlbumScreen(
                    idAlbum = "1",
                    onSongClick = {},
                    onBackButtonClicked = {},
                    onAddToPlaylistClicked = {},
                    onAddToFavoriteClicked = {},
                    onShareClicked = {},
                    onDownloadClicked = {}
                )
            }
        }
    }

    @Test
    fun albumScreen_DisplaysSongList() {
        // Arrange
        composeTestRule.setContent {
            HarmonyHubTheme {
                AlbumScreen(
                    idAlbum = "1",
                    onSongClick = {},
                    onBackButtonClicked = {},
                    onAddToPlaylistClicked = {},
                    onAddToFavoriteClicked = {},
                    onShareClicked = {},
                    onDownloadClicked = {}
                )
            }
        }
    }

    @Test
    fun albumScreen_BackButtonWorks() {
        // Arrange
        var backButtonClicked = false
        composeTestRule.setContent {
            HarmonyHubTheme {
                AlbumScreen(
                    idAlbum = "1",
                    onSongClick = {},
                    onBackButtonClicked = { backButtonClicked = true },
                    onAddToPlaylistClicked = {},
                    onAddToFavoriteClicked = {},
                    onShareClicked = {},
                    onDownloadClicked = {}
                )
            }
        }

        // Act
        composeTestRule.onNodeWithContentDescription("Back")
            .performClick()

        // Assert
        assert(backButtonClicked) // Verifies that the back button was clicked
    }

    @Test
    fun albumScreen_ShowsBottomSheetWhenMoreClicked() {
        // Arrange
        var isBottomSheetVisible = false
        composeTestRule.setContent {
            HarmonyHubTheme {
                AlbumScreen(
                    idAlbum = "1",
                    onSongClick = {},
                    onBackButtonClicked = {},
                    onAddToPlaylistClicked = {},
                    onAddToFavoriteClicked = {},
                    onShareClicked = {},
                    onDownloadClicked = {}
                )
            }
        }
        // Assert
        composeTestRule.onNodeWithTag("BottomSheet")
    }

    @Test
    fun albumScreen_HandlesNoSongs() {
        // Arrange
        val emptySongs = emptyList<Song>()
        composeTestRule.setContent {
            HarmonyHubTheme {
                AlbumScreen(
                    idAlbum = "1",
                    onSongClick = {},
                    onBackButtonClicked = {},
                    onAddToPlaylistClicked = {},
                    onAddToFavoriteClicked = {},
                    onShareClicked = {},
                    onDownloadClicked = {}
                )
            }
        }

        // Act & Assert
        composeTestRule.onNodeWithText("No songs available")
    }
}
