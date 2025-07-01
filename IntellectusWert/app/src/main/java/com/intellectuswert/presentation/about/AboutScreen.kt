package com.intellectuswert.presentation.about

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AboutScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Tentang Aplikasi",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Aplikasi ini memprediksi performa siswa berdasarkan beberapa faktor kunci yang relevan dengan proses belajar.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }

        item {
            Text(
                text = "🔍 Parameter yang Digunakan:",
                style = MaterialTheme.typography.titleMedium
            )
        }

        items(parameterList) { (title, description) ->
            ParameterItem(title, description)
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Dibuat menggunakan Kotlin, Jetpack Compose, dan FastAPI.",
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Versi 1.0",
                style = MaterialTheme.typography.bodyMedium
            )
        }



    }
}

private val parameterList = listOf(
    "📚 Jam Belajar" to "Total waktu belajar siswa dalam sehari.",
    "🏃 Aktivitas Ekstrakurikuler" to "Apakah siswa aktif dalam kegiatan ekstrakurikuler.",
    "🛌 Jam Tidur" to "Rata-rata durasi tidur siswa setiap malam.",
    "📝 Jumlah Paket Soal Latihan" to "Jumlah paket soal latihan yang telah dikerjakan.",
    "📈 Skor Sebelumnya" to "Nilai atau performa dari evaluasi sebelumnya."
)

@Composable
fun ParameterItem(title: String, description: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    MaterialTheme {
        AboutScreen()
    }
}
