package com.intellectuswert.data.repository

import android.util.Log
import com.intellectuswert.data.api.ApiService
import com.intellectuswert.data.local.dao.PredictionDao
import com.intellectuswert.data.mapper.toDto
import com.intellectuswert.data.mapper.toDomain
import com.intellectuswert.data.mapper.toEntity
import com.intellectuswert.domain.model.Prediction
import com.intellectuswert.domain.model.PredictionResult
import com.intellectuswert.domain.model.StudentInput
import com.intellectuswert.domain.repository.PredictionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PredictionRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dao: PredictionDao
) : PredictionRepository {

    override suspend fun fetchPrediction(input: StudentInput): PredictionResult {
        val inputDto = input.toDto()
        Log.d("PredictionAPI", "Sending input: $inputDto")

        val response = api.predict(input.toDto())

        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string()
            Log.e("PredictionAPI", "Prediction API failed: ${response.code()} - ${response.message()}")
            Log.e("PredictionAPI", "Error Body: $errorBody")

            throw Exception("Prediction API failed: ${response.code()} - ${response.message()}")
        }

        val resultDto = requireNotNull(response.body()) {
            "Prediction API returned empty body"
        }
        Log.i("PredictionAPI", "Prediction success. Response: $resultDto")


        return resultDto.toDomain()
    }


    override suspend fun insertPrediction(prediction: Prediction) {
        dao.insert(prediction.toEntity())
    }

    override fun getAllPredictions(): Flow<List<Prediction>> {
        return dao.getAll().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getPredictionById(id: Int): Prediction? {
        return dao.getById(id)?.toDomain()
    }

    override suspend fun deletePrediction(id: Int) {
        dao.delete(id)
    }

    override suspend fun clearAllPredictions() {
        dao.clearAll()
    }
}
