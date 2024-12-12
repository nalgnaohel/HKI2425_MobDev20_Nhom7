package com.example.harmonyhub.library_test

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.harmonyhub.ui.library.AddSongToPlaylistScreen
import org.junit.Rule
import org.junit.Test

class AddSongToPlaylistScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAddSongToPlaylistScreen() {
        // Thiết lập nội dung của màn hình
        composeTestRule.setContent {
            AddSongToPlaylistScreen(
                playlistName = "My Playlist",
                onBackButtonClicked = { /* mock back button click */ }
            )
        }

        // Kiểm tra thanh tiêu đề
        composeTestRule.onNodeWithText("Thêm bài hát vào playlist").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()

        // Kiểm tra ô tìm kiếm
        composeTestRule.onNodeWithText("Tìm kiếm bài hát...").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Search").assertIsDisplayed()

        // Kiểm tra sự hiện diện của icon xóa trong ô tìm kiếm khi có từ khóa
        composeTestRule.onNodeWithText("Tìm kiếm bài hát...").performTextInput("Hello")
        composeTestRule.onNodeWithContentDescription("Clear").assertIsDisplayed()

        // Kiểm tra nhấn vào nút Clear
        composeTestRule.onNodeWithContentDescription("Clear").performClick()
        composeTestRule.onNodeWithText("Tìm kiếm bài hát...").assert(hasText(""))


        // Kiểm tra nhấn nút quay lại
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Kiểm tra nhấn nút Done
        composeTestRule.onNodeWithContentDescription("Done").performClick()
    }

    @Test
    fun testSearchFunctionality() {
        // Thiết lập nội dung của màn hình
        composeTestRule.setContent {
            AddSongToPlaylistScreen(
                playlistName = "My Playlist",
                onBackButtonClicked = { /* mock back button click */ }
            )
        }

        // Nhập từ khóa vào ô tìm kiếm
        composeTestRule.onNodeWithText("Tìm kiếm bài hát...").performTextInput("Song 1")

        // Kiểm tra danh sách không có bài hát nếu từ khóa không khớp
        composeTestRule.onNodeWithText("Song 999").assertDoesNotExist()
    }
}
