package com.intellectuswert.domain.usecase

data class PredictionUseCases(
    val fetchPrediction: FetchPredictionUseCase,
    val insertPrediction: InsertPredictionUseCase,
    val getAllPredictions: GetAllPredictionsUseCase,
    val getPredictionById: GetPredictionByIdUseCase,
    val deletePrediction: DeletePredictionUseCase,
    val clearAllPredictions: ClearAllPredictionsUseCase,
)
