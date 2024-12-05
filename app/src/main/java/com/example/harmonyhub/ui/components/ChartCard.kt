package com.example.harmonyhub.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.harmonyhub.ui.theme.NotoSans

@Composable
fun ChartCard(chartImg: String?, chartName: String, chartId: String, onChartClicked : () -> Unit) {
    Surface(
        modifier = Modifier
            .size(width = 150.dp, height = 150.dp)
            .clickable {onChartClicked() },
        color = Color.Transparent
    ) {

        Column(modifier = Modifier.padding(4.dp))
        {
            Box(
                modifier = Modifier.size(width = 125.dp, height = 125.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(chartImg)
                        .crossfade(true)
                        .build(),
                    error = painterResource(com.example.harmonyhub.R.drawable.ic_broken_image),
                    placeholder = painterResource(id = com.example.harmonyhub.R.drawable.loading_img),
                    contentDescription = "Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)),
                )
            }
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                text = chartName,
//                style = TextStyle(
//                    fontFamily = NotoSans,
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 16.sp
//                ),
//                maxLines = 1,
//                overflow = Ellipsis
//            )
        }
    }
}