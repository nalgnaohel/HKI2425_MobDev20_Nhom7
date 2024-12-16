package com.example.harmonyhub.components

import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.components.contains
import org.junit.Assert.assertEquals
import org.junit.Test

class SongCardTest {
    @Test
    fun testContains() {
        // Test the SongCard
        var song = Song("1", "Conditionally", "Katy Perry", "imageResId", "url");
        assertEquals(song.contains("Conditionally"), true);
    }

    @Test
    fun testContainsArtist() {
        // Test the SongCard
        var song = Song("1", "Conditionally", "Katy Perry", "imageResId", "url");
        assertEquals(song.contains("Kat"), true);
    }

    @Test()
    fun testNoContains() {
        // Test the SongCard
        var song = Song("1", "Conditionally", "Katy Perry", "imageResId", "url");
        assertEquals(song.contains("Taylor"), false);
    }
}