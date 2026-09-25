package com.timelens.app.domain.usecase

import com.timelens.app.domain.model.AppUsageInfo
import com.timelens.app.domain.repository.UsageRepository
import javax.inject.Inject

class GetTopAppsUseCase @Inject constructor(
    private val repository: UsageRepository
) {
    suspend operator fun invoke(limit: Int = 5): List<AppUsageInfo> {
        return repository.getTopApps(limit)
    }
}
