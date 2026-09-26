package com.timelens.app.domain.usecase

import com.timelens.app.domain.repository.UsageRepository
import javax.inject.Inject

class CheckUsagePermissionUseCase @Inject constructor(
    private val repository: UsageRepository
) {
    operator fun invoke(): Boolean {
        return repository.hasUsagePermission()
    }
}
