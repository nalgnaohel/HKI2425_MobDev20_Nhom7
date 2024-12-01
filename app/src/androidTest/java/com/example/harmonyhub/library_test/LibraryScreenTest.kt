package com.example.harmonyhub.library_test

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.ui.library.LibraryScreen
import com.example.harmonyhub.ui.theme.HarmonyHubTheme

class LibraryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

//    @Test
//    fun testLibraryScreenDisplaysCorrectly() {
//        composeTestRule.setContent {
//            HarmonyHubTheme {
//                LibraryScreen(
//                    onPlaySongClicked = {},
//                    onProfileButtonClicked = {},
//                    onViewAllRecentCLicked = {},
//                    onFavoriteButtonClicked = {},
//                    onDownloadButtonClicked = {},
//                    onPlaylistButtonClicked = {},
//                    onArtistsFollowingButtonClicked = {},
//                    onLogoutButtonClicked = {},
//                    onSettingsButtonClicked = {}
//                )
//            }
//        }
//
//        // Act & Assert
//        // Kiểm tra tiêu đề màn hình "Thư viện"
//        composeTestRule.onNodeWithText("Thư viện").assertIsDisplayed()
//
//        // Kiểm tra "Xem tất cả" có hiển thị không
//        composeTestRule.onNodeWithText("Xem tất cả").assertIsDisplayed()
//
//        // Kiểm tra danh sách các thẻ LibraryCard
//        composeTestRule.onNodeWithText("Đã thích").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Tải xuống").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Playlists").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Nghệ sĩ").assertIsDisplayed()
//
//        // Kiểm tra xem có đúng 5 bài hát được hiển thị
//        composeTestRule.onAllNodesWithTag("SongCard").assertCountEquals(5)
//    }

//    @Test
//    fun testClickViewAllRecent() {
//        // Arrange
//        composeTestRule.setContent {
//            HarmonyHubTheme {
//                LibraryScreen(
//                    onPlaySongClicked = {},
//                    onProfileButtonClicked = {},
//                    onViewAllRecentCLicked = {
//                        // Kiểm tra xem phương thức có được gọi khi nhấp vào "Xem tất cả"
//                        println("Xem tất cả đã được nhấn!")
//                    },
//                    onFavoriteButtonClicked = {},
//                    onDownloadButtonClicked = {},
//                    onPlaylistButtonClicked = {},
//                    onArtistsFollowingButtonClicked = {},
//                    onLogoutButtonClicked = {},
//                    onSettingsButtonClicked = {}
//                )
//            }
//        }
//
//        // Act
//        // Nhấp vào "Xem tất cả"
//        composeTestRule.onNodeWithText("Xem tất cả").performClick()
//
//    }

//    @Test
//    fun testClickSongCard() {
//        // Arrange
//        val songId = SongRepository.allSongs.first().id
//        composeTestRule.setContent {
//            HarmonyHubTheme {
//                LibraryScreen(
//                    onPlaySongClicked = { id ->
//                        // Kiểm tra id bài hát đã được gửi khi nhấp vào
//                        assert(id == songId)
//                    },
//                    onProfileButtonClicked = {},
//                    onViewAllRecentCLicked = {},
//                    onFavoriteButtonClicked = {},
//                    onDownloadButtonClicked = {},
//                    onPlaylistButtonClicked = {},
//                    onArtistsFollowingButtonClicked = {},
//                    onLogoutButtonClicked = {},
//                    onSettingsButtonClicked = {}
//                )
//            }
//        }
//
//        // Act
//        // Nhấp vào card của bài hát
//        composeTestRule.onNodeWithTag("SongCard").performClick()
//
//        // Assert: Đã gọi onPlaySongClicked với đúng ID bài hát
//        // (Kiểm tra assert trong phương thức xử lý đã được gọi)
//    }
}
