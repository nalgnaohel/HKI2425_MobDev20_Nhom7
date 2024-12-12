package com.example.harmonyhub.account
import com.example.harmonyhub.ui.account.isValidEmail
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
class ForgotPasswordTest {

    @Test
    fun testForgotPassword1() {
        // Test case
        assertEquals(isValidEmail("lanlehoang8124@gmail.com"), true)
    }

    @Test
    fun testForgotPassword2() {
        // Test case
        assertEquals(isValidEmail("lanlehoang8124"), false)
    }
}