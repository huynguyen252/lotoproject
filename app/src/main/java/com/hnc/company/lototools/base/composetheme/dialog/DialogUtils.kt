package com.hnc.company.lototools.base.composetheme.dialog

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.DialogWindowProvider
import kotlin.math.roundToInt

@Composable
fun getDialogWindow(): Window? = (LocalView.current.parent as? DialogWindowProvider)?.window

@Composable
fun getActivityWindow(): Window? = LocalView.current.context.getActivityWindow()

private tailrec fun Context.getActivityWindow(): Window? = when (this) {
    is Activity -> window
    is ContextWrapper -> baseContext.getActivityWindow()
    else -> null
}

fun Context.getDisplayWidth(): Int{
    val density = resources.displayMetrics.density
    return (resources.configuration.screenWidthDp * density).roundToInt()
}

fun Context.getDisplayHeight(): Int{
    val density = resources.displayMetrics.density
    return (resources.configuration.screenHeightDp * density).roundToInt()
}

const val DefaultDurationMillis: Int = 250

