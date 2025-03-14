package com.hnc.company.lototools.base.composetheme.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.hnc.company.lototools.base.composetheme.BaseTheme

enum class TextType {
    TITLE,
    BODY_MEDIUM,
    BODY,
    BODY_SMALL,
    BODY_LARGE,
    HEADER_LINE,
    TITLE_LARGE,
    BODY1,
    BODY2,
    BODY3,
}

@Composable
fun GPackageBaseText(
    text: String,
    modifier: Modifier = Modifier,
    type: TextType = TextType.BODY,
    color: Color = BaseTheme.colors.textBlack,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = 1,
    textAlign: TextAlign? = null,
) {
    val textStyle = when (type) {
        TextType.TITLE -> BaseTheme.typography.title2
        TextType.BODY_LARGE -> BaseTheme.typography.bodyLarge
        TextType.BODY_MEDIUM -> BaseTheme.typography.body1
        TextType.BODY3 -> BaseTheme.typography.body3
        TextType.BODY -> BaseTheme.typography.body1Regular
        TextType.BODY_SMALL -> BaseTheme.typography.body2
        TextType.HEADER_LINE -> BaseTheme.typography.headerLine
        TextType.TITLE_LARGE -> BaseTheme.typography.titleLarge
        TextType.BODY1 -> BaseTheme.typography.title1
        TextType.BODY2 -> BaseTheme.typography.title1
    }

    Text(
        text = text,
        style = textStyle,
        color = color,
        modifier = modifier,
        overflow = overflow,
        maxLines = maxLines,
        textAlign = textAlign,
    )
}

@Preview(showBackground = true)
@Composable
private fun BaseTextPreview() {
    BaseTheme {
        GPackageBaseText("BaseTextPreview")
    }
}
