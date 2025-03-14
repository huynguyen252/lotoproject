package com.hnc.company.lototools.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(
    startDestination: String = BaseNavScreen.PlayerScreen.route,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        createResultScreen(navController)
        createPlayerScreen(navController)
        createSettingsScreen(navController)
        createDetailPlayerScreen(navController)
    }

}
