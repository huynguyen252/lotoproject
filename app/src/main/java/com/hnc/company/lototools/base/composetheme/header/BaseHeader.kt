package com.hnc.company.lototools.base.composetheme.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hnc.company.lototools.base.composetheme.BaseTheme
import com.hnc.company.lototools.base.composetheme.text.BaseText
import com.hnc.company.lototools.base.composetheme.text.TextType
import com.hnc.company.lototools.utils.rippleClick
import com.hnc.company.lototools.R

@Composable
fun BaseHeader(
    modifier: Modifier = Modifier,
    title: String,
    showStartButton: Boolean = false,
    showEndButton: Boolean = true,
    onStartButtonClick: () -> Unit = { },
    onEndButtonClick: () -> Unit = {},
    startButton: @Composable BoxScope.(Modifier) -> Unit = {
        Image(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 12.dp)
                .size(28.dp)
                .then(it),
            imageVector = ImageVector.vectorResource(R.drawable.ic_back),
            contentDescription = "Back"
        )
    },
    endButton: @Composable BoxScope.(Modifier) -> Unit = {
        Image(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(horizontal = 12.dp)
                .size(24.dp)
                .then(it),
            imageVector = ImageVector.vectorResource(R.drawable.ic_close),
            contentDescription = "close"
        )
    },
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .background(BaseTheme.colors.backgroundSecondary)
    ) {
        if (showStartButton) {
            startButton(
                Modifier.rippleClick {
                    onStartButtonClick.invoke()
                }
            )
        }

        BaseText(
            text = title,
            modifier = Modifier.align(Alignment.Center),
            type = TextType.TITLE
        )

        if (showEndButton) {
            endButton(
                Modifier.rippleClick {
                    onEndButtonClick.invoke()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCustom() {
    BaseTheme{
        BaseHeader(
            title = "Custom Header",
            showStartButton = true,
            endButton = {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(horizontal = 12.dp)
                        .size(24.dp)
                        .rippleClick {},
                    imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                    contentDescription = "Back"
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDefault() {
    BaseTheme {
        BaseHeader(title = "Default Header")
    }
}
