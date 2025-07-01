package com.intellectuswert.presentation.detail

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
class DetailViewModel @Inject constructor(
    private val useCases: PredictionUseCases
) : ViewModel() {

    private val _prediction = MutableStateFlow<Prediction?>(null)
    val prediction: StateFlow<Prediction?> = _prediction

    fun loadPrediction(id: Int) {
        viewModelScope.launch {
            val result = useCases.getPredictionById(id)
            _prediction.value = result
        }
    }

    fun deletePrediction(id: Int) {
        viewModelScope.launch {
            useCases.deletePrediction(id)
        }
    }
}
