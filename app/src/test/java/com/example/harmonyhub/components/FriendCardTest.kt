package com.example.harmonyhub.components

import com.example.harmonyhub.ui.components.Friend
import com.example.harmonyhub.ui.components.contains
import org.junit.Assert.assertEquals
import org.junit.Test

class FriendCardTest {
    @Test
    fun testContains() {
        val friend =  Friend("nalgnaohel", "lanlehoang8124@gmail.com", 2)
        assertEquals(friend.contains("nalgnaohel"), true)
    }

    @Test
    fun testNoContains() {
        val friend = Friend("nalgnaohel", "lanlehoang8124@gmail.com", 2)
        assertEquals(friend.contains("Penta"), false)
    }

    @Test
    fun testIgnoreCaseFalse() {
        val friend = Friend("nalgnaohel", "lanlehoang8124@gmail.com", 2)
        assertEquals(friend.contains("nALgnaohel", ignoreCase = false), false)
    }
}