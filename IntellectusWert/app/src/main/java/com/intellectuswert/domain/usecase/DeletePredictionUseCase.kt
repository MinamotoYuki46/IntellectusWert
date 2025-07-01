package com.intellectuswert.domain.usecase

import com.intellectuswert.domain.model.Prediction
import com.intellectuswert.domain.repository.PredictionRepository
import javax.inject.Inject

class DeletePredictionUseCase(
    private val repository: PredictionRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deletePrediction(id)
    }
}

