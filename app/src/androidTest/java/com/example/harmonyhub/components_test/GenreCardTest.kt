package com.example.harmonyhub.components_test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.harmonyhub.ui.components.GenreCard
import org.junit.Rule
import org.junit.Test

class GenreCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testGenreCardDisplay() {
        // Thiết lập nội dung cho composable GenreCard
        composeTestRule.setContent {
            GenreCard(genre = "Pop")
        }

        // Kiểm tra xem GenreCard có hiển thị đúng không (bằng cách tìm text trên card)
        composeTestRule.onNodeWithText("Pop")
            .assertIsDisplayed()
    }

    @Test
    fun testGenreCardClickAction() {
        // Thiết lập nội dung cho composable GenreCard
        composeTestRule.setContent {
            GenreCard(genre = "Pop")
        }

        // Kiểm tra hành động nhấp vào card
        composeTestRule.onNodeWithText("Pop")
            .performClick()
    }
}
