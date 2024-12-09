package com.example.harmonyhub.components_test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.harmonyhub.ui.components.AlbumCard
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun albumCard_displaysSongNameAndArtistsCorrectly() {
        val songName = "Shape of You"
        val albumImg = "https://example.com/sample_image.jpg"
        val id = "1"
        val listArtist = listOf("Ed Sheeran")

        composeTestRule.setContent {
            AlbumCard(
                songName = songName,
                albumImg = albumImg,
                id = id,
                listArtist = listArtist,
                onAlbumCardClick = {}
            )
        }

        // Kiểm tra sự tồn tại của AlbumCard thông qua TestTag
        composeTestRule
            .onNodeWithTag("AlbumCard")
            .assertExists()

        // Kiểm tra hiển thị songName và nghệ sĩ
        composeTestRule
            .onNodeWithText(songName)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Ed Sheeran")
            .assertIsDisplayed()
    }


    @Test
    fun albumCard_handlesMultipleArtistsCorrectly() {
        val songName = "Chìm Sâu"
        val albumImg = "https://example.com/sample_image2.jpg"
        val id = "2"
        val listArtist = listOf("RPT MCK", "Trung Trần")

        composeTestRule.setContent {
            AlbumCard(
                songName = songName,
                albumImg = albumImg,
                id = id,
                listArtist = listArtist,
                onAlbumCardClick = {}
            )
        }

        // Kiểm tra danh sách nghệ sĩ ghép đúng
        composeTestRule
            .onNodeWithText("RPT MCK, Trung Trần")
            .assertIsDisplayed()
    }

}