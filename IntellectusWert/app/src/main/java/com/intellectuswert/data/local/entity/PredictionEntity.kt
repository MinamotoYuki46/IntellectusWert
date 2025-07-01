package com.intellectuswert.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "predictions")
data class PredictionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long,
    val hoursStudied: Int,
    val previousScores: Int,
    val extracurricularActivities: Int,
    val sleepHours: Int,
    val sampleQuestionPapersPracticed: Int,
    val predictedPerformanceIndex: Double
)
