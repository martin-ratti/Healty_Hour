package com.timelens.app.presentation.screens.settings

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timelens.app.data.local.db.dao.DailyUsageDao
import com.timelens.app.data.local.prefs.UserPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val prefsManager: UserPreferencesManager,
    private val dailyUsageDao: DailyUsageDao,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val dailyGoalHours: StateFlow<Int> = prefsManager.dailyGoalHours
    val notificationsEnabled: StateFlow<Boolean> = prefsManager.notificationsEnabled
    val darkThemeEnabled: StateFlow<Boolean> = prefsManager.darkThemeEnabled

    fun setDailyGoal(hours: Int) {
        prefsManager.setDailyGoalHours(hours)
    }

    fun toggleNotifications(enabled: Boolean) {
        prefsManager.setNotificationsEnabled(enabled)
    }

    fun toggleDarkTheme(enabled: Boolean) {
        prefsManager.setDarkThemeEnabled(enabled)
    }

    fun exportDataToCsv(onSuccess: (Intent) -> Unit) {
        viewModelScope.launch {
            val history = dailyUsageDao.getLastDays(30)
            val csvBuilder = StringBuilder()
            csvBuilder.append("Fecha,Tiempo_Pantalla_Minutos,Desbloqueos,Sesion_Max_Minutos,Sesion_Max_App\n")

            history.forEach { entity ->
                val screenMins = entity.totalScreenTimeMs / (1000 * 60)
                val longestMins = entity.longestSessionMs / (1000 * 60)
                csvBuilder.append("${entity.date},$screenMins,${entity.totalUnlocks},$longestMins,${entity.longestSessionApp}\n")
            }

            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, csvBuilder.toString())
                type = "text/csv"
                putExtra(Intent.EXTRA_TITLE, "TimeLens_historial.csv")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            val chooser = Intent.createChooser(sendIntent, "Exportar historial TimeLens (CSV)").apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            onSuccess(chooser)
        }
    }
}
