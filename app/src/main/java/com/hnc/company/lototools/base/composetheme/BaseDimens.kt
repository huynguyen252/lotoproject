package com.hnc.company.lototools.base.composetheme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
public data class BaseDimens(
    val largePadding: Dp,
    val mediumPadding: Dp,
    val smallPadding: Dp,
    val toolbarHeight: Dp,
    val dividerHeight: Dp
) {

    companion object {
        /**
         * Builds the default dimensions for our theme.
         *
         * @return A [BaseDimens] instance holding our default dimensions.
         */
        fun defaultDimens(): BaseDimens = BaseDimens(
            largePadding = 20.dp,
            mediumPadding = 16.dp,
            smallPadding = 4.dp,
            toolbarHeight = 56.dp,
            dividerHeight = 0.5.dp
        )
    }
}
