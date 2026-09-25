package com.timelens.app.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timelens.app.presentation.components.*
import com.timelens.app.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.RemoveRedEye,
                            contentDescription = null,
                            tint = NeonBlue
                        )
                        Text(
                            text = "TimeLens",
                            fontWeight = FontWeight.Bold
                        )
                    }
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
            item {
                CircularProgressCard(
                    totalTimeText = "4h 32m",
                    progress = 0.75f,
                    comparisonText = "-15% vs ayer"
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        icon = Icons.Outlined.LockOpen,
                        title = "Desbloqueos",
                        value = "47",
                        subtitle = "hoy",
                        accentColor = NeonOrange,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        icon = Icons.Outlined.Timer,
                        title = "Sesión más larga",
                        value = "1h 23m",
                        subtitle = "Instagram",
                        accentColor = NeonPurple,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        icon = Icons.Outlined.DarkMode,
                        title = "Horario pico",
                        value = "Noche",
                        subtitle = "20:00-22:00",
                        accentColor = NeonBlue,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.BarChart,
                        contentDescription = null,
                        tint = NeonBlue,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Apps más usadas",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            item {
                AppUsageCard(
                    icon = Icons.Outlined.CameraAlt,
                    appName = "Instagram",
                    usageTime = "1h 45m",
                    progress = 1f,
                    accentColor = NeonPurple,
                    sessionCount = 23
                )
            }

            item {
                AppUsageCard(
                    icon = Icons.Outlined.PlayCircle,
                    appName = "YouTube",
                    usageTime = "1h 12m",
                    progress = 0.68f,
                    accentColor = NeonRed,
                    sessionCount = 8
                )
            }

            item {
                AppUsageCard(
                    icon = Icons.Outlined.Chat,
                    appName = "WhatsApp",
                    usageTime = "48m",
                    progress = 0.45f,
                    accentColor = NeonGreen,
                    sessionCount = 45
                )
            }

            item {
                AppUsageCard(
                    icon = Icons.Outlined.Tag,
                    appName = "Twitter / X",
                    usageTime = "25m",
                    progress = 0.24f,
                    accentColor = NeonBlue,
                    sessionCount = 15
                )
            }

            item {
                AppUsageCard(
                    icon = Icons.Outlined.MusicNote,
                    appName = "Spotify",
                    usageTime = "22m",
                    progress = 0.21f,
                    accentColor = NeonOrange,
                    sessionCount = 3
                )
            }

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
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.LocalFireDepartment,
                                contentDescription = null,
                                tint = NeonRed,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Racha tóxica",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = NeonRed
                            )
                        }
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

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Estos son datos de demostración. Conectá tu teléfono y concedé el permiso de uso para ver datos reales.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                }
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
