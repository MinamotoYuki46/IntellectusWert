package com.intellectuswert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.intellectuswert.presentation.navigation.BottomBarScreen
import com.intellectuswert.presentation.navigation.NavigationGraph
import com.intellectuswert.presentation.theme.IntellectusWertTheme

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntellectusWertLaunch()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntellectusWertLaunch() {
    IntellectusWertTheme {
        val navController = rememberNavController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry?.destination?.route

        val bottomBarScreens = listOf(
            BottomBarScreen.Dashboard,
            BottomBarScreen.Predict,
            BottomBarScreen.History,
            BottomBarScreen.About
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = when (currentDestination) {
                            BottomBarScreen.Dashboard.route -> "Dashboard"
                            BottomBarScreen.Predict.route -> "Prediksi"
                            BottomBarScreen.History.route -> "Riwayat"
                            BottomBarScreen.About.route -> "Tentang"
                            else -> "INTELLECTUS"
                        })
                    }
                )
            },
            bottomBar = {
                if (bottomBarScreens.any { it.route == currentDestination }) {
                    BottomNavigationBar(navController, bottomBarScreens)
                }
            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier.padding(innerPadding),
                color = MaterialTheme.colorScheme.background
            ) {
                NavigationGraph(navController = navController)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, items: List<BottomBarScreen>) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            val icon = when (screen.route) {
                BottomBarScreen.Dashboard.route -> Icons.Default.Home
                BottomBarScreen.Predict.route -> Icons.Default.Assessment
                BottomBarScreen.History.route -> Icons.Default.History
                BottomBarScreen.About.route -> Icons.Default.Info
                else -> Icons.Default.Home
            }

            NavigationBarItem(
                icon = { Icon(icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
