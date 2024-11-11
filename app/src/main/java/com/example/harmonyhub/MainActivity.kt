package com.example.harmonyhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.harmonyhub.ui.home.HomeScreen
import com.example.harmonyhub.ui.login.LoginScreen
import com.example.harmonyhub.ui.theme.HarmonyHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HarmonyHubTheme {
                LoginScreen()
            }
        }
    }
}