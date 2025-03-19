package com.hnc.company.lototools.utils

import android.net.Uri
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions

fun NavController.to(
    deeplink: Uri? = null,
    destination: String? = null,
    isSingleTop: Boolean = false,
    bundle: Bundle? = null,
    navOptions: NavOptions = NavOptions.Builder().setLaunchSingleTop(isSingleTop).build()
) {
    deeplink?.let {
        navigate(
            deepLink = it,
            navOptions = navOptions
        )
    }
    destination?.let {
        bundle?.let {
            currentBackStackEntry?.arguments?.putAll(bundle)
        }
        navigate(route = it) {
            launchSingleTop = isSingleTop
        }
    }
}

fun NavController.popUpTo(destination: String) = navigate(destination) {
    popUpTo(graph.findStartDestination().id) {
        saveState = true
    }
    // Restore state when reselecting a previously selected item
    restoreState = true
}

fun NavController.popUpTo(
    route: String,
    @IdRes id: Int
) = navigate(route) {
    popUpTo(id) {
        inclusive = true
        saveState = true
    }
    restoreState = true
}
