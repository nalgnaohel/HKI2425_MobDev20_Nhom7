package com.example.harmonyhub

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.example.harmonyhub.ui.theme.HarmonyHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setLightStatusBarIcons(this, lightIcons = false)
        setContent {
            HarmonyHubTheme {
                HarmonyHubApp()
            }
        }
    }
}
fun setLightStatusBarIcons(activity: Activity, lightIcons: Boolean) {
    WindowCompat.getInsetsController(activity.window, activity.window.decorView)
        .isAppearanceLightStatusBars = lightIcons
}
