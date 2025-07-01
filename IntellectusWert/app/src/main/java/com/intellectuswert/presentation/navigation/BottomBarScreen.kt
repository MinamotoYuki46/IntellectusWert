package com.intellectuswert.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Dashboard : BottomBarScreen("dashboard", "Dashboard", Icons.Default.BarChart)
    object Predict : BottomBarScreen("predict", "Prediksi", Icons.Default.Psychology)
    object History : BottomBarScreen("history", "Riwayat", Icons.Default.History)
    object About : BottomBarScreen("about", "Tentang", Icons.Default.Info)
}

sealed class Screen(val route: String) {
    object Detail : Screen("detail/{id}") {
        fun passId(id: Int) = "detail/$id"
    }
}
