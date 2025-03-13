package com.hnc.company.lototools.base.composetheme.images.internal

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import com.hnc.company.lototools.base.composetheme.images.disposable.Disposable

import com.hnc.company.lototools.base.composetheme.images.helper.ImageHeadersProvider


sealed interface StreamImageLoader {
    companion object {
        fun instance(): StreamImageLoader = CoilStreamImageLoader
    }

    public var imageHeadersProvider: ImageHeadersProvider

    @Suppress("LongParameterList")
    fun load(
        target: ImageView,
        data: Any?,
        @DrawableRes placeholderResId: Int? = null,
        transformation: ImageTransformation = ImageTransformation.None,
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
    ): Disposable

    @Suppress("LongParameterList")
    fun load(
        target: ImageView,
        data: Any?,
        placeholderDrawable: Drawable? = null,
        transformation: ImageTransformation = ImageTransformation.None,
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
    ): Disposable

    @Suppress("LongParameterList")
    suspend fun loadAndResize(
        target: ImageView,
        data: Any?,
        placeholderDrawable: Drawable? = null,
        transformation: ImageTransformation = ImageTransformation.None,
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
    )

    @Suppress("LongParameterList")
    public fun loadVideoThumbnail(
        target: ImageView,
        uri: Uri?,
        @DrawableRes placeholderResId: Int? = null,
        transformation: ImageTransformation = ImageTransformation.None,
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
    ): Disposable

    suspend fun loadAsBitmap(
        context: Context,
        url: String,
        transformation: ImageTransformation = ImageTransformation.None,
    ): Bitmap?

    sealed class ImageTransformation {
        object None : ImageTransformation() {
            override fun toString(): String = "None"
        }
        object Circle : ImageTransformation() {
            override fun toString(): String = "Circle"
        }
        data class RoundedCorners(@Px val radius: Float) : ImageTransformation()
    }
}
