package com.example.harmonyhub.components_test

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.harmonyhub.ui.components.AppScaffoldWithDrawer
import org.junit.Rule
import org.junit.Test

class NavigationDrawerTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDrawerBehavior() {
        var profileClicked = false
        var settingsClicked = false
        var logoutClicked = false

        composeTestRule.setContent {
            AppScaffoldWithDrawer(
                onProfileClicked = { profileClicked = true },
                onSettingsClicked = { settingsClicked = true },
                onLogoutClicked = { logoutClicked = true },
                content = { onOpenDrawer ->
                    TextButton(onClick = onOpenDrawer) {
                        Text("Mở Drawer")
                    }
                }
            )
        }

        // Kiểm tra Drawer mở khi nhấn "Mở Drawer"
        composeTestRule.onNodeWithText("Mở Drawer").performClick()
        composeTestRule.onNodeWithText("Hồ sơ").assertIsDisplayed()

        // Kiểm tra callback khi nhấn "Hồ sơ"
        composeTestRule.onNodeWithText("Hồ sơ").performClick()
        assert(profileClicked)

        // Kiểm tra callback khi nhấn "Cài đặt"
        composeTestRule.onNodeWithText("Cài đặt").performClick()
        assert(settingsClicked)

        // Kiểm tra callback khi nhấn "Đăng xuất"
        composeTestRule.onNodeWithText("Đăng xuất").performClick()
        assert(logoutClicked)
    }
}

