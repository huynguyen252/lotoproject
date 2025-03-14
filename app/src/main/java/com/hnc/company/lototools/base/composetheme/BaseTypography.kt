

package com.hnc.company.lototools.base.composetheme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hnc.company.lototools.R

/**
 * Contains all the typography we provide for our components.
 *
 * @param large Used for big size, like the header of screen.
 * @param title1 Used for big titles, like the image attachment overlay text.
 * @param title3 Used for empty content text.
 * @param title3Bold Used for titles of app bars and bottom bars.
 * @param body Used for body content, such as messages.
 * @param bodyItalic Used for body content, italicized, like deleted message components.
 * @param bodyBold Used for emphasized body content, like small titles.
 * @param footnote Used for footnote information, like timestamps.
 * @param footnoteItalic Used for footnote information that's less important, like the deleted message text.
 * @param footnoteBold Used for footnote information in certain important items, like the thread reply text,
 * or user info components.
 * @param captionBold Used for unread count indicator.
 * @param singleEmoji Used for messages whose content consists only of a single emoji.
 * @param emojiOnly Used for messages whose content consists only if emojis.
 */
@Immutable
data class BaseTypography(
    val large: TextStyle,
    val large2: TextStyle,
    val title1: TextStyle,
    val title2: TextStyle,
    val title3: TextStyle,
    val title4: TextStyle,
    val title3Bold: TextStyle,
    val body: TextStyle,
    val body600: TextStyle,
    val bodyItalic: TextStyle,
    val bodyBold: TextStyle,
    val textNormal: TextStyle,
    val body1: TextStyle,
    val body1Regular: TextStyle,
    val body2: TextStyle,
    val body3: TextStyle,
    val small1: TextStyle,
    val footnote: TextStyle,
    val footnoteItalic: TextStyle,
    val footnoteBold: TextStyle,
    val captionBold: TextStyle,
    val tabBar: TextStyle,
    val singleEmoji: TextStyle,
    val emojiOnly: TextStyle,
    val buttonConfirmCancel: TextStyle,
    val bodyLarge: TextStyle,
    val headerLine: TextStyle,
    val titleLarge: TextStyle
) {

    companion object {
        fun defaultTypography(
            fontFamily: FontFamily? = null,
            semibold: FontFamily = FontFamily(
                Font(R.font.sf_pro_semibold)
            ),
            bold: FontFamily = FontFamily(
                Font(R.font.sf_pro_bold)
            ),
            medium: FontFamily = FontFamily(
                Font(R.font.sf_pro_medium)
            ),
            regular: FontFamily = FontFamily(
                Font(R.font.sf_pro_regular)
            ),
        ): BaseTypography = BaseTypography(
            large = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight.W600,
                fontFamily = bold,
            ),
            large2 = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.W500,
                fontFamily = semibold,
            ),
            title1 = TextStyle(
                fontSize = 20.sp,
                lineHeight = 34.sp,
                fontWeight = FontWeight.W500,
                fontFamily = medium,
            ),
            title2 = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight.W500,
                fontFamily = medium,
            ),
            title3 = TextStyle(
                fontSize = 18.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight.W400,
                fontFamily = regular,
            ),
            title4 = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.W500,
                fontFamily = medium,
            ),
            title3Bold = TextStyle(
                fontSize = 18.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight.W500,
                fontFamily = medium,
            ),
            body = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                fontFamily = regular,
            ),
            body600 = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.W600,
                fontFamily = fontFamily,
            ),
            bodyItalic = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                fontStyle = FontStyle.Italic,
                fontFamily = regular,
            ),
            bodyBold = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                fontFamily = medium,
            ),
            body1 = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.W500,
                fontFamily = medium,
            ),
            body1Regular = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.W400,
                fontFamily = regular,
            ),
            body2 = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.W400,
                fontFamily = regular,
            ),

            body3 = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.W500,
                fontFamily = medium,
            ),
            textNormal = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                fontFamily = fontFamily,
            ),
            small1 = TextStyle(
                fontSize = 5.sp,
                fontWeight = FontWeight.W400,
                fontFamily = regular,
            ),
            footnote = TextStyle(
                fontSize = 12.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.W400,
                fontFamily = regular,
            ),
            footnoteItalic = TextStyle(
                fontSize = 12.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.W400,
                fontStyle = FontStyle.Italic,
                fontFamily = regular,
            ),
            footnoteBold = TextStyle(
                fontSize = 12.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.W500,
                fontFamily = medium,
            ),
            captionBold = TextStyle(
                fontSize = 10.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.W700,
                fontFamily = semibold,
            ),
            tabBar = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.W400,
                fontFamily = regular,
            ),
            singleEmoji = TextStyle(
                fontSize = 50.sp,
                fontFamily = fontFamily,
            ),
            emojiOnly = TextStyle(
                fontSize = 50.sp,
                fontFamily = fontFamily,
            ),
            buttonConfirmCancel = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.W600,
                fontFamily = bold,
            ),
            bodyLarge = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.W600,
                fontFamily = semibold,
            ),
            headerLine = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
                fontFamily = semibold,
            ),
            titleLarge = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                fontFamily = semibold,
            ),
        )
    }
}
