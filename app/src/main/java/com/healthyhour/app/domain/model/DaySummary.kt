package com.healthyhour.app.domain.model

import java.time.LocalDate

data class DaySummary(
    val date: LocalDate,
    val totalScreenTimeMs: Long,
    val totalUnlocks: Int,
    val topApps: List<AppUsageInfo>,
    val longestSession: Session?,
    val peakHour: Int,
    val totalSessions: Int
)
