package com.timelens.app.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.timelens.app.domain.model.DaySummary
import com.timelens.app.presentation.components.*
import com.timelens.app.presentation.theme.*
import com.timelens.app.util.TimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
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
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val state = uiState) {
                is HomeUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = NeonBlue
                    )
                }
                is HomeUiState.MissingPermission -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = null,
                            tint = NeonRed,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Se necesita permiso de acceso a uso para mostrar tus estadísticas.",
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
                is HomeUiState.Error -> {
                    Text(
                        text = "Error: ${state.message}",
                        color = NeonRed,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is HomeUiState.Success -> {
                    HomeContent(summary = state.summary, comparisonText = state.comparisonText)
                }
            }
        }
    }
}

@Composable
fun HomeContent(summary: DaySummary, comparisonText: String) {
    // Calculamos el objetivo de 6 horas para el progreso (6h = 21600000ms)
    val dailyGoalMs = 6 * 60 * 60 * 1000L
    val progress = (summary.totalScreenTimeMs.toFloat() / dailyGoalMs).coerceIn(0f, 1f)
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            CircularProgressCard(
                totalTimeText = TimeFormatter.formatMillisToShort(summary.totalScreenTimeMs),
                progress = progress,
                comparisonText = comparisonText
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
                    value = summary.totalUnlocks.toString(),
                    subtitle = "hoy",
                    accentColor = NeonOrange,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    icon = Icons.Outlined.Timer,
                    title = "Sesión max",
                    value = summary.longestSession?.durationMs?.let { TimeFormatter.formatMillisToShort(it) } ?: "0m",
                    subtitle = summary.longestSession?.appName ?: "Sin uso",
                    accentColor = NeonPurple,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    icon = Icons.Outlined.Schedule,
                    title = "Horario pico",
                    value = TimeFormatter.getTimeOfDayLabel(summary.peakHour),
                    subtitle = String.format(java.util.Locale.getDefault(), "%02d:00", summary.peakHour),
                    accentColor = NeonBlue,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
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

        items(summary.topApps) { appInfo ->
            // Usamos un icono por defecto si no tenemos el ícono real para el preview rápido,
            // pero ahora soporta Coil con AsyncImage para el Drawable.
            AppUsageCard(
                icon = appInfo.icon ?: Icons.Outlined.Apps,
                appName = appInfo.appName,
                usageTime = TimeFormatter.formatMillisToShort(appInfo.totalTimeMs),
                progress = (appInfo.totalTimeMs.toFloat() / summary.totalScreenTimeMs).coerceIn(0f, 1f),
                accentColor = NeonPurple,
                sessionCount = appInfo.sessionCount
            )
        }
    }
}
