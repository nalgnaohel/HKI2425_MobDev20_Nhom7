package com.example.harmonyhub.home_test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.harmonyhub.ui.home.ErrorScreen
import com.example.harmonyhub.ui.home.LoadingScreen
import com.example.harmonyhub.ui.theme.HarmonyHubTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingScreen_isDisplayed() {
        composeTestRule.setContent {
            HarmonyHubTheme {
                LoadingScreen()
            }
        }

        composeTestRule.onNodeWithTag("Circular Progress Indicator")
            .assertIsDisplayed()
    }

    @Test
    fun errorScreen_isDisplayed_andRefreshButtonWorks() {
        var refreshClicked = false

        composeTestRule.setContent {
            HarmonyHubTheme {
                ErrorScreen(onRefreshContent = { refreshClicked = true })
            }
        }

        // Kiểm tra màn hình lỗi hiển thị
        composeTestRule.onNodeWithTag("Error")
            .assertIsDisplayed()

        // Nhấn nút refresh và kiểm tra sự kiện
        composeTestRule.onNodeWithContentDescription("Refresh")
            .performClick()

        assert(refreshClicked)
    }
}

