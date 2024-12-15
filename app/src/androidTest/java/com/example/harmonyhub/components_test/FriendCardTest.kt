package com.example.harmonyhub.components_test

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.harmonyhub.R
import com.example.harmonyhub.ui.components.Friend
import com.example.harmonyhub.ui.components.FriendCard
import com.example.harmonyhub.ui.theme.HarmonyHubTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FriendCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun friendCard_DisplaysFriendInfoCorrectly() {
        val friend = Friend(
            name = "John Doe",
            email = "john.doe@example.com",
            imageResId = R.drawable.ic_broken_image
        )

        composeTestRule.setContent {
            HarmonyHubTheme {
                FriendCard(
                    friend = friend,
                    screenType = "Friends",
                    onMoreClick = {},
                    onAcceptClick = {},
                    onRejectClick = {}
                )
            }
        }

        // Kiểm tra hiển thị tên và email
        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed()
        composeTestRule.onNodeWithText("john.doe@example.com").assertIsDisplayed()

        // Kiểm tra nút "More Options"
        composeTestRule.onNodeWithContentDescription("More Options").assertIsDisplayed()
    }

    @Test
    fun friendCard_ButtonsWorkCorrectly_AcceptReject() {
        val friend = Friend(
            name = "Jane Smith",
            email = "jane.smith@example.com",
            imageResId = R.drawable.ic_broken_image
        )
        var acceptClicked = false
        var rejectClicked = false

        composeTestRule.setContent {
            HarmonyHubTheme {
                FriendCard(
                    friend = friend,
                    screenType = "Requests",
                    onMoreClick = {},
                    onAcceptClick = { acceptClicked = true },
                    onRejectClick = { rejectClicked = true }
                )
            }
        }

        // Kiểm tra nút "Accept"
        composeTestRule.onNodeWithContentDescription("Accept").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Accept").performClick()
        assert(acceptClicked)

        // Kiểm tra nút "Reject"
        composeTestRule.onNodeWithContentDescription("Reject").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Reject").performClick()
        assert(rejectClicked)
    }

    @Test
    fun friendCard_HidesAcceptRejectButtons_WhenScreenTypeIsFriends() {
        val friend = Friend(
            name = "Emily Johnson",
            email = "emily.johnson@example.com",
            imageResId = R.drawable.ic_broken_image
        )

        composeTestRule.setContent {
            HarmonyHubTheme {
                FriendCard(
                    friend = friend,
                    screenType = "Friends",
                    onMoreClick = {},
                    onAcceptClick = {},
                    onRejectClick = {}
                )
            }
        }

        // Kiểm tra nút "More Options" có hiển thị
        composeTestRule.onNodeWithContentDescription("More Options").assertIsDisplayed()

        // Đảm bảo các nút "Accept" và "Reject" không xuất hiện
        composeTestRule.onNodeWithContentDescription("Accept").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("Reject").assertDoesNotExist()
    }
}
