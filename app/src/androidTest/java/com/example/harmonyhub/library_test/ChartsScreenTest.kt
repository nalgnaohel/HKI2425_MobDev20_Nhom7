package com.example.harmonyhub.library_test

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isEnabled
import androidx.compose.ui.test.onNodeWithContentDescription
import com.example.harmonyhub.ui.library.ChartsScreen
import org.junit.Rule
import org.junit.Test

class ChartsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testChartsScreen_DisplayAlbumName() {
        // Arrange
        composeTestRule.setContent {
            ChartsScreen(
                idCharts = "1",
                onSongClick = {},
                onBackButtonClicked = {},
                onAddToPlaylistClicked = {},
                onAddToFavoriteClicked = {},
                onShareClicked = {},
                onDownloadClicked = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithText("Top Songs - Global").assertIsDisplayed()
    }

    @Test
    fun testChartsScreen_SongListVisible() {
        // Arrange
        composeTestRule.setContent {
            ChartsScreen(
                idCharts = "1",
                onSongClick = {},
                onBackButtonClicked = {},
                onAddToPlaylistClicked = {},
                onAddToFavoriteClicked = {},
                onShareClicked = {},
                onDownloadClicked = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithTag("Song List").assertIsDisplayed()
    }

    @Test
    fun testChartsScreen_SongClick() {
        // Arrange
        var songClicked = false
        composeTestRule.setContent {
            ChartsScreen(
                idCharts = "1",
                onSongClick = { songClicked = true },
                onBackButtonClicked = {},
                onAddToPlaylistClicked = {},
                onAddToFavoriteClicked = {},
                onShareClicked = {},
                onDownloadClicked = {}
            )
        }

        // Act
        composeTestRule.onNodeWithText("Die With A Smile").performClick()

        // Assert
        assert(songClicked) { "Song click was not handled properly" }
    }

    @Test
    fun testBackButtonClick() {
        // Arrange
        var backClicked = false
        composeTestRule.setContent {
            ChartsScreen(
                idCharts = "1",
                onSongClick = {},
                onBackButtonClicked = { backClicked = true },
                onAddToPlaylistClicked = {},
                onAddToFavoriteClicked = {},
                onShareClicked = {},
                onDownloadClicked = {}
            )
        }

        // Act
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Assert
        assert(backClicked) { "Back button click was not handled properly" }
    }

    @Test
    fun testSongMoreOptions() {
        // Arrange
        var bottomSheetVisible = false
        composeTestRule.setContent {
            ChartsScreen(
                idCharts = "1",
                onSongClick = {},
                onBackButtonClicked = {},
                onAddToPlaylistClicked = {},
                onAddToFavoriteClicked = {},
                onShareClicked = {},
                onDownloadClicked = {}
            )
        }

        // Act
        composeTestRule.onNodeWithText("Die With A Smile")

        composeTestRule.onNodeWithContentDescription("More Options")

    }

    @Test
    fun testBottomSheetClose() {
        // Arrange
        var bottomSheetVisible = false
        composeTestRule.setContent {
            ChartsScreen(
                idCharts = "1",
                onSongClick = {},
                onBackButtonClicked = {},
                onAddToPlaylistClicked = {},
                onAddToFavoriteClicked = {},
                onShareClicked = {},
                onDownloadClicked = {}
            )
        }

        // Act
        composeTestRule.onNodeWithText("Die With A Smile")
        composeTestRule.onNodeWithText("More Options")

        composeTestRule.onNodeWithContentDescription("Dismiss")

        // Assert
        composeTestRule.onNodeWithText("Add to Playlist").assertDoesNotExist()
    }
}
