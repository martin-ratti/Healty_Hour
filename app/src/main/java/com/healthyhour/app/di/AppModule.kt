package com.healthyhour.app.di

import android.content.Context
import androidx.room.Room
import com.healthyhour.app.data.local.db.HealthyHourDatabase
import com.healthyhour.app.data.local.db.dao.AppDailyUsageDao
import com.healthyhour.app.data.local.db.dao.DailyUsageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): HealthyHourDatabase {
        return Room.databaseBuilder(
            context,
            HealthyHourDatabase::class.java,
            "healthy_hour_db"
        ).build()
    }

    @Provides
    fun provideDailyUsageDao(db: HealthyHourDatabase): DailyUsageDao {
        return db.dailyUsageDao()
    }

    @Provides
    fun provideAppDailyUsageDao(db: HealthyHourDatabase): AppDailyUsageDao {
        return db.appDailyUsageDao()
    }
}
