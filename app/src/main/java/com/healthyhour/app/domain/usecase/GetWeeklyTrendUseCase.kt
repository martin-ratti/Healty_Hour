package com.healthyhour.app.domain.usecase

import com.healthyhour.app.domain.model.DaySummary
import com.healthyhour.app.domain.repository.UsageRepository
import javax.inject.Inject

class GetWeeklyTrendUseCase @Inject constructor(
    private val repository: UsageRepository
) {
    suspend operator fun invoke(): List<DaySummary> {
        return repository.getWeeklyTrend()
    }
}
