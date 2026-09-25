package com.timelens.app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.timelens.app.data.local.db.dao.AppDailyUsageDao
import com.timelens.app.data.local.db.dao.DailyUsageDao
import com.timelens.app.data.local.db.entity.AppDailyUsageEntity
import com.timelens.app.data.local.db.entity.DailyUsageEntity

@Database(
    entities = [
        DailyUsageEntity::class,
        AppDailyUsageEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TimeLensDatabase : RoomDatabase() {
    abstract fun dailyUsageDao(): DailyUsageDao
    abstract fun appDailyUsageDao(): AppDailyUsageDao
}
