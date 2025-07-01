package com.intellectuswert.presentation.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.intellectuswert.domain.model.Prediction
import com.intellectuswert.presentation.navigation.Screen
import com.intellectuswert.presentation.theme.SuccessGreen
import com.intellectuswert.presentation.theme.WarningYellow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val predictions by viewModel.predictions.collectAsState()
    var showConfirmDialog by remember { mutableStateOf(false) }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Hapus Semua Riwayat") },
            text = { Text("Apakah Anda yakin ingin menghapus semua data prediksi?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.clearAllPredictions()
                    showConfirmDialog = false
                }) {
                    Text("Ya")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Prediksi") },
                actions = {
                    if (predictions.isNotEmpty()) {
                        IconButton(onClick = { showConfirmDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Hapus semua"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (predictions.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Belum ada data prediksi.")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(predictions) { prediction ->
                        PredictionHistoryCard(
                            prediction,
                            onClick = {
                                navController.navigate(Screen.Detail.passId(prediction.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PredictionHistoryCard(prediction: Prediction, onClick: () -> Unit) {
    val date = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        .format(Date(prediction.timestamp))

    val score = prediction.predictedPerformanceIndex
    val resultColor = when {
        score >= 85 -> SuccessGreen
        score in 75.0..84.99 -> WarningYellow
        else -> MaterialTheme.colorScheme.error
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = resultColor.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Tanggal: $date",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = String.format("%.2f", score),
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = resultColor,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}
