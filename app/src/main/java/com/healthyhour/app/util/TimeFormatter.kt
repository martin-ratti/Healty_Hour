package com.healthyhour.app.util

object TimeFormatter {

    fun formatMillisToReadable(millis: Long): String {
        val totalSeconds = millis / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        return when {
            hours > 0 -> "${hours}h ${minutes}min"
            minutes > 0 -> "${minutes}min ${seconds}s"
            else -> "${seconds}s"
        }
    }

    fun formatMillisToShort(millis: Long): String {
        val totalMinutes = millis / 1000 / 60
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60

        return when {
            hours > 0 -> "${hours}h ${minutes}m"
            else -> "${minutes}m"
        }
    }

    fun formatPercentageChange(current: Long, previous: Long): String {
        if (previous == 0L) return "Sin datos previos"
        val change = ((current - previous).toFloat() / previous * 100).toInt()
        return when {
            change > 0 -> "+$change% vs ayer"
            change < 0 -> "$change% vs ayer"
            else -> "Igual que ayer"
        }
    }

    fun getTimeOfDayLabel(hour: Int): String {
        return when (hour) {
            in 6..11 -> "Mañana"
            in 12..17 -> "Tarde"
            in 18..22 -> "Noche"
            else -> "Madrugada"
        }
    }
}
