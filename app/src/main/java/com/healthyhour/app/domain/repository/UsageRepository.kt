package com.healthyhour.app.domain.repository

import com.healthyhour.app.domain.model.AppUsageInfo
import com.healthyhour.app.domain.model.DaySummary
import com.healthyhour.app.domain.model.Session
import java.time.LocalDate

interface UsageRepository {
    suspend fun getTodaySummary(): DaySummary
    suspend fun getAppUsageToday(): List<AppUsageInfo>
    suspend fun getTopApps(limit: Int = 5): List<AppUsageInfo>
    suspend fun getSessions(packageName: String, date: LocalDate): List<Session>
    suspend fun getDaySummary(date: LocalDate): DaySummary?
    suspend fun getWeeklyTrend(): List<DaySummary>
    suspend fun saveDaySummary(summary: DaySummary)
}
