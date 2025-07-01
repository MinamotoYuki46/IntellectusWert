package com.intellectuswert.domain.model

data class Prediction(
    val id: Int = 0,
    val timestamp: Long,
    val predictedPerformanceIndex: Double,
    val studentInput: StudentInput
)
