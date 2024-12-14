package com.example.harmonyhub.ui.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SplitMusicScreen(
    url1 : String?,
    url2 : String?,
    onBackButtonClicked :() -> Unit

){
    Column {
        IconButton(
            onClick = { onBackButtonClicked() },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Quay lại")
        }
        if (url1 != null) {
            Text(
                text = url1
            )
        }
        if (url2 != null) {
            Text(
                text = url2
            )
        }
    }

}