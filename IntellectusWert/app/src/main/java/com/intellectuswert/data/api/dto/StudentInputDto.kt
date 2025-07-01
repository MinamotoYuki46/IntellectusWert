package com.intellectuswert.data.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.google.gson.annotations.SerializedName

@Serializable
data class StudentInputDto(
    @SerializedName("hours_studied")
    val hoursStudied: Int,

    @SerializedName("previous_scores")
    val previousScores: Int,

    @SerializedName("extracurricular_activities")
    val extracurricularActivities: Int,

    @SerializedName("sleep_hours")
    val sleepHours: Int,

    @SerializedName("sample_question_papers_practiced")
    val sampleQuestionPapersPracticed: Int
)
