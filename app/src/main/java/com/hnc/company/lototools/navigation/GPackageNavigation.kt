package com.hnc.company.lototools.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hnc.company.lototools.R
import com.hnc.company.lototools.presentation.player.PlayerStatisticScreen
import com.hnc.company.lototools.presentation.result.XSMBResultScreen
import com.hnc.company.lototools.presentation.settings.SettingsScreen

sealed class BaseNavScreen(val route: String, val title: String? = null, val icon: Int? = null) {
    data object ResultScreen :
        BaseNavScreen("g_result_screen_route", title = "Kết quả", icon = R.drawable.ic_close)

    data object PlayerScreen :
        BaseNavScreen("g_player_screen_route", title = "Thống kê", icon = R.drawable.ic_close)

    data object SettingsScreen :
        BaseNavScreen("g_settings_screen_route", title = "Cài đặt", icon = R.drawable.ic_close)
}

fun NavGraphBuilder.createResultScreen(navController: NavController) {
    composable(
        BaseNavScreen.ResultScreen.route
    ) {
        XSMBResultScreen(navController)
    }
}

fun NavGraphBuilder.createPlayerScreen(navController: NavController) {
    composable(
        BaseNavScreen.PlayerScreen.route
    ) {
        PlayerStatisticScreen(navController)
    }
}

fun NavGraphBuilder.createSettingsScreen(navController: NavController) {
    composable(
        BaseNavScreen.SettingsScreen.route
    ) {
        SettingsScreen(navController)
    }
}
