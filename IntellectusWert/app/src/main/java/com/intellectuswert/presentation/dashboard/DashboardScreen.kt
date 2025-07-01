package com.intellectuswert.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intellectuswert.R

@Composable
fun DashboardScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "📊 Evaluasi Model Prediksi",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Model ini dilatih menggunakan data performa siswa dan dievaluasi dengan metrik regresi umum.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }

        items(statList) { stat ->
            StatCard(title = stat.first, value = stat.second)
        }


        item {
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.training_vs_validation_loss),
                contentDescription = "Train vs Validation Loss",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.actual_vs_predicted),
                contentDescription = "Actual vs Predicted",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
        }
    }
}

private val statList = listOf(
    "Test MAE" to "1.6296",
    "Test MSE" to "4.1973",
    "Test RMSE" to "2.0487",
    "R² Score" to "0.9887"
)

@Composable
fun StatCard(title: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = title, style = MaterialTheme.typography.labelMedium)
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}
