package com.timelens.app.domain.usecase

import com.timelens.app.domain.model.DaySummary
import com.timelens.app.domain.repository.UsageRepository
import javax.inject.Inject

class GetWeeklyTrendUseCase @Inject constructor(
    private val repository: UsageRepository
) {
    suspend operator fun invoke(): List<DaySummary> {
        return repository.getWeeklyTrend()
    }
}
