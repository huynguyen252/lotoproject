package com.hnc.company.lototools.base.composetheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Suppress("DEPRECATION_ERROR")
@Immutable
data class BaseColors(
    val primaryBasic: Color,
    val textBlack: Color,
    val secondaryRed: Color,
    val textSoft: Color,
    val strokeInput: Color,
    val textWhite: Color,
    val textDisable: Color,
    val textHintInputText: Color,
    val bgBgr: Color,
    val strokeStroke: Color,
    val textSubtext: Color,

    val primary: Color,
    val primary10: Color,
    val secondaryBlue: Color,
    val onPrimary: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textHint: Color,
    val textHint7a7a7a: Color,
    val dividers: Color,
    val dividers2: Color,
    val dividers3: Color,
    val divider9a9a9a: Color,
    val dividerLarge: Color,
    val background: Color,
    val backgroundF9F9F9: Color,
    val backgroundIconCloseGSocials: Color,
    val backgroundTooltip: Color,
    val backgroundActionProfile: Color,
    val backgroundLinkPage: Color,
    val line: Color,
    val backgroundSecondary: Color,
    val background3: Color,
    val backgroundDialog: Color,
    val line2: Color,
    val red: Color,
    val bgr2: Color,
    val buttonDisableColor: Color,
    val textStrockDropList: Color,
    val textOrangeColor: Color,
    val viewNumberColor: Color,
    val interactionNumberColor: Color,
    val consultNumberColor: Color,
    val transparentColor: Color,
    val buyNumberColor: Color,
    val backgroundIdLivestreamProduct: Color,
    val borderImgColor: Color,
    val increaseDarkChangeColor: Color,
    val borderTagColor: Color,
    val black: Color,
    val borderBlack: Color,
    val color5C5C5C: Color,
    val color5A5A5A: Color,
    val red2: Color,
    val purplePrimary: Color = Color(0xFF673AB7),
    val purpleDark: Color = Color(0xFF512DA8)
) {

    companion object {
        @Composable
        fun defaultColors(): BaseColors = BaseColors(
            primaryBasic = Color(0xFF158C4D),
            textBlack = Color(0xFF000000),
            secondaryRed = Color(0xFFD92616),
            textSoft = Color(0xFF888888),
            strokeInput = Color(0xFFDEDEDE),
            textWhite = Color(0xFFFFFFFF),
            textDisable = Color(0xFFC8C8C8),
            textHintInputText = Color(0xFF7A7A7A),
            bgBgr = Color(0xFFF6F6F6),
            strokeStroke = Color(0xFFEEEEEE),
            textSubtext = Color(0xFF5A5A5A),


            primary = Color(0xFF00904A),
            primary10 = Color(0x1A00904A),
            secondaryBlue = Color(0xFF0767DB),
            onPrimary = Color(0xFFFFFFFF),
            textPrimary = Color(0xFF000000),
            textSecondary = Color(0xFF5C5C5C),
            textHint = Color(0xFF808080),
            textHint7a7a7a = Color(0xFF7a7a7a),
            dividers = Color(0xFFE6E6E6),
            dividers2 = Color(0xFFE6E6E6),
            dividers3 = Color(0xFFF4F4F4),
            divider9a9a9a = Color(0xFF9a9a9a),
            background = Color(0xFFFFFFFF),
            backgroundF9F9F9 = Color(0xFFF9F9F9),
            backgroundIconCloseGSocials = Color(0xFFe9e9ea),
            backgroundActionProfile = Color(0xFFF6F6F6),
            backgroundLinkPage = Color(0xFFE5F4ED),
            line = Color(0xFFBDBDBD),
            backgroundSecondary = Color(0xFFF6F6F6),
            dividerLarge = Color(0xFFF1F1F1),
            background3 = Color(0x1A00904A),
            line2 = Color(0xFFE6E6E6),
            red = Color(0xFFE54545),
            backgroundDialog = Color(0xFFFFFFFF),
            backgroundTooltip = Color(0xFFFFFFFF),
            bgr2 = Color(0xFFFFFFFF),
            buttonDisableColor = Color(0xFFb3b3b3),
            textStrockDropList = Color(0xFFB3B3B3),
            textOrangeColor = Color(0xFFE57A17),
            viewNumberColor = Color(0xFF00CC69),
            interactionNumberColor = Color(0xFF00B85E),
            consultNumberColor = Color(0xFF00A655),
            buyNumberColor = Color(0xFF00904A),
            backgroundIdLivestreamProduct = Color(0xFFf4f4f4),
            borderImgColor = Color(0xFFe8e8e8),
            increaseDarkChangeColor = Color(0xFF00f57e),
            borderTagColor = Color(0xFFf8f8f8),
            transparentColor = Color(0x00FFFFFF),
            black = Color(0xFF000000),
            borderBlack = Color(0x33000000),
            color5C5C5C = Color(0xFF5C5C5C),
            color5A5A5A = Color(0xFF5A5A5A),
            red2 = Color(0xFFF73E2A),
        )

        @Composable
        fun defaultDarkColors(): BaseColors = BaseColors(
            primaryBasic = Color(0xFF158C4D),
            textBlack = Color(0xFF000000),
            secondaryRed = Color(0xFFD92616),
            textSoft = Color(0xFF888888),
            strokeInput = Color(0xFFDEDEDE),
            textWhite = Color(0xFFFFFFFF),
            textDisable = Color(0xFFC8C8C8),
            textHintInputText = Color(0xFF7A7A7A),
            bgBgr = Color(0xFFF6F6F6),
            strokeStroke = Color(0xFFEEEEEE),
            textSubtext = Color(0xFF5A5A5A),

            primary = Color(0xFF00904A),
            primary10 = Color(0x1A00904A),
            secondaryBlue = Color(0xFF0767DB),
            onPrimary = Color(0xFFFFFFFF),
            textPrimary = Color(0xFF000000),
            textSecondary = Color(0xFF5C5C5C),
            textHint = Color(0xFF808080),
            textHint7a7a7a = Color(0xFF7a7a7a),
            dividers = Color(0xFFE6E6E6),
            dividers2 = Color(0xFFE6E6E6),
            dividers3 = Color(0xFFF4F4F4),
            divider9a9a9a = Color(0xFF9a9a9a),
            background = Color(0xFFFFFFFF),
            backgroundF9F9F9 = Color(0xFFF9F9F9),
            backgroundIconCloseGSocials = Color(0xFFe9e9ea),
            backgroundActionProfile = Color(0xFFF6F6F6),
            backgroundLinkPage = Color(0xFFE5F4ED),
            line = Color(0xFFBDBDBD),
            backgroundSecondary = Color(0xFFF6F6F6),
            dividerLarge = Color(0xFFF1F1F1),
            background3 = Color(0x1A00904A),
            line2 = Color(0xFFE6E6E6),
            red = Color(0xFFE54545),
            backgroundDialog = Color(0xFFFFFFFF),
            backgroundTooltip = Color(0xFFFFFFFF),
            bgr2 = Color(0xFFFFFFFF),
            buttonDisableColor = Color(0xFFb3b3b3),
            textStrockDropList = Color(0xFFB3B3B3),
            textOrangeColor = Color(0xFFE57A17),
            viewNumberColor = Color(0xFF00CC69),
            interactionNumberColor = Color(0xFF00B85E),
            consultNumberColor = Color(0xFF00A655),
            buyNumberColor = Color(0xFF00904A),
            backgroundIdLivestreamProduct = Color(0xFFf4f4f4),
            borderImgColor = Color(0xFFe8e8e8),
            increaseDarkChangeColor = Color(0xFF00f57e),
            borderTagColor = Color(0xFFf8f8f8),
            transparentColor = Color(0x00FFFFFF),
            black = Color(0xFF000000),
            borderBlack = Color(0x33000000),
            color5C5C5C = Color(0xFF5C5C5C),
            color5A5A5A = Color(0xFF5A5A5A),
            red2 = Color(0xFFF73E2A),
        )
    }
}
