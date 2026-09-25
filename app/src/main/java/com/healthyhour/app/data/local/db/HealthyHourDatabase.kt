package com.healthyhour.app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.healthyhour.app.data.local.db.dao.AppDailyUsageDao
import com.healthyhour.app.data.local.db.dao.DailyUsageDao
import com.healthyhour.app.data.local.db.entity.AppDailyUsageEntity
import com.healthyhour.app.data.local.db.entity.DailyUsageEntity

@Database(
    entities = [
        DailyUsageEntity::class,
        AppDailyUsageEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class HealthyHourDatabase : RoomDatabase() {
    abstract fun dailyUsageDao(): DailyUsageDao
    abstract fun appDailyUsageDao(): AppDailyUsageDao
}
