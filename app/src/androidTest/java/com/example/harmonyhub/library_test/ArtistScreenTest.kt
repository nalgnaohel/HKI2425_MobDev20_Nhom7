package com.example.harmonyhub.library_test

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.harmonyhub.ui.library.ArtistScreen
import com.example.harmonyhub.ui.theme.HarmonyHubTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//@RunWith(AndroidJUnit4::class)
//class ArtistScreenTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun artistScreen_displaysArtistName_andControls() {
//        composeTestRule.setContent {
//            HarmonyHubTheme {
//                ArtistScreen(
//                    artist = "Jack - J97",
//                    onSongClick = {}
//                )
//            }
//        }
//
//        // Kiểm tra tên nghệ sĩ được hiển thị
//        composeTestRule
//            .onNodeWithTag("Song Name")
//            .assertIsDisplayed()
//
//        // Kiểm tra nút "Follow" được hiển thị
//        composeTestRule
//            .onNodeWithText("Follow")
//            .assertIsDisplayed()
//
//        // Kiểm tra nút "Play" được hiển thị
//        composeTestRule
//            .onNodeWithContentDescription("Play")
//            .assertIsDisplayed()
//
//        // Kiểm tra nút "Share" được hiển thị
//        composeTestRule
//            .onNodeWithContentDescription("Share")
//            .assertIsDisplayed()
//    }
//
//    @Test
//    fun artistScreen_displaysSongsList() {
//        composeTestRule.setContent {
//            HarmonyHubTheme {
//                ArtistScreen(
//                    artist = "Jack - J97",
//                    onSongClick = {}
//                )
//            }
//        }
//
//        // Kiểm tra tiêu đề "Popular releases" được hiển thị
//        composeTestRule
//            .onNodeWithTag("Song List")
//            .assertIsDisplayed()
//
//        // Kiểm tra danh sách bài hát được hiển thị
//        val songTitles = listOf("Thiên Lý Ơi") // Mock tên bài hát
//        songTitles.forEach { title ->
//            composeTestRule
//                .onNodeWithText(title)
//                .assertIsDisplayed()
//        }
//    }
//
//    @Test
//    fun artistScreen_displaysNoSongsMessage_whenEmpty() {
//        composeTestRule.setContent {
//            HarmonyHubTheme {
//                ArtistScreen(
//                    artist = "Unknown Artist",
//                    onSongClick = {}
//                )
//            }
//        }
//
//        // Kiểm tra thông báo "No songs available" được hiển thị khi không có bài hát
//        composeTestRule
//            .onNodeWithText("No songs available")
//            .assertIsDisplayed()
//    }
//}
