package com.timelens.app.domain.util

import android.app.usage.UsageEvents
import java.util.Calendar

data class MetricsResult(
    val totalUnlocks: Int,
    val longestSessionMs: Long,
    val longestSessionAppPackage: String?,
    val peakHour: Int,
    val totalSessions: Int
)

object SessionCalculator {
    fun calculateMetrics(eventsList: List<UsageEvents.Event>): MetricsResult {
        var unlocks = 0
        var totalSessions = 0
        var longestSessionMs = 0L
        var longestSessionAppPackage: String? = null
        val hourUsageMap = mutableMapOf<Int, Long>()

        val activeSessions = mutableMapOf<String, Long>()

        for (event in eventsList) {
            when (event.eventType) {
                18 -> { // EVENT_KEYGUARD_HIDDEN (Unlock)
                    unlocks++
                }
                1 -> { // ACTIVITY_RESUMED (Move to foreground)
                    activeSessions[event.packageName] = event.timeStamp
                }
                2 -> { // ACTIVITY_PAUSED (Move to background)
                    val startTime = activeSessions.remove(event.packageName)
                    if (startTime != null && event.timeStamp > startTime) {
                        val duration = event.timeStamp - startTime
                        if (duration > longestSessionMs) {
                            longestSessionMs = duration
                            longestSessionAppPackage = event.packageName
                        }
                        totalSessions++
                        
                        // Add duration to the corresponding hour for peak hour calculation
                        val calendar = Calendar.getInstance().apply { timeInMillis = startTime }
                        val hour = calendar.get(Calendar.HOUR_OF_DAY)
                        hourUsageMap[hour] = (hourUsageMap[hour] ?: 0L) + duration
                    }
                }
            }
        }

        val peakHour = hourUsageMap.maxByOrNull { it.value }?.key ?: 0

        return MetricsResult(
            totalUnlocks = unlocks,
            longestSessionMs = longestSessionMs,
            longestSessionAppPackage = longestSessionAppPackage,
            peakHour = peakHour,
            totalSessions = totalSessions
        )
    }
}
