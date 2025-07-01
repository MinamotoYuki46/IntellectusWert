package com.intellectuswert.data.api

import com.intellectuswert.data.api.dto.StudentInputDto
import com.intellectuswert.data.api.dto.PredictionResultDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/predict")
    suspend fun predict(
        @Body studentInput: StudentInputDto
    ): Response<PredictionResultDto>
}
