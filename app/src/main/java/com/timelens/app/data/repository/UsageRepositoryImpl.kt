package com.timelens.app.data.repository

import com.timelens.app.data.local.db.dao.AppDailyUsageDao
import com.timelens.app.data.local.db.dao.DailyUsageDao
import com.timelens.app.data.local.db.entity.AppDailyUsageEntity
import com.timelens.app.data.local.db.entity.DailyUsageEntity
import com.timelens.app.data.local.usage.UsageDataSource
import com.timelens.app.domain.model.AppUsageInfo
import com.timelens.app.domain.model.DaySummary
import com.timelens.app.domain.model.Session
import com.timelens.app.domain.repository.UsageRepository
import com.timelens.app.domain.util.SessionCalculator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsageRepositoryImpl @Inject constructor(
    private val dataSource: UsageDataSource,
    private val dailyUsageDao: DailyUsageDao,
    private val appDailyUsageDao: AppDailyUsageDao
) : UsageRepository {

    override suspend fun getTodaySummary(): DaySummary = withContext(Dispatchers.IO) {
        val apps = getAppUsageToday()
        val totalTime = apps.sumOf { it.totalTimeMs }
        val topApps = apps.sortedByDescending { it.totalTimeMs }.take(5)

        val events = dataSource.getDailyEvents()
        val metrics = SessionCalculator.calculateMetrics(events)

        val longestSessionAppInfo = apps.find { it.packageName == metrics.longestSessionAppPackage }

        val longestSession = if (metrics.longestSessionAppPackage != null) {
            Session(
                packageName = metrics.longestSessionAppPackage,
                appName = longestSessionAppInfo?.appName ?: dataSource.getAppName(metrics.longestSessionAppPackage),
                durationMs = metrics.longestSessionMs,
                startTimeMs = 0L, // Mock startTime for now
                endTimeMs = 0L    // Mock endTime for now
            )
        } else null

        val summary = DaySummary(
            date = LocalDate.now(),
            totalScreenTimeMs = totalTime,
            totalUnlocks = metrics.totalUnlocks,
            topApps = topApps,
            longestSession = longestSession,
            peakHour = metrics.peakHour,
            totalSessions = metrics.totalSessions
        )

        // Save immediately for persistency
        saveDaySummary(summary)

        summary
    }

    override suspend fun getAppUsageToday(): List<AppUsageInfo> = withContext(Dispatchers.IO) {
        val stats = dataSource.getDailyUsageStats()
        val events = dataSource.getDailyEvents()
        val metrics = SessionCalculator.calculateMetrics(events) // Basic calculation, in future we can map per app

        stats.map { stat ->
            AppUsageInfo(
                packageName = stat.packageName,
                appName = dataSource.getAppName(stat.packageName),
                icon = dataSource.getAppIcon(stat.packageName),
                totalTimeMs = stat.totalTimeInForeground,
                sessionCount = 0, // We can enhance calculator to return map of session counts per app
                longestSessionMs = if (metrics.longestSessionAppPackage == stat.packageName) metrics.longestSessionMs else 0L
            )
        }.filter { it.totalTimeMs > 0 }
         .sortedByDescending { it.totalTimeMs }
    }

    override suspend fun getTopApps(limit: Int): List<AppUsageInfo> {
        return getAppUsageToday().take(limit)
    }

    override suspend fun getSessions(packageName: String, date: LocalDate): List<Session> {
        return emptyList()
    }

    override suspend fun getDaySummary(date: LocalDate): DaySummary? = withContext(Dispatchers.IO) {
        val dateString = date.toString()
        val entity = dailyUsageDao.getByDate(dateString) ?: return@withContext null
        val appEntities = appDailyUsageDao.getByDate(dateString)

        val topApps = appEntities.take(5).map {
            AppUsageInfo(
                packageName = it.packageName,
                appName = it.appName,
                icon = dataSource.getAppIcon(it.packageName),
                totalTimeMs = it.totalTimeMs,
                sessionCount = it.sessionCount,
                longestSessionMs = it.longestSessionMs
            )
        }

        DaySummary(
            date = date,
            totalScreenTimeMs = entity.totalScreenTimeMs,
            totalUnlocks = entity.totalUnlocks,
            topApps = topApps,
            longestSession = if (entity.longestSessionApp.isNotEmpty()) Session(entity.longestSessionApp, dataSource.getAppName(entity.longestSessionApp), entity.longestSessionMs, 0L, 0L) else null,
            peakHour = entity.peakHour,
            totalSessions = entity.totalSessions
        )
    }

    override suspend fun getWeeklyTrend(): List<DaySummary> = withContext(Dispatchers.IO) {
        val entities = dailyUsageDao.getLastDays(7)
        entities.mapNotNull { getDaySummary(LocalDate.parse(it.date)) }.sortedBy { it.date }
    }

    override suspend fun saveDaySummary(summary: DaySummary) = withContext(Dispatchers.IO) {
        val dateString = summary.date.toString()
        
        val dailyEntity = DailyUsageEntity(
            date = dateString,
            totalScreenTimeMs = summary.totalScreenTimeMs,
            totalUnlocks = summary.totalUnlocks,
            longestSessionMs = summary.longestSession?.durationMs ?: 0L,
            longestSessionApp = summary.longestSession?.packageName ?: "",
            topAppPackage = summary.topApps.firstOrNull()?.packageName ?: "",
            topAppTimeMs = summary.topApps.firstOrNull()?.totalTimeMs ?: 0L,
            totalSessions = summary.totalSessions,
            peakHour = summary.peakHour
        )
        
        dailyUsageDao.insertOrUpdate(dailyEntity)

        val appEntities = summary.topApps.map { app ->
            AppDailyUsageEntity(
                date = dateString,
                packageName = app.packageName,
                appName = app.appName,
                totalTimeMs = app.totalTimeMs,
                sessionCount = app.sessionCount,
                longestSessionMs = app.longestSessionMs,
                category = null
            )
        }
        
        appDailyUsageDao.insertAll(appEntities)
    }

    override fun hasUsagePermission(): Boolean {
        return dataSource.hasUsagePermission()
    }
}
