package com.timelens.app.presentation.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.timelens.app.domain.model.DaySummary
import com.timelens.app.presentation.components.WeeklyBarChart
import com.timelens.app.presentation.theme.DarkBackground
import com.timelens.app.presentation.theme.DarkCard
import com.timelens.app.presentation.theme.NeonGreen
import com.timelens.app.presentation.theme.NeonOrange
import com.timelens.app.presentation.theme.NeonPurple
import com.timelens.app.presentation.theme.TimeLensTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.TrendingUp,
                contentDescription = null,
                tint = NeonPurple,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Tendencias",
                style = MaterialTheme.typography.headlineMedium,
                color = NeonPurple,
                fontWeight = FontWeight.Bold
            )
        }

        when (val state = uiState) {
            is HistoryUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    androidx.compose.material3.CircularProgressIndicator(color = NeonPurple)
                }
            }
            is HistoryUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error al cargar historial: ${state.message}", color = NeonOrange)
                }
            }
            is HistoryUiState.Success -> {
                val data = state.weeklyData
                if (data.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay datos históricos aún.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                } else {
                    HistoryContent(data)
                }
            }
        }
    }
}

@Composable
fun HistoryContent(weeklyData: List<DaySummary>) {
    val formatter = java.time.format.DateTimeFormatter.ofPattern("EEEE", java.util.Locale("es", "ES"))
    val days = weeklyData.map { it.date.format(formatter).replaceFirstChar { char -> char.uppercase() } }
    val timesMs = weeklyData.map { it.totalScreenTimeMs }
    
    val avgTimeMs = if (weeklyData.isNotEmpty()) timesMs.average().toLong() else 0L

    // Calculate Best/Worst days
    val bestDay = weeklyData.minByOrNull { it.totalScreenTimeMs }
    val worstDay = weeklyData.maxByOrNull { it.totalScreenTimeMs }

    Spacer(modifier = Modifier.height(16.dp))
    
    // Custom Bar Chart
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DarkCard)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Actividad (Últimos ${weeklyData.size} días)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            WeeklyBarChart(
                days = days,
                valuesMs = timesMs,
                barColor = NeonPurple
            )
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(containerColor = DarkCard)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.EmojiEvents,
                    contentDescription = null,
                    tint = NeonGreen,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Mejor día",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = bestDay?.date?.format(formatter)?.replaceFirstChar { it.uppercase() } ?: "-",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(containerColor = DarkCard)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.Warning,
                    contentDescription = null,
                    tint = NeonOrange,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Peor día",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = worstDay?.date?.format(formatter)?.replaceFirstChar { it.uppercase() } ?: "-",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Text(
        text = "Promedio semanal: ${com.timelens.app.util.TimeFormatter.formatMillisToReadable(avgTimeMs)}",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Preview
@Composable
fun HistoryScreenPreview() {
    TimeLensTheme {
        HistoryScreen()
    }
}
