package com.example.harmonyhub.search_test

import androidx.compose.ui.test.assertCountEquals
import com.example.harmonyhub.ui.search.SearchScreen
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.ui.components.Song

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Danh sách bài hát giả để sử dụng trong bài kiểm tra
    private val mockSongs = listOf(
        Song(
            id = "3ztP5O7dJSha2PG429eUCb", name = "Thiên Lý Ơi", artist = "Jack - J97",
            imageResId = "https://i.scdn.co/image/ab67616d00001e0233a31cc1175e787bfea17a65",
            url = "https://p.scdn.co/mp3-preview/8ede412058d9e95de0b4b3a735fe3c999498336c?cid=d8a5ed958d274c2e8ee717e6a4b0971d"
        ),
        Song(
            id = "7gSQxwCpEMjg8wb6Y3oqe7", name = "HÀO QUANG", artist = "ANH TRAI SAY HI",
            imageResId = "https://i.scdn.co/image/ab67616d00001e02901ea2601f4f8262474a75a1",
            url = "https://p.scdn.co/mp3-preview/05a875c428a96e884053a5bb500394e8503f8c18?cid=d8a5ed958d274c2e8ee717e6a4b0971d"
        ),
        Song(
            id = "7jLSThU5Kg1RWt19Leiaxm", name = "Chìm Sâu", artist = "RPT MCK, Trung Trần",
            imageResId = "https://i.scdn.co/image/ab67616d00001e02b315e8bb7ef5e57e9a25bb0f",
            url = "https://p.scdn.co/mp3-preview/0496b1c18c7653d9124a2f39e148ec3babcae737?cid=d8a5ed958d274c2e8ee717e6a4b0971d"
        ),
        Song(
            id = "41DlPJXKTCypXdf2eSqa03", name = "bình yên", artist = "Vũ., Binz",
            imageResId = "https://i.scdn.co/image/ab67616d00001e02be066d7fd668d8a0672b1245",
            url = "https://p.scdn.co/mp3-preview/90a1ce11d080ad8ec16e0b02a65f3d135c951d3c?cid=d8a5ed958d274c2e8ee717e6a4b0971d"
        ),
        Song(
            id = "5Bti0azlFhMattVY76qFr9", name = "Lạ lùng", artist = "Vũ.",
            imageResId = "https://i.scdn.co/image/ab67616d00001e02e93fadbe305ea32872b6dd11",
            url = "https://p.scdn.co/mp3-preview/7c8c36fc929c6c3f35188355e3bb37f35e4b87fd?cid=d8a5ed958d274c2e8ee717e6a4b0971d"
        ),
        Song(
            id = "7kpNUrBDYDoX6QKGzrBD1R", name = "Thôi Em Đừng Đi", artist = "RPT MCk, Trung Trần",
            imageResId = "https://i.scdn.co/image/ab67616d00001e02b315e8bb7ef5e57e9a25bb0f",
            url = "https://p.scdn.co/mp3-preview/69b768853fe8a3ef97adfc3f0a64d6f20b07e40c?cid=d8a5ed958d274c2e8ee717e6a4b0971d"
        )
    )

    @Before
    fun setup() {
        SongRepository.allSongs = mockSongs
    }

    @Test
    fun testSearchScreen_initialState() {
        // Thiết lập nội dung Compose với SearchScreen
        composeTestRule.setContent {
            SearchScreen(onSearchQueryChanged = {})
        }

        // Kiểm tra placeholder được hiển thị ban đầu
        composeTestRule.onNodeWithText("Tìm kiếm bài hát, nghệ sĩ...").assertIsDisplayed()

        // Kiểm tra tiêu đề 'Phát gần đây' được hiển thị
        composeTestRule.onNodeWithText("Phát gần đây").assertIsDisplayed()

        // Kiểm tra rằng tất cả các bài hát trong mockSongs được hiển thị
        mockSongs.forEach { song ->
            composeTestRule.onNodeWithText(song.name).assertIsDisplayed()
        }
    }

    @Test
    fun testSearchScreen_searchQuery() {
        composeTestRule.setContent {
            SearchScreen(onSearchQueryChanged = {})
        }

        // Nhập dữ liệu vào TextField
        composeTestRule.onNodeWithContentDescription("Search").performClick()
        composeTestRule.onNodeWithContentDescription("Search").performTextInput("Thiên Lý")

        // Kiểm tra tiêu đề thay đổi thành 'Kết quả tìm kiếm'
        composeTestRule.onNodeWithText("Kết quả tìm kiếm").assertIsDisplayed()

        composeTestRule.onNodeWithText("Thiên Lý Ơi")
            .assertIsDisplayed()
    }


    @Test
    fun testSearchScreen_clearQuery() {
        // Thiết lập nội dung Compose với SearchScreen
        composeTestRule.setContent {
            SearchScreen(onSearchQueryChanged = {})
        }

        // Nhập dữ liệu vào TextField
        composeTestRule.onNodeWithContentDescription("Search").performClick()
        composeTestRule.onNodeWithContentDescription("Search").performTextInput("Hello")

        // Kiểm tra nút Clear hiển thị
        composeTestRule.onNodeWithContentDescription("Clear").assertIsDisplayed()

        // Nhấn nút Clear
        composeTestRule.onNodeWithContentDescription("Clear").performClick()

        // Kiểm tra TextField trống sau khi xóa
        composeTestRule.onNodeWithText("Tìm kiếm bài hát, nghệ sĩ...").assertIsDisplayed()

        // Kiểm tra rằng tất cả các bài hát được hiển thị lại
        mockSongs.forEach { song ->
            composeTestRule.onNodeWithText(song.name).assertIsDisplayed()
        }
    }

}