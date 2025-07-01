package com.intellectuswert.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.intellectuswert.presentation.theme.SuccessGreen
import com.intellectuswert.presentation.theme.WarningYellow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    predictionId: Int,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val prediction by viewModel.prediction.collectAsState()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(predictionId) {
        viewModel.loadPrediction(predictionId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Prediksi") }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = { navController.popBackStack() }) {
                    Text("Kembali")
                }

                Button(onClick = { showDialog = true }) {
                    Text("Hapus")
                }
            }
        }
    ) { innerPadding ->
        prediction?.let { data ->
            val formattedDate = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
                .format(Date(data.timestamp))
            val score = data.predictedPerformanceIndex
            val resultColor = when {
                score >= 85 -> SuccessGreen
                score in 75.0..84.99 -> WarningYellow
                else -> MaterialTheme.colorScheme.error
            }

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ReadOnlyTextField("Tanggal", formattedDate)
                ReadOnlyTextField("Jam Belajar", "${data.studentInput.hoursStudied}")
                ReadOnlyTextField("Nilai Sebelumnya", "${data.studentInput.previousScores}")
                ReadOnlyTextField("Aktif Ekskul", if (data.studentInput.extracurricularActivities == 1) "Ya" else "Tidak")
                ReadOnlyTextField("Jam Tidur", "${data.studentInput.sleepHours}")
                ReadOnlyTextField("Jumlah paket soal latihan yang dikerjakan", "${data.studentInput.sampleQuestionPapersPracticed}")

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = resultColor.copy(alpha = 0.1f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Prediksi Nilai:",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = String.format("%.2f", score),
                            fontWeight = FontWeight.Bold,
                            fontSize = 60.sp,
                            style = MaterialTheme.typography.headlineLarge,
                            color = resultColor,
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus data ini?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deletePrediction(predictionId)
                        showDialog = false
                        navController.popBackStack()
                    }
                ) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
fun ReadOnlyTextField(label: String, value: String) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        modifier = Modifier.fillMaxWidth()
    )
}
