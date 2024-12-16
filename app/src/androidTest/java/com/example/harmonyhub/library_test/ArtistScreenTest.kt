package com.example.harmonyhub.library_test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.harmonyhub.ui.library.ArtistScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtistScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun artistScreen_backButtonWorks() {
        var backClicked = false
        composeTestRule.setContent {
            ArtistScreen(
                myArtist = "Kendrick Lamar",
                onSongClick = {},
                onBackButtonClicked = { backClicked = true },
                onAddToPlaylistClicked = {},
                onAddToFavoriteClicked = {},
                onShareClicked = {},
                onDownloadClicked = {},
                onAlbumClicked = {}
            )
        }

        // Kiểm tra nút Back hoạt động
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(backClicked)
    }

    @Test
    fun artistScreen_followButtonWorks() {
        composeTestRule.setContent {
            ArtistScreen(
                myArtist = "Kendrick Lamar",
                onSongClick = {},
                onBackButtonClicked = {},
                onAddToPlaylistClicked = {},
                onAddToFavoriteClicked = {},
                onShareClicked = {},
                onDownloadClicked = {},
                onAlbumClicked = {}
            )
        }

        // Kiểm tra nút Follow hiển thị
        composeTestRule.onNodeWithText("Follow").assertIsDisplayed()

        // Click nút Follow
        composeTestRule.onNodeWithText("Follow").performClick()
    }


    @Test
    fun artistScreen_displaysPopularReleasesAndAlbums() {
        composeTestRule.setContent {
            ArtistScreen(
                myArtist = "Kendrick Lamar",
                onSongClick = {},
                onBackButtonClicked = {},
                onAddToPlaylistClicked = {},
                onAddToFavoriteClicked = {},
                onShareClicked = {},
                onDownloadClicked = {},
                onAlbumClicked = {}
            )
        }

        // Kiểm tra danh sách "Popular releases"
        composeTestRule.onNodeWithText("Popular releases").assertIsDisplayed()

        // Kiểm tra một bài hát cụ thể hiển thị
        composeTestRule.onNodeWithText("wacced out murals").assertIsDisplayed()

        // Kiểm tra danh sách "Albums"
        composeTestRule.onNodeWithText("Albums").assertIsDisplayed()
    }
}
