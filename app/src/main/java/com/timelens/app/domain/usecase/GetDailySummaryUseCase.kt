package com.timelens.app.domain.usecase

import com.timelens.app.domain.model.DaySummary
import com.timelens.app.domain.repository.UsageRepository
import javax.inject.Inject

class GetDailySummaryUseCase @Inject constructor(
    private val repository: UsageRepository
) {
    suspend operator fun invoke(): DaySummary {
        return repository.getTodaySummary()
    }
}
