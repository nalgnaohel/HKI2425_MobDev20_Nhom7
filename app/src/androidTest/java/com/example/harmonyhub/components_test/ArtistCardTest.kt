package com.example.harmonyhub.components_test

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.harmonyhub.ui.components.ArtistsCard
import org.junit.Rule
import org.junit.Test


class ArtistsCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testArtistCardDisplaysCorrectly() {
        // Set up the content for the composable
        composeTestRule.setContent {
            ArtistsCard(
                artistName = "Artist Name",
                artistImg = "https://example.com/artist_image.jpg"
            )
        }

        // Kiểm tra xem ảnh có hiển thị không
        composeTestRule.onNodeWithContentDescription("Photo")
            .assertIsDisplayed()

        // Kiểm tra xem tên nghệ sĩ có hiển thị không
        composeTestRule.onNodeWithText("Artist Name")
            .assertIsDisplayed()

        // Kiểm tra xem tên nghệ sĩ có đúng nội dung không
        composeTestRule.onNodeWithText("Artist Name")
            .assert(hasText("Artist Name"))
    }

    @Test
    fun testArtistCardClickAction() {
        // Set up the content for the composable
        composeTestRule.setContent {
            ArtistsCard(
                artistName = "Artist Name",
                artistImg = "https://example.com/artist_image.jpg"
            )
        }

        // Kiểm tra rằng có thể nhấp vào card
        composeTestRule.onNodeWithText("Artist Name")
            .performClick()

        // Sau khi nhấn, bạn có thể kiểm tra hành động nào đó (ví dụ: chuyển hướng, thay đổi UI, v.v.)
        // Nếu bạn có bất kỳ hành động nào liên quan đến nhấp, bạn sẽ kiểm tra hành động đó ở đây.
        // Ví dụ: assert that a navigation event occurred or something else.
    }

    @Test
    fun testArtistCardImageLoading() {
        // Set up the content for the composable with a valid image
        composeTestRule.setContent {
            ArtistsCard(
                artistName = "Artist Name",
                artistImg = "https://example.com/artist_image.jpg"
            )
        }

        // Kiểm tra ảnh tải thành công
        composeTestRule.onNodeWithContentDescription("Photo")
            .assertIsDisplayed()

        // Nếu bạn cần kiểm tra ảnh bị lỗi hoặc placeholder, bạn có thể làm điều này:
        // composeTestRule.onNodeWithContentDescription("Photo")
        //     .assert(hasContentDescription("Broken Image"))
    }
}
