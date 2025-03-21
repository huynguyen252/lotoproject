package com.hnc.company.lototools.base.composetheme.images

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.hnc.company.lototools.base.composetheme.images.disposable.Disposable
import com.hnc.company.lototools.base.composetheme.images.internal.StreamImageLoader
import com.hnc.company.lototools.base.composetheme.images.internal.StreamImageLoader.ImageTransformation

public fun ImageView.load(
    data: Any?,
    @DrawableRes placeholderResId: Int? = null,
    transformation: ImageTransformation = ImageTransformation.None,
    onStart: () -> Unit = {},
    onComplete: () -> Unit = {},
): Disposable {
    return StreamImageLoader.instance().load(
        target = this,
        data = data,
        placeholderResId = placeholderResId,
        transformation = transformation,
        onStart = onStart,
        onComplete = onComplete,
    )
}


public fun ImageView.load(
    data: Any?,
    placeholderDrawable: Drawable?,
    transformation: ImageTransformation = ImageTransformation.None,
    onStart: () -> Unit = {},
    onComplete: () -> Unit = {},
): Disposable {
    return StreamImageLoader.instance().load(
        target = this,
        data = data,
        placeholderDrawable = placeholderDrawable,
        transformation = transformation,
        onStart = onStart,
        onComplete = onComplete,
    )
}

/**
 * Loads an image into a drawable and then applies the drawable to the container, resizing it based on the scale types
 * and the given configuration.
 *
 * @param data The data to load.
 * @param placeholderDrawable Drawable that's shown while the image is loading.
 * @param transformation The transformation for the image before applying to the target.
 * @param onStart The callback when the load has started.
 * @param onComplete The callback when the load has finished.
 */
public suspend fun ImageView.loadAndResize(
    data: Any?,
    placeholderDrawable: Drawable?,
    transformation: ImageTransformation = ImageTransformation.None,
    onStart: () -> Unit = {},
    onComplete: () -> Unit = {},
) {
    StreamImageLoader.instance().loadAndResize(
        target = this,
        data = data,
        placeholderDrawable = placeholderDrawable,
        transformation = transformation,
        onStart = onStart,
        onComplete = onComplete,
    )
}

public fun ImageView.loadVideoThumbnail(
    uri: Uri?,
    @DrawableRes placeholderResId: Int? = null,
    transformation: ImageTransformation = ImageTransformation.None,
    onStart: () -> Unit = {},
    onComplete: () -> Unit = {},
): Disposable {
    return StreamImageLoader.instance().loadVideoThumbnail(
        target = this,
        uri = uri,
        placeholderResId = placeholderResId,
        transformation = transformation,
        onStart = onStart,
        onComplete = onComplete,
    )
}
