package com.intellectuswert.presentation.predict

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intellectuswert.domain.model.Prediction
import com.intellectuswert.domain.model.StudentInput
import com.intellectuswert.domain.usecase.PredictionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PredictViewModel @Inject constructor(
    private val useCases: PredictionUseCases
) : ViewModel() {

    var studyHours by mutableStateOf("")
    var prevScore by mutableStateOf("")
    var extraActivities by mutableStateOf("Tidak")
    var sleepHours by mutableStateOf("")
    var problemPracticed by mutableStateOf("")
    var resultText by mutableStateOf("")

    var errorMessage by mutableStateOf<String?>(null)

    private val _latestPrediction = MutableStateFlow<Prediction?>(null)
    val latestPrediction: StateFlow<Prediction?> = _latestPrediction

    fun predict() {
        val study = studyHours.toIntOrNull() ?: -1
        val score = prevScore.toIntOrNull() ?: -1
        val sleep = sleepHours.toIntOrNull() ?: -1
        val paper = problemPracticed.toIntOrNull() ?: -1

        val isValid = when {
            study == null || study < 1 || study > 9 -> {
                errorMessage = "Jam belajar harus antara 1–9"
                false
            }
            score == null || score < 1 || score > 100 -> {
                errorMessage = "Nilai sebelumnya harus antara 1–100"
                false
            }
            sleep == null || sleep < 1 || sleep > 9 -> {
                errorMessage = "Jam tidur harus antara 1–9"
                false
            }
            paper == null || paper < 1 || paper > 10 -> {
                errorMessage = "Jumlah soal latihan harus antara 1–10"
                false
            }
            else -> true
        }

        if (!isValid) return

        errorMessage = null

        val input = StudentInput(
            hoursStudied = study,
            previousScores = score,
            extracurricularActivities = if (extraActivities == "Ya") 1 else 0,
            sleepHours = sleep,
            sampleQuestionPapersPracticed = paper
        )

        viewModelScope.launch {
            try {
                Log.d("PredictViewModel", "Memulai prediksi dengan input: $input")

                val result = useCases.fetchPrediction(input)
                resultText = String.format("%.2f", result.predictedPerformanceIndex)

                _latestPrediction.value = Prediction(
                    id = 0,
                    studentInput = input,
                    predictedPerformanceIndex = result.predictedPerformanceIndex,
                    timestamp = System.currentTimeMillis()
                )

                Log.d("PredictViewModel", "Hasil prediksi: $resultText")
            } catch (e: Exception) {
                resultText = "Error: ${e.message}"
                Log.e("PredictViewModel", "Gagal melakukan prediksi", e)
            }
        }
    }

    fun savePrediction() {
        val prediction = _latestPrediction.value ?: return
        viewModelScope.launch {
            useCases.insertPrediction(prediction)
            Log.d("PredictViewModel", "Berhasil disimpan ke riwayat: $prediction")
        }
    }

    fun clearForm() {
        studyHours = ""
        prevScore = ""
        extraActivities = "Tidak"
        sleepHours = ""
        problemPracticed = ""
        resultText = ""
        errorMessage = null
        _latestPrediction.value = null
        Log.d("PredictViewModel", "Form di-reset")
    }
}


