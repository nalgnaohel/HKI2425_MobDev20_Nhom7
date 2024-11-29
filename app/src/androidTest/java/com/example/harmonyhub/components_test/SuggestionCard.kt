package com.example.harmonyhub.components_test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.onNodeWithContentDescription
import com.example.harmonyhub.ui.components.SuggestionCard
import org.junit.Rule
import org.junit.Test

class SuggestionCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSuggestionCardDisplay() {
        val songName = "Shape of You"
        val artistName = "Ed Sheeran"
        val songId = "1"
        val songImg = "https://example.com/shape_of_you.jpg"

        composeTestRule.setContent {
            SuggestionCard(
                songName = songName,
                artistName = artistName,
                songId = songId,
                songImg = songImg
            )
        }

        // Kiểm tra xem tên bài hát và tên nghệ sĩ có hiển thị không
        composeTestRule.onNodeWithText(songName)
            .assertIsDisplayed()

        composeTestRule.onNodeWithText(artistName)
            .assertIsDisplayed()

        // Kiểm tra ảnh bài hát (kiểm tra mô tả ảnh)
        composeTestRule.onNodeWithContentDescription("Photo")
            .assertExists()
    }

    @Test
    fun testSuggestionCardClickAction() {
        val songName = "Shape of You"
        val artistName = "Ed Sheeran"
        val songId = "1"
        val songImg = "https://example.com/shape_of_you.jpg"

        composeTestRule.setContent {
            SuggestionCard(
                songName = songName,
                artistName = artistName,
                songId = songId,
                songImg = songImg
            )
        }

        // Mô phỏng nhấp vào card
        composeTestRule.onNodeWithText(songName)
            .performClick()

    }
}
