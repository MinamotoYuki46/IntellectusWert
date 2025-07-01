package com.intellectuswert.data.mapper

import com.intellectuswert.domain.model.*
import com.intellectuswert.data.api.dto.*

fun PredictionResultDto.toDomain(): PredictionResult {
    return PredictionResult(predictedPerformanceIndex)
}

fun StudentInput.toDto(): StudentInputDto {
    return StudentInputDto(
        hoursStudied,
        previousScores,
        extracurricularActivities,
        sleepHours,
        sampleQuestionPapersPracticed
    )
}
