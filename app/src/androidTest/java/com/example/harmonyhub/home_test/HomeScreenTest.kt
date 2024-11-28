package com.example.harmonyhub.home_test

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.harmonyhub.data.network.AlbumOut
import com.example.harmonyhub.data.network.ArtistOut
import com.example.harmonyhub.data.network.ChartOut
import com.example.harmonyhub.data.network.ResponseHomeScreenData
import com.example.harmonyhub.ui.home.ErrorScreen
import com.example.harmonyhub.ui.home.LoadingScreen
import com.example.harmonyhub.ui.home.MainHomeScreen
import com.example.harmonyhub.ui.theme.HarmonyHubTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingScreen_isDisplayed() {
        composeTestRule.setContent {
            HarmonyHubTheme {
                LoadingScreen()
            }
        }
        composeTestRule
            .onNodeWithTag("Circular Progress Indicator")
            .assertExists()
    }

    @Test
    fun errorScreen_isDisplayed_and_refreshButtonWorks() {
        var refreshClicked = false

        composeTestRule.setContent {
            HarmonyHubTheme {
                ErrorScreen(onRefreshContent = { refreshClicked = true })
            }
        }

        // Kiểm tra rằng hình ảnh lỗi kết nối được hiển thị
        composeTestRule
            .onNodeWithTag("Error")
            .assertExists("Connection error image not displayed")

        // Nhấn nút refresh và kiểm tra lambda đã được gọi chưa
        composeTestRule
            .onNodeWithContentDescription("Refresh")
            .performClick()

        assert(refreshClicked)
    }

    @Test
    fun homeScreen_displaysUserName_andGenres() {
        composeTestRule.setContent {
            HarmonyHubTheme {
                MainHomeScreen(
                    onSearchButtonClicked = {},
                    onPlayButtonClicked = {},
                    onLibraryButtonClicked = {},
                    onProfileButtonClicked = {},
                    onLogoutButtonClicked = {},
                    onSettingsButtonClicked = {},
                    nameUser = "Test User",
                    resPopularItem = fakeResponseData() // Sử dụng dữ liệu giả lập
                )
            }
        }

        // Kiểm tra tên người dùng có hiển thị không
        composeTestRule
            .onNodeWithText("Test User")
            .assertIsDisplayed()

        // Kiểm tra xem tiêu đề "Thể loại" có hiển thị không
        composeTestRule
            .onNodeWithText("Thể loại")
            .assertIsDisplayed()

        // Kiểm tra "V-Pop" xuất hiện trong danh sách thể loại
        composeTestRule
            .onNodeWithText("V-Pop")
            .assertIsDisplayed()
    }
}

// Hàm tạo dữ liệu giả lập
private fun fakeResponseData(): ResponseHomeScreenData {
    return ResponseHomeScreenData(
        listPopularArtist = mutableListOf(
            ArtistOut(id = "1", name = "Sơn Tùng M-TP", image = "artist1.png"),
            ArtistOut(id = "2", name = "IU", image = "artist2.png")
        ),
        listPopularAlbums = mutableListOf(
            AlbumOut(id = "1", name = "Sky Tour", image = "album1.png", listArtist = emptyList())
        ),
        listChart = mutableListOf(
            ChartOut(id = "1", name = "Top Hits", image = "chart1.png")
        )
    )
}
