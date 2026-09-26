package com.timelens.app.domain.util

import android.app.usage.UsageEvents
import java.util.Calendar

data class MetricsResult(
    val totalUnlocks: Int,
    val longestSessionMs: Long,
    val longestSessionAppPackage: String?,
    val peakHour: Int,
    val totalSessions: Int,
    val appUsageMap: Map<String, Long>,
    val appSessionCountMap: Map<String, Int>
)

object SessionCalculator {
    fun calculateMetrics(eventsList: List<UsageEvents.Event>, startTimeMs: Long): MetricsResult {
        var unlocks = 0
        var totalSessions = 0
        var longestSessionMs = 0L
        var longestSessionAppPackage: String? = null
        val hourUsageMap = mutableMapOf<Int, Long>()
        val appUsageMap = mutableMapOf<String, Long>()
        val appSessionCountMap = mutableMapOf<String, Int>()

        val activeSessions = mutableMapOf<String, Long>()

        for (event in eventsList) {
            when (event.eventType) {
                18 -> unlocks++ // EVENT_KEYGUARD_HIDDEN (Unlock)
                1 -> { // ACTIVITY_RESUMED / MOVE_TO_FOREGROUND
                    activeSessions[event.packageName] = event.timeStamp
                }
                2 -> { // ACTIVITY_PAUSED / MOVE_TO_BACKGROUND
                    val startTime = activeSessions.remove(event.packageName)
                    if (startTime != null && event.timeStamp > startTime) {
                        val duration = event.timeStamp - startTime
                        
                        // Ignore impossibly long single sessions (e.g., > 12 hours) just as a safety net
                        if (duration < 12 * 60 * 60 * 1000L) {
                            // Update max session
                            if (duration > longestSessionMs) {
                                longestSessionMs = duration
                                longestSessionAppPackage = event.packageName
                            }
                            totalSessions++
                            
                            // Update app usage map
                            appUsageMap[event.packageName] = (appUsageMap[event.packageName] ?: 0L) + duration
                            appSessionCountMap[event.packageName] = (appSessionCountMap[event.packageName] ?: 0) + 1
                            
                            // Add duration to the corresponding hour for peak hour calculation
                            val calendar = Calendar.getInstance().apply { timeInMillis = startTime }
                            val hour = calendar.get(Calendar.HOUR_OF_DAY)
                            hourUsageMap[hour] = (hourUsageMap[hour] ?: 0L) + duration
                        }
                    }
                }
            }
        }
        
        // Handle apps that are still open
        val currentTime = System.currentTimeMillis()
        for ((packageName, startTime) in activeSessions) {
            val duration = currentTime - startTime
            if (duration > 0) {
                appUsageMap[packageName] = (appUsageMap[packageName] ?: 0L) + duration
                
                if (duration > longestSessionMs) {
                    longestSessionMs = duration
                    longestSessionAppPackage = packageName
                }
            }
        }

        val peakHour = hourUsageMap.maxByOrNull { it.value }?.key ?: 0

        return MetricsResult(
            totalUnlocks = unlocks,
            longestSessionMs = longestSessionMs,
            longestSessionAppPackage = longestSessionAppPackage,
            peakHour = peakHour,
            totalSessions = totalSessions,
            appUsageMap = appUsageMap,
            appSessionCountMap = appSessionCountMap
        )
    }
}
