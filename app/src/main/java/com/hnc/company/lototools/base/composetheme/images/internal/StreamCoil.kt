package com.hnc.company.lototools.base.composetheme.images.internal

import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.ghtk.business.gpackage.base.composetheme.images.StreamImageLoaderFactory


public object StreamCoil {

    private var imageLoader: ImageLoader? = null
    private var imageLoaderFactory: ImageLoaderFactory? = null

    @Synchronized
    public fun setImageLoader(factory: ImageLoaderFactory) {
        imageLoaderFactory = factory
        imageLoader = null
    }

    internal fun imageLoader(context: Context): ImageLoader = imageLoader ?: newImageLoader(context)

    @Synchronized
    private fun newImageLoader(context: Context): ImageLoader {
        imageLoader?.let { return it }

        val imageLoaderFactory = imageLoaderFactory ?: newImageLoaderFactory(context)
        return imageLoaderFactory.newImageLoader().apply {
            imageLoader = this
        }
    }

    private fun newImageLoaderFactory(context: Context): ImageLoaderFactory {
        return StreamImageLoaderFactory(context).apply {
            imageLoaderFactory = this
        }
    }

    internal inline val Context.streamImageLoader: ImageLoader
        get() = imageLoader(this)
}
