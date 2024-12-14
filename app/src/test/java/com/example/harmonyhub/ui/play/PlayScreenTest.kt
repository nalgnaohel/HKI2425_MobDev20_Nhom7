package com.example.harmonyhub.ui.play;

import org.junit.Assert.assertEquals

import org.junit.Test;


public class PlayScreenTest {

    @Test
    fun testFormatTime1() {
        // Test the formatTime function
        assertEquals(formatTime(0), "00:00")
    }

    @Test
    fun testFormatTime2() {
        // Test the formatTime function
        assertEquals(formatTime(60000), "01:00")
    }

    @Test
    fun testFormatTime3() {
        // Test the formatTime function
        assertEquals(formatTime(61000), "01:01")
    }

    @Test
    fun testFormatTime4() {
        // Test the formatTime function
        assertEquals(formatTime(3599999), "59:59")
    }
}
