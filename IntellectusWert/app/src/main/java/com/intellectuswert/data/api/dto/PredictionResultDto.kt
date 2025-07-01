package com.intellectuswert.data.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.google.gson.annotations.SerializedName


@Serializable
data class PredictionResultDto(
    @SerializedName("predicted_performance_index")
    val predictedPerformanceIndex: Double
)

