package com.healthyhour.app.domain.usecase

import com.healthyhour.app.domain.model.AppUsageInfo
import com.healthyhour.app.domain.repository.UsageRepository
import javax.inject.Inject

class GetTopAppsUseCase @Inject constructor(
    private val repository: UsageRepository
) {
    suspend operator fun invoke(limit: Int = 5): List<AppUsageInfo> {
        return repository.getTopApps(limit)
    }
}
