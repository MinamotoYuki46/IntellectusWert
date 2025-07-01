package com.intellectuswert.domain.usecase

import com.intellectuswert.domain.model.PredictionResult
import com.intellectuswert.domain.model.StudentInput
import com.intellectuswert.domain.repository.PredictionRepository
import javax.inject.Inject

class FetchPredictionUseCase @Inject constructor(
    private val repository: PredictionRepository
) {
    suspend operator fun invoke(input: StudentInput): PredictionResult {
        return repository.fetchPrediction(input)
    }
}
