package com.example.harmonyhub.home_test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test
import com.example.harmonyhub.data.network.ResponseHomeScreenData
import com.example.harmonyhub.data.network.ArtistOut
import com.example.harmonyhub.data.network.AlbumOut
import com.example.harmonyhub.data.network.ChartOut
import com.example.harmonyhub.ui.home.MainHomeScreen
import com.example.harmonyhub.ui.home.HomeUIState
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class MainHomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mainHomeScreen_displaysUsername() {
        val response = ResponseHomeScreenData(
            listPopularArtist = mutableListOf(ArtistOut("1", "Artist1", "image1")),
            listPopularAlbums = mutableListOf(AlbumOut("1", "Album1", "image1", listOf("Artist1"))),
            listChart = mutableListOf(ChartOut("1", "Chart1", "image1"))
        )

        composeTestRule.setContent {
            MainHomeScreen(
                onSearchButtonClicked = {},
                onPlayButtonClicked = {},
                onLibraryButtonClicked = {},
                onProfileButtonClicked = {},
                onLogoutButtonClicked = {},
                onSettingsButtonClicked = {},
                nameUser = "Test User",
                resPopularItem = response
            )
        }

        // Kiểm tra tên người dùng có hiển thị
        composeTestRule
            .onNodeWithText("Test User")
            .assertIsDisplayed()
    }

    @Test
    fun mainHomeScreen_displaysSections() {
        val response = ResponseHomeScreenData(
            listPopularArtist = mutableListOf(ArtistOut("1", "Artist1", "image1")),
            listPopularAlbums = mutableListOf(AlbumOut("1", "Album1", "image1", listOf("Artist1"))),
            listChart = mutableListOf(ChartOut("1", "Chart1", "image1"))
        )

        composeTestRule.setContent {
            MainHomeScreen(
                onSearchButtonClicked = {},
                onPlayButtonClicked = {},
                onLibraryButtonClicked = {},
                onProfileButtonClicked = {},
                onLogoutButtonClicked = {},
                onSettingsButtonClicked = {},
                nameUser = "Test User",
                resPopularItem = response
            )
        }

        // Kiểm tra các phần "Thể loại", "Nghệ sĩ phổ biến", "Album phổ biến", "Đề xuất cho bạn", "Bảng xếp hạng"
        composeTestRule
            .onNodeWithText("Thể loại")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Nghệ sĩ phổ biến")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Album phổ biến")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Đề xuất cho bạn")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Bảng xếp hạng")
            .assertIsDisplayed()
    }

    @Test
    fun mainHomeScreen_displaysGenres() {
        val response = ResponseHomeScreenData(
            listPopularArtist = mutableListOf(ArtistOut("1", "Artist1", "image1")),
            listPopularAlbums = mutableListOf(AlbumOut("1", "Album1", "image1", listOf("Artist1"))),
            listChart = mutableListOf(ChartOut("1", "Chart1", "image1"))
        )

        composeTestRule.setContent {
            MainHomeScreen(
                onSearchButtonClicked = {},
                onPlayButtonClicked = {},
                onLibraryButtonClicked = {},
                onProfileButtonClicked = {},
                onLogoutButtonClicked = {},
                onSettingsButtonClicked = {},
                nameUser = "Test User",
                resPopularItem = response
            )
        }

        // Kiểm tra các genre có hiển thị
        composeTestRule
            .onNodeWithText("V-Pop")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("K-Pop")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("R&B")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Hip Hop")
            .assertIsDisplayed()
    }

    @Test
    fun mainHomeScreen_displaysAlbums() {
        val response = ResponseHomeScreenData(
            listPopularArtist = mutableListOf(ArtistOut("1", "Artist1", "image1")),
            listPopularAlbums = mutableListOf(AlbumOut("1", "Album1", "image1", listOf("Artist1"))),
            listChart = mutableListOf(ChartOut("1", "Chart1", "image1"))
        )

        composeTestRule.setContent {
            MainHomeScreen(
                onSearchButtonClicked = {},
                onPlayButtonClicked = {},
                onLibraryButtonClicked = {},
                onProfileButtonClicked = {},
                onLogoutButtonClicked = {},
                onSettingsButtonClicked = {},
                nameUser = "Test User",
                resPopularItem = response
            )
        }

        // Kiểm tra tên album "Album1" hiển thị
        composeTestRule
            .onNodeWithText("Album1")
            .assertIsDisplayed()
    }

    @Test
    fun mainHomeScreen_displaysCharts() {
        val response = ResponseHomeScreenData(
            listPopularArtist = mutableListOf(ArtistOut("1", "Artist1", "image1")),
            listPopularAlbums = mutableListOf(AlbumOut("1", "Album1", "image1", listOf("Artist1"))),
            listChart = mutableListOf(ChartOut("1", "Chart1", "image1"))
        )

        composeTestRule.setContent {
            MainHomeScreen(
                onSearchButtonClicked = {},
                onPlayButtonClicked = {},
                onLibraryButtonClicked = {},
                onProfileButtonClicked = {},
                onLogoutButtonClicked = {},
                onSettingsButtonClicked = {},
                nameUser = "Test User",
                resPopularItem = response
            )
        }

        // Kiểm tra chart tên "Chart1" hiển thị
        composeTestRule
            .onNodeWithText("Chart1")
            .assertIsDisplayed()
    }
}





