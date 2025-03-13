package com.hnc.company.lototools.base.composetheme.wheelpicker

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hnc.company.lototools.base.composetheme.BaseTheme

/**
 * The default implementation of focus view in vertical.
 */
@Composable
fun FWheelPickerFocusVertical(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(BaseTheme.colors.dividerLarge)
    ) {

    }
}

/**
 * The default implementation of focus view in horizontal.
 */
@Composable
fun FWheelPickerFocusHorizontal(
    modifier: Modifier = Modifier,
    dividerSize: Dp = 1.dp,
    dividerColor: Color = DefaultDividerColor,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .background(dividerColor)
                .width(dividerSize)
                .fillMaxHeight()
                .align(Alignment.CenterStart),
        )
        Box(
            modifier = Modifier
                .background(dividerColor)
                .width(dividerSize)
                .fillMaxHeight()
                .align(Alignment.CenterEnd),
        )
    }
}

/**
 * Default divider color.
 */
private val DefaultDividerColor: Color
    @Composable
    get() {
        val color = if (isSystemInDarkTheme()) Color.White else Color.Black
        return color.copy(alpha = 0.2f)
    }

/**
 * Default display.
 */
@Composable
fun FWheelPickerDisplayScope.DefaultWheelPickerDisplay(index: Int,) {
    val focused = index == state.currentIndexSnapshot
    val targetScale = if (focused) 1.0f else 0.8f
    val animateScale by animateFloatAsState(targetScale, label = "")
    Box(
        modifier = Modifier.graphicsLayer {
            this.alpha = if (focused) 1.0f else 0.3f
            this.scaleX = animateScale
            this.scaleY = animateScale
        }
    ) {
        Content(index)
    }
}
