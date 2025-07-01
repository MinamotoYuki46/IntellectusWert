package com.intellectuswert.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.intellectuswert.presentation.about.AboutScreen
import com.intellectuswert.presentation.dashboard.DashboardScreen
import com.intellectuswert.presentation.detail.DetailScreen
import com.intellectuswert.presentation.history.HistoryScreen
import com.intellectuswert.presentation.predict.PredictScreen

object ScreenRoute {
    const val DASHBOARD = "dashboard"
    const val PREDICT = "predict"
    const val HISTORY = "history"
    const val DETAIL = "detail/{id}"
    const val ABOUT = "about"

//    fun detailRoute(id: Int) = "detail/$id"
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = ScreenRoute.DASHBOARD,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
        }
    ) {
        composable(ScreenRoute.DASHBOARD) {
            DashboardScreen(navController = navController)
        }
        composable(ScreenRoute.PREDICT) {
            PredictScreen(navController = navController)
        }
        composable(ScreenRoute.HISTORY) {
            HistoryScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: -1
            DetailScreen(navController = navController, predictionId = id)
        }
        composable(ScreenRoute.ABOUT) {
            AboutScreen()
        }
    }
}
