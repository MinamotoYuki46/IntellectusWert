package com.intellectuswert.data.mapper

import com.intellectuswert.data.local.entity.PredictionEntity
import com.intellectuswert.domain.model.*

fun Prediction.toEntity(): PredictionEntity {
    return PredictionEntity(
        id = id,
        timestamp = timestamp,
        hoursStudied = studentInput.hoursStudied,
        previousScores = studentInput.previousScores,
        extracurricularActivities = studentInput.extracurricularActivities,
        sleepHours = studentInput.sleepHours,
        sampleQuestionPapersPracticed = studentInput.sampleQuestionPapersPracticed,
        predictedPerformanceIndex = predictedPerformanceIndex
    )
}

fun PredictionEntity.toDomain(): Prediction {
    return Prediction(
        id = id,
        timestamp = timestamp,
        predictedPerformanceIndex = predictedPerformanceIndex,
        studentInput = StudentInput(
            hoursStudied = hoursStudied,
            previousScores = previousScores,
            extracurricularActivities = extracurricularActivities,
            sleepHours = sleepHours,
            sampleQuestionPapersPracticed = sampleQuestionPapersPracticed
        )
    )
}