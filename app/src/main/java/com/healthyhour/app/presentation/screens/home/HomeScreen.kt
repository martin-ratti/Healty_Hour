package com.healthyhour.app.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.healthyhour.app.presentation.components.*
import com.healthyhour.app.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "TimeLens",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { /* TODO: navigate to settings */ }) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Ajustes"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackground,
                    titleContentColor = NeonBlue
                )
            )
        },
        containerColor = DarkBackground
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Circular progress - total screen time
            item {
                CircularProgressCard(
                    totalTimeText = "4h 32m",
                    progress = 0.75f,
                    comparisonText = "-15% vs ayer \uD83C\uDF89"
                )
            }

            // Stats row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        emoji = "\uD83D\uDD13",
                        title = "Desbloqueos",
                        value = "47",
                        subtitle = "hoy",
                        accentColor = NeonOrange,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        emoji = "⏱️",
                        title = "Sesión más larga",
                        value = "1h 23m",
                        subtitle = "Instagram",
                        accentColor = NeonPurple,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        emoji = "\uD83C\uDF19",
                        title = "Horario pico",
                        value = "Noche",
                        subtitle = "20:00-22:00",
                        accentColor = NeonBlue,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Section title
            item {
                Text(
                    text = "📊 Apps más usadas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // App usage cards
            item {
                AppUsageCard(
                    emoji = "\uD83D\uDCF8",
                    appName = "Instagram",
                    usageTime = "1h 45m",
                    progress = 1f,
                    accentColor = NeonPurple,
                    sessionCount = 23
                )
            }

            item {
                AppUsageCard(
                    emoji = "▶️",
                    appName = "YouTube",
                    usageTime = "1h 12m",
                    progress = 0.68f,
                    accentColor = NeonRed,
                    sessionCount = 8
                )
            }

            item {
                AppUsageCard(
                    emoji = "\uD83D\uDCAC",
                    appName = "WhatsApp",
                    usageTime = "48m",
                    progress = 0.45f,
                    accentColor = NeonGreen,
                    sessionCount = 45
                )
            }

            item {
                AppUsageCard(
                    emoji = "\uD83D\uDC26",
                    appName = "Twitter / X",
                    usageTime = "25m",
                    progress = 0.24f,
                    accentColor = NeonBlue,
                    sessionCount = 15
                )
            }

            item {
                AppUsageCard(
                    emoji = "\uD83C\uDFB5",
                    appName = "Spotify",
                    usageTime = "22m",
                    progress = 0.21f,
                    accentColor = NeonOrange,
                    sessionCount = 3
                )
            }

            // Toxic streak card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = NeonRed.copy(alpha = 0.1f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "\uD83D\uDD25 Racha tóxica",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = NeonRed
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "2h 15min sin soltar el teléfono",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Hoy entre las 21:30 y 23:45",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    }
                }
            }

            // Footer note
            item {
                Text(
                    text = "⚠️ Estos son datos de demostración. Conectá tu teléfono y concedé el permiso de uso para ver datos reales.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    TimeLensTheme(darkTheme = true) {
        HomeScreen()
    }
}
