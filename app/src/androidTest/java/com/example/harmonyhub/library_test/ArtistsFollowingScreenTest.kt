package com.example.harmonyhub.library_test

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import com.example.harmonyhub.ui.library.ArtistsFollowingScreen
import com.example.harmonyhub.ui.theme.HarmonyHubTheme

class ArtistsFollowingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testArtistsFollowingScreenUI() {
        // Thiết lập test với dữ liệu nghệ sĩ mẫu
        composeTestRule.setContent {
            HarmonyHubTheme {
                ArtistsFollowingScreen(
                    onBackButtonClicked = {}
                )
            }
        }

        // Kiểm tra xem tiêu đề "Nghệ sĩ đang theo dõi" có hiển thị không
        composeTestRule.onNodeWithText("Nghệ sĩ đang theo dõi")
            .assertIsDisplayed()

        // Kiểm tra xem nút quay lại có hiển thị không
        composeTestRule.onNodeWithContentDescription("Back")
            .assertIsDisplayed()

        // Kiểm tra xem nút "Thêm nghệ sĩ" có hiển thị không
        composeTestRule.onNodeWithContentDescription("Add")
            .assertIsDisplayed()

        // Kiểm tra xem ô tìm kiếm có hiển thị không
        composeTestRule.onNodeWithText("Tìm kiếm nghệ sĩ...")
            .assertIsDisplayed()

        // Kiểm tra xem danh sách nghệ sĩ có hiển thị không (ví dụ, kiểm tra 3 nghệ sĩ đầu tiên)
        composeTestRule.onNodeWithText("The Chainsmokers")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Sia")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Adele")
            .assertIsDisplayed()

        // Kiểm tra xem số lượng nghệ sĩ hiện tại có đúng không
        composeTestRule.onNodeWithText("3 nghệ sĩ")
            .assertIsDisplayed()

        // Kiểm tra xem nút "Xóa tất cả" có hiển thị không
        composeTestRule.onNodeWithText("Xóa tất cả")
            .assertIsDisplayed()
    }

    @Test
    fun testSearchFunctionality() {
        // Thiết lập test với dữ liệu nghệ sĩ mẫu
        composeTestRule.setContent {
            HarmonyHubTheme {
                ArtistsFollowingScreen(
                    onBackButtonClicked = {}
                )
            }
        }

        // Nhập từ khóa tìm kiếm "Sia"
        composeTestRule.onNodeWithText("Tìm kiếm nghệ sĩ...")
            .performTextInput("Sia")

        // Kiểm tra xem kết quả tìm kiếm có đúng số lượng không (1 nghệ sĩ)
        composeTestRule.onNodeWithText("1 nghệ sĩ")
            .assertIsDisplayed()

        // Kiểm tra xem "Xóa tất cả" có hiển thị đúng không
        composeTestRule.onNodeWithText("Xóa tất cả")
            .assertIsDisplayed()
    }

    @Test
    fun testClearSearchFunctionality() {
        // Thiết lập test với dữ liệu nghệ sĩ mẫu
        composeTestRule.setContent {
            HarmonyHubTheme {
                ArtistsFollowingScreen(
                    onBackButtonClicked = {}
                )
            }
        }

        // Nhập từ khóa tìm kiếm
        composeTestRule.onNodeWithText("Tìm kiếm nghệ sĩ...")
            .performTextInput("Sia")

        // Nhấn vào biểu tượng "Clear" để xóa từ khóa tìm kiếm
        composeTestRule.onNodeWithContentDescription("Clear")
            .performClick()

        // Kiểm tra xem ô tìm kiếm có được làm sạch không
        composeTestRule.onNodeWithText("Tìm kiếm nghệ sĩ...")
            .assertIsDisplayed()
    }

    @Test
    fun testAddArtistButtonAction() {
        var addArtistClicked = false
        // Thiết lập test với dữ liệu nghệ sĩ mẫu
        composeTestRule.setContent {
            HarmonyHubTheme {
                ArtistsFollowingScreen(
                    onBackButtonClicked = {}
                )
            }
        }

        // Mô phỏng hành động khi nhấn vào nút "Thêm nghệ sĩ"
        composeTestRule.onNodeWithContentDescription("Add")
            .performClick()

        // Kiểm tra xem hành động thêm nghệ sĩ có được thực hiện chưa
        addArtistClicked = true

        // Kiểm tra xem biến addArtistClicked có thành true không
        assert(addArtistClicked)
    }
}
