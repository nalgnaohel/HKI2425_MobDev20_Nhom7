package com.example.harmonyhub.profile_test

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.harmonyhub.MainActivity
import com.example.harmonyhub.domain.repository.UserDataRepo
import com.example.harmonyhub.presentation.viewmodel.UserDataViewModel
import com.example.harmonyhub.ui.profile.ProfileScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testProfileScreen() {
        // Thiết lập UI cho màn hình Profile
        composeTestRule.setContent {
            ProfileScreen(
                onBackButtonClicked = {},
                onFriendsButtonClicked = {},
                userDataViewModel = hiltViewModel()
            )
        }

        // Kiểm tra các phần tử UI
        composeTestRule.onNodeWithText("Thông tin cá nhân").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Người theo dõi").assertIsDisplayed()
        composeTestRule.onNodeWithText("Đang theo dõi").assertIsDisplayed()

        // Kiểm tra hình ảnh đại diện có hiển thị
        composeTestRule.onNodeWithContentDescription("Profile").assertIsDisplayed()

        // Kiểm tra chức năng mở hộp thoại thay đổi ảnh
        composeTestRule.onNodeWithContentDescription("Profile").performClick()
        composeTestRule.onNodeWithText("Thay đổi ảnh đại diện").assertIsDisplayed()
    }

    @Test
    fun testBackButton() {
        // Thiết lập UI cho màn hình Profile
        composeTestRule.setContent {
            ProfileScreen(
                onBackButtonClicked = { /* mock back action */ },
                onFriendsButtonClicked = {},
                userDataViewModel = hiltViewModel()
            )
        }

        // Kiểm tra chức năng nhấn nút quay lại
        composeTestRule.onNodeWithContentDescription("Back").performClick()
    }

    @Test
    fun testImageChangeDialog() {
        // Thiết lập UI cho màn hình Profile
        composeTestRule.setContent {
            ProfileScreen(
                onBackButtonClicked = {},
                onFriendsButtonClicked = {},
                userDataViewModel = hiltViewModel()
            )
        }

        // Mở hộp thoại thay đổi ảnh
        composeTestRule.onNodeWithContentDescription("Profile").performClick()

        // Kiểm tra rằng hộp thoại thay đổi ảnh đã hiển thị
        composeTestRule.onNodeWithText("Thay đổi ảnh đại diện").assertIsDisplayed()

        // Kiểm tra các nút trong hộp thoại
        composeTestRule.onNodeWithText("Lưu").assertIsDisplayed()
        composeTestRule.onNodeWithText("Hủy").assertIsDisplayed()
    }
}


