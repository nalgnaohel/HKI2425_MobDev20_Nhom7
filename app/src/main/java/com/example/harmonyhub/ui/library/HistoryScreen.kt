package com.example.harmonyhub.ui.library

import androidx.compose.runtime.Composable
import com.example.harmonyhub.R
import com.example.harmonyhub.ui.components.Song

@Composable
fun HistoryScreen(onBackButtonClicked: () -> Unit) {
    val allSongs = listOf(
        Song("Inside Out", "The Chainsmokers, Charlee", R.drawable.v),
        Song("Young", "The Chainsmokers", R.drawable.v),
        Song("Beach House", "The Chainsmokers, Sick", R.drawable.v),
        Song("Kills You Slowly", "The Chainsmokers", R.drawable.v),
        Song("Setting Fires", "The Chainsmokers, XYLO", R.drawable.v),
        Song("The Real Slim Shady", "Eminem", R.drawable.v),
        Song("Lose Yourself", "Eminem", R.drawable.v),
    )
    SongList(
        title = "Đã nghe gần đây",
        songs = allSongs,
        onBackButtonClicked = onBackButtonClicked
    )
}




