package com.healthyhour.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.healthyhour.app.presentation.screens.home.HomeScreen
import com.healthyhour.app.presentation.theme.TimeLensTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeLensTheme(darkTheme = true) {
                HomeScreen()
            }
        }
    }
}
