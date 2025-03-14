package com.hnc.company.lototools.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hnc.company.lototools.base.composetheme.BaseTheme
import com.hnc.company.lototools.base.composetheme.scafold.BaseScaffold
import com.hnc.company.lototools.base.composetheme.text.GPackageBaseText
import com.hnc.company.lototools.navigation.BaseNavScreen
import com.hnc.company.lototools.navigation.NavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaseTheme {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = MaterialTheme.colorScheme.background.luminance() > 0.5f
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color(0xFF673AB7),
                        darkIcons = useDarkIcons
                    )
                }
                val navController = rememberNavController()
                BaseScaffold(
                    modifier = Modifier.systemBarsPadding(),
                    topBarView = { },
                    bottomBar = { BottomNavigationBar(navController = navController) },
                    content = { innerPadding ->
                        NavGraph(
                            BaseNavScreen.PlayerScreen.route,
                            navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                )
            }
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BaseNavScreen.PlayerScreen,
        BaseNavScreen.ResultScreen
    )
    NavigationBar(
        containerColor = BaseTheme.colors.purpleDark,
        contentColor = BaseTheme.colors.textWhite
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(screen.icon ?: 0),
                        contentDescription = screen.title,
                        tint = if (isSelected) BaseTheme.colors.textWhite else BaseTheme.colors.black,
                        modifier = Modifier.size(if (isSelected) 30.dp else 24.dp)
                    )
                },
                label = {
                    val textColor = if (currentRoute == screen.route) {
                        BaseTheme.colors.textWhite
                    } else {
                        BaseTheme.colors.black
                    }
                    GPackageBaseText(text = screen.title ?: "", color = textColor)
                },
                selected = currentRoute == screen.route,
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = BaseTheme.colors.textWhite,
                    unselectedTextColor = BaseTheme.colors.black,
                    indicatorColor = Color.Transparent
                ),
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}