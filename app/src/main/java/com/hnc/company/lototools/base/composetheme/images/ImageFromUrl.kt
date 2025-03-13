package com.hnc.company.lototools.base.composetheme.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.hnc.company.lototools.R
import com.hnc.company.lototools.base.composetheme.BaseTheme


@Composable
fun ImageFromUrl(
    modifier: Modifier = Modifier,
    url: String = "",
    radiusBorder: Dp = 8.dp,
    size: Dp = 40.dp,
    borderWidth: Dp = 0.5.dp,
    borderColor: Color = BaseTheme.colors.transparentColor,
    placeHolderImgWidth: Dp = 27.dp
) {
    if (url.isEmpty()) {
        Box(
            modifier = modifier
                .size(size)
                .background(
                    color = BaseTheme.colors.backgroundSecondary,
                    shape = RoundedCornerShape(radiusBorder)
                )
                .clip(RoundedCornerShape(radiusBorder))
                .border(
                    color = borderColor,
                    width = borderWidth,
                    shape = RoundedCornerShape(radiusBorder)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_default_image),
                contentDescription = "",
                modifier = Modifier.size(placeHolderImgWidth),
            )
        }
    } else {
        Image(
            painter = rememberAsyncImagePainter(url),
            contentDescription = null,
            modifier = modifier
                .size(size)
                .clip(RoundedCornerShape(radiusBorder))
                .border(
                    color = borderColor,
                    width = borderWidth,
                    shape = RoundedCornerShape(radiusBorder)
                ),
            contentScale = ContentScale.Crop,
        )
    }
}
