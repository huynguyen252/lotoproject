package com.hnc.company.lototools.base.composetheme.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hnc.company.lototools.base.composetheme.BaseTheme
import com.hnc.company.lototools.base.composetheme.text.GPackageBaseText
import com.hnc.company.lototools.base.composetheme.text.TextType
import com.hnc.company.lototools.utils.rippleClick

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    text: String,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    paddingValues: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
    buttonColor: Color = Color(0xFF158C4D),
    textColor: Color = Color.White,
    onClick: () -> Unit = {}
) {
    GPackageBaseText(
        modifier = modifier
            .clip(shape)
            .background(
                shape = shape,
                color = buttonColor
            )
            .rippleClick(onClick = onClick)
            .padding(
                paddingValues = paddingValues
            ),
        text = text,
        type = TextType.BODY_MEDIUM,
        color = textColor,
        textAlign = TextAlign.Center
    )
}


@Preview
@Composable
private fun Preview() {
    BaseTheme {
        BaseButton(text = "Simple button")
    }
}
