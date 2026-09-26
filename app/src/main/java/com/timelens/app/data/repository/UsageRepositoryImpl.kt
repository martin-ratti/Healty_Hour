package com.timelens.app.data.repository

import com.timelens.app.data.local.db.dao.AppDailyUsageDao
import com.timelens.app.data.local.db.dao.DailyUsageDao
import com.timelens.app.data.local.db.entity.AppDailyUsageEntity
import com.timelens.app.data.local.db.entity.DailyUsageEntity
import com.timelens.app.data.local.db.entity.toDomain
import com.timelens.app.data.local.db.entity.toEntity
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
        
        // Use calendar exact midnight for start time
        val calendar = java.util.Calendar.getInstance().apply {
            set(java.util.Calendar.HOUR_OF_DAY, 0)
            set(java.util.Calendar.MINUTE, 0)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
        }
        val metrics = SessionCalculator.calculateMetrics(events, calendar.timeInMillis)

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
        val events = dataSource.getDailyEvents()
        val calendar = java.util.Calendar.getInstance().apply {
            set(java.util.Calendar.HOUR_OF_DAY, 0)
            set(java.util.Calendar.MINUTE, 0)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
        }
        val metrics = SessionCalculator.calculateMetrics(events, calendar.timeInMillis)

        // Map from our exact usage calculations instead of UsageStats
        metrics.appUsageMap.map { (packageName, totalTimeMs) ->
            AppUsageInfo(
                packageName = packageName,
                appName = dataSource.getAppName(packageName),
                icon = dataSource.getAppIcon(packageName),
                totalTimeMs = totalTimeMs,
                sessionCount = metrics.appSessionCountMap[packageName] ?: 0,
                longestSessionMs = if (metrics.longestSessionAppPackage == packageName) metrics.longestSessionMs else 0L
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
            it.toDomain(dataSource.getAppIcon(it.packageName))
        }

        entity.toDomain(topApps, dataSource.getAppName(entity.longestSessionApp))
    }

    override suspend fun getWeeklyTrend(): List<DaySummary> = withContext(Dispatchers.IO) {
        val entities = dailyUsageDao.getLastDays(7)
        entities.mapNotNull { getDaySummary(LocalDate.parse(it.date)) }.sortedBy { it.date }
    }

    override suspend fun saveDaySummary(summary: DaySummary) = withContext(Dispatchers.IO) {
        val dateString = summary.date.toString()
        
        dailyUsageDao.insertOrUpdate(summary.toEntity())

        val appEntities = summary.topApps.map { it.toEntity(dateString) }
        
        appDailyUsageDao.insertAll(appEntities)
    }

    override fun hasUsagePermission(): Boolean {
        return dataSource.hasUsagePermission()
    }
}
