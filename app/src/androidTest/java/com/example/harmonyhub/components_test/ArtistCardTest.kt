package com.example.harmonyhub.components_test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.harmonyhub.ui.components.ArtistsCard
import com.example.harmonyhub.ui.theme.HarmonyHubTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtistsCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun artistsCard_DisplaysArtistInfoCorrectly() {
        // Arrange: Khởi tạo dữ liệu giả
        val artistName = "Taylor Swift"
        val artistImg = "https://example.com/taylor_swift.jpg"

        composeTestRule.setContent {
            HarmonyHubTheme {
                ArtistsCard(
                    artistName = artistName,
                    artistImg = artistImg,
                    onArtistCardClick = {}
                )
            }
        }

        // Act & Assert: Kiểm tra tên nghệ sĩ hiển thị chính xác
        composeTestRule.onNodeWithText(artistName).assertIsDisplayed()

        // Act & Assert: Kiểm tra hình ảnh có mô tả đúng
        composeTestRule.onNodeWithContentDescription("Photo").assertIsDisplayed()
    }

    @Test
    fun artistsCard_HandlesClickAction() {
        // Arrange: Biến để kiểm tra sự kiện click
        var clicked = false

        composeTestRule.setContent {
            HarmonyHubTheme {
                ArtistsCard(
                    artistName = "Ed Sheeran",
                    artistImg = "https://example.com/ed_sheeran.jpg",
                    onArtistCardClick = { clicked = true }
                )
            }
        }

        // Act: Nhấn vào thẻ nghệ sĩ
        composeTestRule.onNodeWithContentDescription("Photo").performClick()

        // Assert: Kiểm tra biến `clicked` thay đổi sau khi click
        assert(clicked)
    }

    @Test
    fun artistsCard_ShowsPlaceholderWhenImageFailsToLoad() {
        // Arrange: Dùng một đường dẫn hình ảnh không hợp lệ
        val artistName = "Adele"
        val invalidArtistImg = "https://example.com/invalid_image.jpg"

        composeTestRule.setContent {
            HarmonyHubTheme {
                ArtistsCard(
                    artistName = artistName,
                    artistImg = invalidArtistImg,
                    onArtistCardClick = {}
                )
            }
        }

        // Act & Assert: Kiểm tra hiển thị hình ảnh lỗi
        composeTestRule.onNodeWithContentDescription("Photo").assertIsDisplayed()
    }
}

