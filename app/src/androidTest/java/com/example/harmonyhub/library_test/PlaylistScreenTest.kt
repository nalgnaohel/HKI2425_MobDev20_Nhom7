//package com.example.harmonyhub.library_test
//
//import androidx.compose.ui.test.*
//import androidx.compose.ui.test.junit4.createComposeRule
//import com.example.harmonyhub.ui.library.PlaylistsScreen
//import org.junit.Rule
//import org.junit.Test
//
//class PlaylistsScreenTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun test_PlaylistsScreen_InitialState() {
//        // Set up the composable for testing
//        composeTestRule.setContent {
//            PlaylistsScreen(
//                onBackButtonClicked = {},
//                onPlaylistClicked = {}
//            )
//        }
//
//        // Verify that the title text "Danh sách phát của tôi" is displayed
//        composeTestRule.onNodeWithText("Danh sách phát của tôi").assertIsDisplayed()
//
//        // Verify that the add button is displayed
//        composeTestRule.onNodeWithContentDescription("Add").assertIsDisplayed()
//
//        // Verify that the search field is displayed
//        composeTestRule.onNodeWithText("Tìm kiếm danh sách phát...").assertIsDisplayed()
//
//        // Verify that the "Xóa tất cả" text is displayed
//        composeTestRule.onNodeWithText("Xóa tất cả").assertIsDisplayed()
//
//        // Verify that at least one playlist item is displayed
//        composeTestRule.onNodeWithText("Playlist 1").assertIsDisplayed()
//    }
//
//    @Test
//    fun test_SearchFunctionality() {
//        composeTestRule.setContent {
//            PlaylistsScreen(
//                onBackButtonClicked = {},
//                onPlaylistClicked = {}
//            )
//        }
//
//        // Type into the search bar
//        composeTestRule.onNodeWithText("Tìm kiếm danh sách phát...").performTextInput("Playlist 1")
//
//        // Verify that other playlists are not displayed
//        composeTestRule.onNodeWithText("Playlist 2").assertDoesNotExist()
//    }
//
//    @Test
//    fun test_AddPlaylistDialog() {
//        composeTestRule.setContent {
//            PlaylistsScreen(
//                onBackButtonClicked = {},
//                onPlaylistClicked = {}
//            )
//        }
//
//        // Click on the add button to open the dialog
//        composeTestRule.onNodeWithContentDescription("Add").performClick()
//
//        // Verify that the dialog is displayed
//        composeTestRule.onNodeWithText("Tạo playlist mới").assertIsDisplayed()
//
//        // Type a new playlist name
//        composeTestRule.onNodeWithText("Tên playlist").performTextInput("New Playlist")
//
//        // Verify that the "OK" button is enabled
//        composeTestRule.onNodeWithText("OK").assertIsEnabled().performClick()
//
//    }
//
//    @Test
//    fun test_DismissAddPlaylistDialog() {
//        composeTestRule.setContent {
//            PlaylistsScreen(
//                onBackButtonClicked = {},
//                onPlaylistClicked = {}
//            )
//        }
//
//        // Click on the add button to open the dialog
//        composeTestRule.onNodeWithContentDescription("Add").performClick()
//
//        // Verify that the dialog is displayed
//        composeTestRule.onNodeWithText("Tạo playlist mới").assertIsDisplayed()
//
//        // Click the "Cancel" button
//        composeTestRule.onNodeWithText("Hủy").performClick()
//
//        // Verify that the dialog is dismissed
//        composeTestRule.onNodeWithText("Tạo playlist mới").assertDoesNotExist()
//    }
//}
