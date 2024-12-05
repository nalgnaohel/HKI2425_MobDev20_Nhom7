package com.example.harmonyhub.play_test

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.play.PlayScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PlayScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        // Chuẩn bị dữ liệu test
        SongRepository.allSongs = listOf(
            Song(
                id = "3ztP5O7dJSha2PG429eUCb",
                name = "Thiên Lý Ơi",
                artist = "Jack - J97",
                imageResId = "https://i.scdn.co/image/ab67616d00001e0233a31cc1175e787bfea17a65",
                url = "https://p.scdn.co/mp3-preview/8ede412058d9e95de0b4b3a735fe3c999498336c?cid=d8a5ed958d274c2e8ee717e6a4b0971d"
            ),
            Song(
                id = "7gSQxwCpEMjg8wb6Y3oqe7",
                name = "HÀO QUANG",
                artist = "ANH TRAI SAY HI",
                imageResId = "https://i.scdn.co/image/ab67616d00001e02901ea2601f4f8262474a75a1",
                url = "https://p.scdn.co/mp3-preview/05a875c428a96e884053a5bb500394e8503f8c18?cid=d8a5ed958d274c2e8ee717e6a4b0971d"
            )
        )
    }

    @Test
    fun playScreen_displaysCurrentSong() {
        composeTestRule.setContent {
            PlayScreen(onBackButtonClicked = {})
        }

        // Kiểm tra nếu tên bài hát đầu tiên được hiển thị
        composeTestRule
            .onNodeWithText("Thiên Lý Ơi")
            .assertIsDisplayed()

        // Kiểm tra tên nghệ sĩ
        composeTestRule
            .onNodeWithText("Jack - J97")
            .assertIsDisplayed()
    }


    @Test
    fun playScreen_playPauseTogglesCorrectly() {
        composeTestRule.setContent {
            PlayScreen(onBackButtonClicked = {})
        }

        // Ấn nút Play/Pause
        composeTestRule
            .onNodeWithContentDescription("Play/Pause")
            .performClick()

        // Kiểm tra trạng thái thay đổi (ví dụ: trạng thái nút có thể thay đổi thành Pause)
        composeTestRule
            .onNodeWithContentDescription("Play/Pause")
            .assertIsDisplayed()
    }

    @Test
    fun playScreen_navigateToNextSong() {
        composeTestRule.setContent {
            PlayScreen(onBackButtonClicked = {})
        }

        // Ấn nút Next
        composeTestRule
            .onNodeWithContentDescription("Next")
            .performClick()

        // Kiểm tra nếu bài hát tiếp theo được hiển thị
        composeTestRule
            .onNodeWithText("HÀO QUANG")
            .assertIsDisplayed()
    }

    @Test
    fun playScreen_navigateToPreviousSong() {
        composeTestRule.setContent {
            PlayScreen(onBackButtonClicked = {})
        }

        // Ấn nút Previous
        composeTestRule
            .onNodeWithContentDescription("Previous")
            .performClick()

        // Kiểm tra nếu bài hát cuối cùng được hiển thị (vòng lặp danh sách)
        composeTestRule
            .onNodeWithText("HÀO QUANG")
            .assertIsDisplayed()
    }
}
