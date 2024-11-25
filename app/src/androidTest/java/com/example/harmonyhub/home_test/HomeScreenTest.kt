package com.example.harmonyhub.home_test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.harmonyhub.ui.home.HomeScreen
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen() {
        composeTestRule.setContent {
            HomeScreen(
                onSearchButtonClicked = {},
                onPlayButtonClicked = {},
                onLibraryButtonClicked = {},
                onProfileButtonClicked = {},
                onLogoutButtonClicked = {},
                onSettingsButtonClicked = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Avatar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Username").assertIsDisplayed()
//        composeTestRule.onNodeWithTag("Username").assertTextEquals("Thomas")

        composeTestRule.onNodeWithTag("DrawerButton").assertIsDisplayed()
        composeTestRule.onNodeWithTag("DrawerButton").performClick()

        composeTestRule.onNodeWithContentDescription("Settings Icon").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Settings Icon").performClick()

    }


    @Test
    fun homeScreen_genreCards() {
        composeTestRule.setContent {
            HomeScreen(
                onSearchButtonClicked = {},
                onPlayButtonClicked = {},
                onLibraryButtonClicked = {},
                onProfileButtonClicked = {},
                onLogoutButtonClicked = {},
                onSettingsButtonClicked = {}
            )
        }

        composeTestRule.onNodeWithText("Thể loại").assertIsDisplayed()

        // Check if genre cards are displayed
    }

    @Test
    fun homeScreen_drawerOpensWhenProfileClicked() {

        composeTestRule.setContent {
            HomeScreen(
                onSearchButtonClicked = {},
                onPlayButtonClicked = {},
                onLibraryButtonClicked = {},
                onProfileButtonClicked = { },
                onLogoutButtonClicked = { },
                onSettingsButtonClicked = { }
            )
        }

        composeTestRule.onNodeWithTag("DrawerButton").assertExists()

        composeTestRule.onNodeWithTag("DrawerButton").performClick()

    }

}

