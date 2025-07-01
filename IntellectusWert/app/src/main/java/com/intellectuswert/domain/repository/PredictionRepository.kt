package com.intellectuswert.domain.repository

import com.intellectuswert.domain.model.*
import kotlinx.coroutines.flow.Flow

interface PredictionRepository {
    suspend fun fetchPrediction(input: StudentInput): PredictionResult

    suspend fun insertPrediction(prediction: Prediction)

    fun getAllPredictions(): Flow<List<Prediction>>

    suspend fun getPredictionById(id: Int): Prediction?

    suspend fun deletePrediction(id: Int)

    suspend fun clearAllPredictions()
}