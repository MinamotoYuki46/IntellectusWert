package com.intellectuswert.domain.usecase

import com.intellectuswert.domain.model.Prediction
import com.intellectuswert.domain.repository.PredictionRepository
import javax.inject.Inject

class GetPredictionByIdUseCase @Inject constructor(
    private val repository: PredictionRepository
) {
    suspend operator fun invoke(id: Int): Prediction? {
        return repository.getPredictionById(id)
    }
}
