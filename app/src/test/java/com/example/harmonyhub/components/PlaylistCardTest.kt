package com.example.harmonyhub.components

import com.example.harmonyhub.ui.components.Playlist
import com.example.harmonyhub.ui.components.contains
import org.junit.Assert.assertEquals
import org.junit.Test

class PlaylistCardTest {
    @Test
    fun testContains() {
        // Test the PlaylistCard
        var playlist = Playlist("Mood In Mind", 1);
        assertEquals(playlist.contains("Mood"), true);
    }

    @Test
    fun testNoContains() {
        // Test the PlaylistCard
        var playlist = Playlist("Mood In Mind", 1);
        assertEquals(playlist.contains("Penta"), false);
    }

    @Test
    fun testIgnoreCaseFalse() {
        // Test the PlaylistCard
        var playlist = Playlist("Mood In Mind", 1);
        assertEquals(false, playlist.contains("mood", ignoreCase = false));
    }
}