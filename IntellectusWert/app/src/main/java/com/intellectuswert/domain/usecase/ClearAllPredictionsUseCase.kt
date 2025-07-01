package com.intellectuswert.domain.usecase

import com.intellectuswert.domain.repository.PredictionRepository
import javax.inject.Inject

class ClearAllPredictionsUseCase @Inject constructor(
    private val repository: PredictionRepository
) {
    suspend operator fun invoke() {
        repository.clearAllPredictions()
    }
}
