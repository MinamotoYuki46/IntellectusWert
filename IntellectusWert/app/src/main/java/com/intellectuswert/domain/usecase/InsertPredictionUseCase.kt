package com.intellectuswert.domain.usecase

import com.intellectuswert.domain.model.Prediction
import com.intellectuswert.domain.repository.PredictionRepository
import javax.inject.Inject

class InsertPredictionUseCase @Inject constructor(
    private val repository: PredictionRepository
) {
    suspend operator fun invoke(prediction: Prediction) {
        repository.insertPrediction(prediction)
    }
}
