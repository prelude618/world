package com.holiware.world1.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.holiware.world1.viewmodel.CoinViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    viewModel: CoinViewModel = koinViewModel()
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.MainScreen.route
    ) {
        composable(Screens.MainScreen.route) {
            MainScreen(
                viewModel = viewModel,
                onNavigateToDetail = { coin ->
                    navHostController.navigate("${Screens.DetailScreen.route}/${coin.id}")
                }
            )
        }
        composable(
            route = "${Screens.DetailScreen.route}/{coinId}",
            arguments = listOf(navArgument("coinId") { type = NavType.StringType })
        ) { backStackEntry ->
            val coinId = backStackEntry.arguments?.getString("coinId")
            val coins by viewModel.coinListState.collectAsStateWithLifecycle()
            val coin = coins.find { it.id == coinId }

            if (coin != null) {
                DetailScreen(
                    coin = coin,
                    onNavigateBack = { navHostController.popBackStack() }
                )
            } else {
                // Show a loading or error state instead of a blank screen
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
