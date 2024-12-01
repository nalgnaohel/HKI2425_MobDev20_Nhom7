//package com.example.harmonyhub.components_test
//
//import androidx.compose.ui.test.assertIsDisplayed
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.onNodeWithText
//import androidx.compose.ui.test.performClick
//import com.example.harmonyhub.ui.components.PlaylistCard
//import com.example.harmonyhub.ui.components.Playlist
//import org.junit.Rule
//import org.junit.Test
//
//class PlaylistCardTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun testPlaylistCardDisplay() {
//        // Thiết lập nội dung cho composable PlaylistCard
//        val playlist = Playlist(name = "Top Hits", img = com.example.harmonyhub.R.drawable.v)
//        composeTestRule.setContent {
//            PlaylistCard(playlist = playlist, onClick = {})
//        }
//
//        // Kiểm tra xem tên playlist có hiển thị trên card không
//        composeTestRule.onNodeWithText("Top Hits")
//            .assertIsDisplayed()
//    }
//
//    @Test
//    fun testPlaylistCardClickAction() {
//        // Thiết lập nội dung cho composable PlaylistCard
//        val playlist = Playlist(name = "Top Hits", img = com.example.harmonyhub.R.drawable.v)
//        var clicked = false
//        composeTestRule.setContent {
//            PlaylistCard(playlist = playlist, onClick = { clicked = true })
//        }
//
//        // Mô phỏng hành động nhấp vào card
//        composeTestRule.onNodeWithText("Top Hits")
//            .performClick()
//
//        // Kiểm tra xem hành động nhấp vào có được thực thi không
//        assert(clicked)
//    }
//}
