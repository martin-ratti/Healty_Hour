package com.healthyhour.app.domain.model

import android.graphics.drawable.Drawable

data class AppUsageInfo(
    val packageName: String,
    val appName: String,
    val icon: Drawable? = null,
    val totalTimeMs: Long,
    val sessionCount: Int,
    val longestSessionMs: Long,
    val category: AppCategory? = null
)

enum class AppCategory {
    SOCIAL,
    ENTERTAINMENT,
    PRODUCTIVITY,
    COMMUNICATION,
    GAMING,
    EDUCATION,
    UTILITY,
    OTHER
}
