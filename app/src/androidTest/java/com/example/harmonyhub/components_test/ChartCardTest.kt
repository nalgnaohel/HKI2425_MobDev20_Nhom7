package com.example.harmonyhub.components_test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.onNodeWithContentDescription
import com.example.harmonyhub.ui.components.ChartCard
import org.junit.Rule
import org.junit.Test

class ChartCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testChartCardDisplay() {
        val chartImg = "https://example.com/chart_image.jpg"
        val chartName = "Top 100 Chart"
        val chartId = "1"

        composeTestRule.setContent {
            ChartCard(
                chartImg = chartImg,
                chartName = chartName,
                chartId = chartId,
                onChartClicked = {}
            )
        }

        // Kiểm tra xem ảnh có được tải và hiển thị đúng không
        composeTestRule.onNodeWithContentDescription("Photo")
            .assertExists()  // Kiểm tra nếu ảnh hiển thị

    }

}
