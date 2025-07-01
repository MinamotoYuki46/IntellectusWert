package com.intellectuswert.domain.usecase

import com.intellectuswert.domain.model.Prediction
import com.intellectuswert.domain.repository.PredictionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPredictionsUseCase @Inject constructor(
    private val repository: PredictionRepository
) {
    operator fun invoke(): Flow<List<Prediction>> {
        return repository.getAllPredictions()
    }
}
