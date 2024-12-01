package com.example.harmonyhub.components_test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.onNodeWithContentDescription
import com.example.harmonyhub.ui.components.SongCard
import com.example.harmonyhub.ui.components.Song
import org.junit.Rule
import org.junit.Test
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert

//class SongCardTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun testSongCardDisplay() {
//        // Thiết lập nội dung cho composable SongCard
//        val song = Song(
//            id = "1",
//            name = "Shape of You",
//            artist = "Ed Sheeran",
//            imageResId = "https://example.com/shape_of_you.jpg",
//            url = "https://example.com/song_url"
//        )
//
//        composeTestRule.setContent {
//            SongCard(
//                song = song,
//                more = Icons.Default.MoreVert,
//                onSongClick = {}
//            )
//        }
//
//        // Kiểm tra tên bài hát và tên nghệ sĩ có hiển thị không
//        composeTestRule.onNodeWithText("Shape of You")
//            .assertIsDisplayed()
//
//        composeTestRule.onNodeWithText("Ed Sheeran")
//            .assertIsDisplayed()
//
//        // Kiểm tra ảnh bài hát (mặc dù đây là ảnh tải động, bạn có thể kiểm tra mô tả của ảnh)
//        composeTestRule.onNodeWithContentDescription("Photo")
//            .assertExists()
//    }
//
//    @Test
//    fun testSongCardClickAction() {
//        // Thiết lập nội dung cho composable SongCard
//        val song = Song(
//            id = "1",
//            name = "Shape of You",
//            artist = "Ed Sheeran",
//            imageResId = "https://example.com/shape_of_you.jpg",
//            url = "https://example.com/song_url"
//        )
//        var clicked = false
//        composeTestRule.setContent {
//            SongCard(
//                song = song,
//                more = Icons.Default.MoreVert,
//                onSongClick = { id ->
//                    if (id == song.id) {
//                        clicked = true
//                    }
//                }
//            )
//        }
//
//        // Mô phỏng nhấp vào song card
//        composeTestRule.onNodeWithText("Shape of You")
//            .performClick()
//
//        // Kiểm tra xem bài hát đã được nhấp chưa
//        assert(clicked)
//    }
//
//    @Test
//    fun testSongCardMoreOptions() {
//        // Thiết lập nội dung cho composable SongCard
//        val song = Song(
//            id = "1",
//            name = "Shape of You",
//            artist = "Ed Sheeran",
//            imageResId = "https://example.com/shape_of_you.jpg",
//            url = "https://example.com/song_url"
//        )
//        val moreClicked = false
//        composeTestRule.setContent {
//            SongCard(
//                song = song,
//                more = Icons.Default.MoreVert,
//                onSongClick = {},
//            )
//        }
//
//        // Mô phỏng nhấp vào biểu tượng "More"
//        composeTestRule.onNodeWithContentDescription("More Options")
//            .performClick()
//
//    }
//}
