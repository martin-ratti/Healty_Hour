package com.timelens.app.data.repository

import com.timelens.app.data.local.usage.UsageDataSource
import com.timelens.app.domain.model.AppUsageInfo
import com.timelens.app.domain.model.DaySummary
import com.timelens.app.domain.model.Session
import com.timelens.app.domain.repository.UsageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsageRepositoryImpl @Inject constructor(
    private val dataSource: UsageDataSource
) : UsageRepository {

    override suspend fun getTodaySummary(): DaySummary = withContext(Dispatchers.IO) {
        val apps = getAppUsageToday()
        val totalTime = apps.sumOf { it.totalTimeMs }
        val topApps = apps.sortedByDescending { it.totalTimeMs }.take(5)

        // Mockeamos algunos datos complejos por ahora (desbloqueos, picos)
        DaySummary(
            date = LocalDate.now(),
            totalScreenTimeMs = totalTime,
            totalUnlocks = 0, // TODO: Calcular desde eventos
            topApps = topApps,
            longestSession = null, // TODO: Calcular desde eventos
            peakHour = 0, // TODO: Calcular desde eventos
            totalSessions = 0 // TODO: Calcular desde eventos
        )
    }

    override suspend fun getAppUsageToday(): List<AppUsageInfo> = withContext(Dispatchers.IO) {
        val stats = dataSource.getDailyUsageStats()
        
        stats.map { stat ->
            AppUsageInfo(
                packageName = stat.packageName,
                appName = dataSource.getAppName(stat.packageName),
                icon = dataSource.getAppIcon(stat.packageName),
                totalTimeMs = stat.totalTimeInForeground,
                sessionCount = 0, // TODO: Calcular desde eventos
                longestSessionMs = 0L // TODO: Calcular desde eventos
            )
        }.filter { it.totalTimeMs > 0 } // Solo apps con tiempo de uso
         .sortedByDescending { it.totalTimeMs }
    }

    override suspend fun getTopApps(limit: Int): List<AppUsageInfo> {
        return getAppUsageToday().take(limit)
    }

    override suspend fun getSessions(packageName: String, date: LocalDate): List<Session> {
        // TODO: Implementar usando getDailyEvents de UsageDataSource
        return emptyList()
    }

    override suspend fun getDaySummary(date: LocalDate): DaySummary? {
        // TODO: Leer desde Room database
        return null
    }

    override suspend fun getWeeklyTrend(): List<DaySummary> {
        // TODO: Leer desde Room database
        return emptyList()
    }

    override suspend fun saveDaySummary(summary: DaySummary) {
        // TODO: Guardar en Room database
    }

    override fun hasUsagePermission(): Boolean {
        return dataSource.hasUsagePermission()
    }
}
