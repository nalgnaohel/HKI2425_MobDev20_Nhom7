package com.example.harmonyhub.library_test

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.library.SongList
import org.junit.Rule
import org.junit.Test

class SongListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummySongs = listOf(
        Song("1", "Song A", "Artist A", "image1.jpg", "url1"),
        Song("2", "Song B", "Artist B", "image2.jpg", "url2"),
        Song("3", "Song C", "Artist C", "image3.jpg", "url3")
    )

    @Test
    fun verify_ui_components_display() {
        composeTestRule.setContent {
            SongList(
                title = "Library",
                more = Icons.Default.MoreVert,
                songs = dummySongs,
                onBackButtonClicked = {}
            )
        }

        // Kiểm tra tiêu đề "Library"
        composeTestRule.onNodeWithText("Library").assertExists()

        // Kiểm tra thanh tìm kiếm có placeholder đúng
        composeTestRule.onNodeWithText("Tìm kiếm bài hát...").assertExists()

        // Kiểm tra danh sách bài hát hiển thị đầy đủ
        composeTestRule.onNodeWithText("Song A").assertExists()
        composeTestRule.onNodeWithText("Artist A").assertExists()

        composeTestRule.onNodeWithText("Song B").assertExists()
        composeTestRule.onNodeWithText("Artist B").assertExists()

        composeTestRule.onNodeWithText("Song C").assertExists()
        composeTestRule.onNodeWithText("Artist C").assertExists()
    }

    @Test
    fun search_functionality_works_correctly() {
        composeTestRule.setContent {
            SongList(
                title = "Library",
                more = Icons.Default.MoreVert,
                songs = dummySongs,
                onBackButtonClicked = {}
            )
        }

        // Nhập từ khóa tìm kiếm
        composeTestRule.onNodeWithText("Tìm kiếm bài hát...").performTextInput("Song B")

        composeTestRule.onNodeWithText("Song A").assertDoesNotExist()
        composeTestRule.onNodeWithText("Song C").assertDoesNotExist()

    }

    @Test
    fun clear_search_works_correctly() {
        composeTestRule.setContent {
            SongList(
                title = "Library",
                more = Icons.Default.MoreVert,
                songs = dummySongs,
                onBackButtonClicked = {}
            )
        }

        // Nhập từ khóa tìm kiếm
        composeTestRule.onNodeWithText("Tìm kiếm bài hát...").performTextInput("Song B")

        // Nhấn nút "Clear"
        composeTestRule.onNodeWithContentDescription("Clear").performClick()

        // Kiểm tra lại tất cả bài hát đều hiển thị
        composeTestRule.onNodeWithText("Song A").assertExists()
        composeTestRule.onNodeWithText("Song B").assertExists()
        composeTestRule.onNodeWithText("Song C").assertExists()
    }

    @Test
    fun back_button_triggers_callback() {
        var backClicked = false

        composeTestRule.setContent {
            SongList(
                title = "Library",
                more = Icons.Default.MoreVert,
                songs = dummySongs,
                onBackButtonClicked = { backClicked = true }
            )
        }

        // Nhấn nút back
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Kiểm tra callback đã được gọi
        assert(backClicked)
    }
}
