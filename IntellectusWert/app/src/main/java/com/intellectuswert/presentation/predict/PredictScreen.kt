package com.intellectuswert.presentation.predict

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.intellectuswert.presentation.theme.SuccessGreen
import com.intellectuswert.presentation.theme.WarningYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PredictScreen(navController: NavController, viewModel: PredictViewModel = hiltViewModel()) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    val backgroundColor = MaterialTheme.colorScheme.background

    SideEffect {
        systemUiController.setStatusBarColor(backgroundColor, darkIcons = useDarkIcons)
        systemUiController.setNavigationBarColor(backgroundColor, darkIcons = useDarkIcons)
    }

    val resultText = viewModel.resultText

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Prediksi Performa") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = viewModel.studyHours,
                onValueChange = { viewModel.studyHours = it },
                label = { Text("Jam belajar") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = viewModel.prevScore,
                onValueChange = { viewModel.prevScore = it },
                label = { Text("Nilai sebelumnya") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            extraActivitiesDropdown(
                selectedValue = viewModel.extraActivities,
                onValueChange = { viewModel.extraActivities = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = viewModel.sleepHours,
                onValueChange = { viewModel.sleepHours = it },
                label = { Text("Jam tidur") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = viewModel.problemPracticed,
                onValueChange = { viewModel.problemPracticed = it },
                label = { Text("Jumlah paket soal latihan yang dikerjakan") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            viewModel.errorMessage?.let { message ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.predict() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Prediksi")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { viewModel.savePrediction() },
                    modifier = Modifier.weight(1f),
                    enabled = resultText.toDoubleOrNull() != null
                ) {
                    Text("Tambah ke Riwayat")
                }

                OutlinedButton(
                    onClick = { viewModel.clearForm() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reset Form")
                }
            }

            if (resultText.isNotBlank()) {
                val score = resultText.toDoubleOrNull()
                val resultColor = when {
                    score == null -> MaterialTheme.colorScheme.error
                    score >= 85 -> SuccessGreen
                    score in 75.0..84.99 -> WarningYellow
                    else -> MaterialTheme.colorScheme.error
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = resultColor.copy(alpha = 0.1f)),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Prediksi Nilai:",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = resultText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 60.sp,
                            textAlign = TextAlign.Start,
                            color = resultColor,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun extraActivitiesDropdown(
    selectedValue: String,
    onValueChange: (String) -> Unit
) {
    val options = listOf("Ya", "Tidak")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text("Ikut Ekstrakurikuler") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
