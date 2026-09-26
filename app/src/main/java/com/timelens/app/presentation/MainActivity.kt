package com.timelens.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.timelens.app.domain.repository.UsageRepository
import com.timelens.app.presentation.navigation.AppNavHost
import com.timelens.app.presentation.navigation.NavRoutes
import com.timelens.app.presentation.theme.TimeLensTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var usageRepository: UsageRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val startDestination = if (usageRepository.hasUsagePermission()) {
            NavRoutes.Home.route
        } else {
            NavRoutes.Onboarding.route
        }

        setContent {
            TimeLensTheme(darkTheme = true) {
                AppNavHost(startDestination = startDestination)
            }
        }
    }
}
