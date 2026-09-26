package com.timelens.app.data.local.usage

import android.app.usage.UsageEvents
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsageDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val usageStatsManager: UsageStatsManager =
        context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    private val packageManager: PackageManager = context.packageManager

    private val appNameCache = mutableMapOf<String, String>()
    private val appIconCache = mutableMapOf<String, Drawable?>()

    fun getDailyUsageStats(): List<UsageStats> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        return usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            calendar.timeInMillis,
            System.currentTimeMillis()
        ).filter { it.totalTimeInForeground > 0 }
    }

    fun getDailyEvents(): List<UsageEvents.Event> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val events = usageStatsManager.queryEvents(
            calendar.timeInMillis,
            System.currentTimeMillis()
        )

        val eventList = mutableListOf<UsageEvents.Event>()
        while (events.hasNextEvent()) {
            val event = UsageEvents.Event()
            events.getNextEvent(event)
            eventList.add(event)
        }
        return eventList
    }

    fun getAppName(packageName: String): String {
        return appNameCache.getOrPut(packageName) {
            try {
                val appInfo = packageManager.getApplicationInfo(packageName, 0)
                packageManager.getApplicationLabel(appInfo).toString()
            } catch (e: PackageManager.NameNotFoundException) {
                when (packageName) {
                    "com.zhiliaoapp.musically" -> "TikTok"
                    "com.google.android.youtube" -> "YouTube"
                    "com.whatsapp" -> "WhatsApp"
                    "com.instagram.android" -> "Instagram"
                    "com.twitter.android", "com.x.android" -> "X"
                    "com.spotify.music" -> "Spotify"
                    "com.facebook.katana" -> "Facebook"
                    "com.google.android.apps.messaging" -> "Mensajes"
                    "com.google.android.dialer" -> "Teléfono"
                    "com.android.chrome" -> "Chrome"
                    else -> packageName.substringAfterLast(".").replaceFirstChar { it.uppercase() }
                }
            }
        }
    }

    fun getAppIcon(packageName: String): Drawable? {
        return appIconCache.getOrPut(packageName) {
            try {
                packageManager.getApplicationIcon(packageName)
            } catch (e: PackageManager.NameNotFoundException) {
                null
            }
        }
    }

    private val eligibleCache = mutableMapOf<String, Boolean>()
    
    fun isAppEligibleForStats(packageName: String): Boolean {
        return eligibleCache.getOrPut(packageName) {
            val lowerPkg = packageName.lowercase()
            // Always allow well-known user apps even if intent lookup fails
            if (lowerPkg.contains("youtube") || 
                lowerPkg.contains("whatsapp") || 
                lowerPkg.contains("instagram") || 
                lowerPkg.contains("musically") ||
                lowerPkg.contains("spotify") || 
                lowerPkg.contains("twitter") || 
                lowerPkg.contains("tiktok") || 
                lowerPkg.contains("chrome")) {
                return@getOrPut true
            }
            // Ignore system launchers, system UI, and known internal packages
            if (lowerPkg.contains("systemui") || 
                lowerPkg.contains("launcher") || 
                lowerPkg.contains("digitalwellbeing") ||
                lowerPkg.contains("overlay") ||
                lowerPkg.contains("wallpaper") ||
                lowerPkg.contains("settings") ||
                lowerPkg == "android") {
                return@getOrPut false
            }
            // Check if it has a launcher intent (meaning it's a real user app)
            val intent = packageManager.getLaunchIntentForPackage(packageName)
            intent != null
        }
    }

    fun hasUsagePermission(): Boolean {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
        }
        val stats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            calendar.timeInMillis,
            System.currentTimeMillis()
        )
        return stats.isNotEmpty()
    }
}
