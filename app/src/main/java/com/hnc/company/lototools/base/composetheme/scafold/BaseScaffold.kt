package com.hnc.company.lototools.base.composetheme.scafold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hnc.company.lototools.base.composetheme.BaseTheme
import com.hnc.company.lototools.utils.surfaceColorAtElevation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScaffold(
    modifier: Modifier,
    containerColor: Color = BaseTheme.colors.backgroundSecondary,
    topBarTonalElevation: Dp = 0.dp,
    containerTonalElevation: Dp = 0.dp,
    topBarView: @Composable () -> Unit = {},
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    floatingActionButton: (@Composable () -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit = {}
) {
    Scaffold(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.surfaceColorAtElevation(
                    topBarTonalElevation,
                    color = containerColor
                )
            ),
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
            containerTonalElevation,
            color = containerColor
        ),
        topBar = {
            if (navigationIcon != null || actions != null) {
                TopAppBar(
                    title = {},
                    navigationIcon = { navigationIcon?.invoke() },
                    actions = { actions?.invoke(this) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                    )
                )
            }else{
                topBarView.invoke()
            }
        },
        content = {
            content(it)
        },
        bottomBar = { bottomBar?.invoke() },
        floatingActionButton = { floatingActionButton?.invoke() },
    )
}
