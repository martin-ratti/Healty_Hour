package com.timelens.app.presentation.screens.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.timelens.app.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val dailyGoal by viewModel.dailyGoalHours.collectAsStateWithLifecycle()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsStateWithLifecycle()
    val darkThemeEnabled by viewModel.darkThemeEnabled.collectAsStateWithLifecycle()

    val context = LocalContext.current

    var showGoalDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Ajustes",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = DarkBackground
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Objetivo diario
            SettingItem(
                icon = Icons.Outlined.Flag,
                title = "Objetivo diario",
                subtitle = "Límite: $dailyGoal horas al día",
                iconTint = NeonOrange,
                onClick = { showGoalDialog = true },
                action = {
                    Text(
                        text = "${dailyGoal}h",
                        color = NeonOrange,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )

            // Notificaciones
            SettingItem(
                icon = Icons.Outlined.Notifications,
                title = "Notificaciones",
                subtitle = if (notificationsEnabled) "Alertas activadas" else "Alertas desactivadas",
                iconTint = NeonBlue,
                onClick = { viewModel.toggleNotifications(!notificationsEnabled) },
                action = {
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { viewModel.toggleNotifications(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = NeonBlue,
                            checkedTrackColor = NeonBlue.copy(alpha = 0.5f)
                        )
                    )
                }
            )

            // Tema
            SettingItem(
                icon = Icons.Outlined.DarkMode,
                title = "Tema Neón Oscuro",
                subtitle = if (darkThemeEnabled) "Activado (Recomendado)" else "Desactivado",
                iconTint = NeonGreen,
                onClick = { viewModel.toggleDarkTheme(!darkThemeEnabled) },
                action = {
                    Switch(
                        checked = darkThemeEnabled,
                        onCheckedChange = { viewModel.toggleDarkTheme(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = NeonGreen,
                            checkedTrackColor = NeonGreen.copy(alpha = 0.5f)
                        )
                    )
                }
            )

            // Exportar datos
            SettingItem(
                icon = Icons.Outlined.FileDownload,
                title = "Exportar datos",
                subtitle = "Compartir historial en CSV",
                iconTint = NeonCyan,
                onClick = {
                    viewModel.exportDataToCsv { chooserIntent ->
                        context.startActivity(chooserIntent)
                    }
                },
                action = {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = "Exportar",
                        tint = NeonCyan
                    )
                }
            )

            // Acerca de
            SettingItem(
                icon = Icons.Outlined.Info,
                title = "Acerca de TimeLens",
                subtitle = "Versión y privacidad",
                iconTint = MaterialTheme.colorScheme.onSurface,
                onClick = { showAboutDialog = true },
                action = {
                    Icon(
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "TimeLens v0.2.0 • 100% Local & Privado",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }

    // Dialog: Seleccionar Objetivo Diario
    if (showGoalDialog) {
        AlertDialog(
            onDismissRequest = { showGoalDialog = false },
            title = { Text("Definir objetivo diario", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Selecciona la cantidad de horas máxima recomendada por día:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    listOf(4, 5, 6, 7, 8, 9).forEach { hours ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setDailyGoal(hours)
                                    showGoalDialog = false
                                    Toast.makeText(context, "Objetivo actualizado a ${hours}h", Toast.LENGTH_SHORT).show()
                                },
                            shape = RoundedCornerShape(12.dp),
                            color = if (hours == dailyGoal) NeonOrange.copy(alpha = 0.2f) else DarkSurface
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "$hours horas",
                                    fontWeight = if (hours == dailyGoal) FontWeight.Bold else FontWeight.Normal,
                                    color = if (hours == dailyGoal) NeonOrange else MaterialTheme.colorScheme.onSurface
                                )
                                if (hours == dailyGoal) {
                                    Icon(
                                        imageVector = Icons.Outlined.Check,
                                        contentDescription = null,
                                        tint = NeonOrange
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showGoalDialog = false }) {
                    Text("Cerrar", color = NeonBlue)
                }
            },
            containerColor = DarkCard
        )
    }

    // Dialog: Acerca de
    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.RemoveRedEye,
                    contentDescription = null,
                    tint = NeonBlue,
                    modifier = Modifier.size(36.dp)
                )
            },
            title = { Text("TimeLens", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "TimeLens es tu lente de consciencia y bienestar digital.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "🛡️ Privacidad primero: Ningún dato sale de tu teléfono. Todo el cálculo de estadísticas se ejecuta 100% de manera local en tu dispositivo.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Versión: 0.2.0\nDesarrollado en Pair Programming con Antigravity.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showAboutDialog = false }) {
                    Text("Entendido", color = NeonBlue)
                }
            },
            containerColor = DarkCard
        )
    }
}

@Composable
fun SettingItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconTint: Color,
    onClick: () -> Unit = {},
    action: @Composable () -> Unit
) {
    Surface(
        color = DarkCard,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            action()
        }
    }
}
