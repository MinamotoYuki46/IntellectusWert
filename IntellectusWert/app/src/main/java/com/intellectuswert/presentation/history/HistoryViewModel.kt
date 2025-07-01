package com.intellectuswert.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intellectuswert.domain.model.Prediction
import com.intellectuswert.domain.usecase.PredictionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val useCases: PredictionUseCases
) : ViewModel() {

    private val _predictions = MutableStateFlow<List<Prediction>>(emptyList())
    val predictions: StateFlow<List<Prediction>> = _predictions

    init {
        loadPredictions()
    }

    private fun loadPredictions() {
        viewModelScope.launch {
            useCases.getAllPredictions().collect { result ->
                _predictions.value = result
            }
        }
    }

    fun clearAllPredictions() {
        viewModelScope.launch {
            useCases.clearAllPredictions()
        }
    }

}
