package com.timelens.app.domain.model

data class Session(
    val packageName: String,
    val appName: String = "",
    val startTimeMs: Long,
    val endTimeMs: Long,
    val durationMs: Long = endTimeMs - startTimeMs,
    val isEstimated: Boolean = false
)
