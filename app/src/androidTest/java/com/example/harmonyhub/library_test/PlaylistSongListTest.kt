//package com.example.harmonyhub.library_test
//
//import androidx.compose.ui.test.*
//import androidx.compose.ui.test.junit4.createComposeRule
//import com.example.harmonyhub.ui.library.PlaylistSongListScreen
//import com.example.harmonyhub.ui.theme.HarmonyHubTheme
//import org.junit.Rule
//import org.junit.Test
//
//class PlaylistSongListScreenTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun testPlaylistSongListScreenDisplaysCorrectly() {
//        // Arrange
//        val playlistName = "My Playlist"
//
//        composeTestRule.setContent {
//            HarmonyHubTheme {
//                PlaylistSongListScreen(
//                    playlistName = playlistName,
//                    onBackButtonClicked = {},
//                    onAddButtonClicked = {}
//                )
//            }
//        }
//
//        // Act & Assert
//        // Kiểm tra tiêu đề playlist
//        composeTestRule.onNodeWithText(playlistName).assertIsDisplayed()
//
//        // Kiểm tra ô tìm kiếm
//        composeTestRule.onNodeWithText("Tìm kiếm bài hát...").assertIsDisplayed()
//
//        // Kiểm tra các nút Play và Share
//        composeTestRule.onNodeWithContentDescription("Play").assertIsDisplayed()
//        composeTestRule.onNodeWithContentDescription("Share").assertIsDisplayed()
//
//    }
//
//    @Test
//    fun testSearchFunctionality() {
//        // Arrange
//        val playlistName = "My Playlist"
//        val searchQuery = "Song 1" // Example song title to search
//
//        composeTestRule.setContent {
//            HarmonyHubTheme {
//                PlaylistSongListScreen(
//                    playlistName = playlistName,
//                    onBackButtonClicked = {},
//                    onAddButtonClicked = {}
//                )
//            }
//        }
//
//        // Act: Nhập vào ô tìm kiếm
//        composeTestRule.onNodeWithText("Tìm kiếm bài hát...").performTextInput(searchQuery)
//    }
//
//    @Test
//    fun testClearSearchQuery() {
//        // Arrange
//        val playlistName = "My Playlist"
//        val searchQuery = "Song 1"
//
//        composeTestRule.setContent {
//            HarmonyHubTheme {
//                PlaylistSongListScreen(
//                    playlistName = playlistName,
//                    onBackButtonClicked = {},
//                    onAddButtonClicked = {}
//                )
//            }
//        }
//
//        // Act: Nhập vào ô tìm kiếm và sau đó xóa
//        composeTestRule.onNodeWithText("Tìm kiếm bài hát...").performTextInput(searchQuery)
//        composeTestRule.onNodeWithContentDescription("Clear").performClick()
//
//        // Assert: Kiểm tra ô tìm kiếm đã được làm sạch
//        composeTestRule.onNodeWithText("").assertExists()
//    }
//
//    @Test
//    fun testAddButtonFunctionality() {
//        // Arrange
//        val playlistName = "My Playlist"
//        composeTestRule.setContent {
//            HarmonyHubTheme {
//                PlaylistSongListScreen(
//                    playlistName = playlistName,
//                    onBackButtonClicked = {},
//                    onAddButtonClicked = {
//                        // Kiểm tra xem phương thức onAddButtonClicked có được gọi
//                        println("Add button clicked!")
//                    }
//                )
//            }
//        }
//
//        // Act: Nhấp vào nút thêm
//        composeTestRule.onNodeWithContentDescription("Add").performClick()
//
//        // Assert: Kiểm tra phương thức đã được gọi (xem trên log)
//        // Không có assert cụ thể vì đây chỉ là việc kiểm tra chức năng
//    }
//
//    @Test
//    fun testBackButtonFunctionality() {
//        // Arrange
//        val playlistName = "My Playlist"
//        composeTestRule.setContent {
//            HarmonyHubTheme {
//                PlaylistSongListScreen(
//                    playlistName = playlistName,
//                    onBackButtonClicked = {
//                        // Kiểm tra phương thức onBackButtonClicked được gọi
//                        println("Back button clicked!")
//                    },
//                    onAddButtonClicked = {}
//                )
//            }
//        }
//
//        // Act: Nhấp vào nút quay lại
//        composeTestRule.onNodeWithContentDescription("Back").performClick()
//
//        // Assert: Kiểm tra phương thức đã được gọi (xem trên log)
//    }
//}
