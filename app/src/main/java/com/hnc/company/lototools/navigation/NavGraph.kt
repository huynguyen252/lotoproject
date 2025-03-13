package com.hnc.company.lototools.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(startDestination: String = BaseNavScreen.PlayerScreen.route, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        createResultScreen(navController)
        createPlayerScreen(navController)
        createSettingsScreen(navController)
    }

}
