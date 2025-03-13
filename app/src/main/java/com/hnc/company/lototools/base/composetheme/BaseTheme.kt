package com.hnc.company.lototools.base.composetheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.hnc.company.lototools.base.composetheme.images.compose.LocalStreamImageLoader
import com.hnc.company.lototools.base.composetheme.images.compose.StreamCoilImageLoaderFactory

/**
 * Local providers for various properties we connect to our components, for styling.
 */
private val LocalColors = compositionLocalOf<BaseColors> {
    error("No colors provided! Make sure to wrap all usages of Stream components in a ChatTheme.")
}
private val LocalDimens = compositionLocalOf<BaseDimens> {
    error("No dimens provided! Make sure to wrap all usages of Stream components in a ChatTheme.")
}
private val LocalTypography = compositionLocalOf<BaseTypography> {
    error(
        "No typography provided! Make sure to wrap all usages of Stream components in a ChatTheme."
    )
}

@Composable
fun BaseTheme(
    isInDarkMode: Boolean = false,
    colors: BaseColors = if (false) BaseColors.defaultDarkColors() else BaseColors.defaultColors(),
    dimens: BaseDimens = BaseDimens.defaultDimens(),
    typography: BaseTypography = BaseTypography.defaultTypography(),
    imageLoaderFactory: StreamCoilImageLoaderFactory = StreamCoilImageLoaderFactory.defaultFactory(),
    content: @Composable () -> Unit,
) {
    LaunchedEffect(Unit) {
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalDimens provides dimens,
        LocalTypography provides typography,
        LocalStreamImageLoader provides
            imageLoaderFactory.imageLoader(LocalContext.current.applicationContext),
    ) {
        content()
    }
}

/**
 * Contains ease-of-use accessors for different properties used to style and customize the app
 * look and feel.
 */
object BaseTheme {
    /**
     * Retrieves the current [BaseColors] at the call site's position in the hierarchy.
     */
    val colors: BaseColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    /**
     * Retrieves the current [BaseDimens] at the call site's position in the hierarchy.
     */
    val dimens: BaseDimens
        @Composable
        @ReadOnlyComposable
        get() = LocalDimens.current

    /**
     * Retrieves the current [BaseTypography] at the call site's position in the hierarchy.
     */
    val typography: BaseTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}
