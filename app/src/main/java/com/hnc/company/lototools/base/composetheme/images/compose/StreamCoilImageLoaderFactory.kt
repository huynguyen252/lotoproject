package com.hnc.company.lototools.base.composetheme.images.compose

import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.hnc.company.lototools.base.composetheme.images.StreamImageLoaderFactory

/**
 * A factory that creates new Coil [ImageLoader] instances.
 */
public fun interface StreamCoilImageLoaderFactory {

    /**
     * Returns a new Coil [ImageLoader].
     */
    public fun imageLoader(context: Context): ImageLoader

    public companion object {
        /**
         * Returns the default singleton instance of [StreamCoilImageLoaderFactory].
         *
         * @return The default implementation of [StreamCoilImageLoaderFactory].
         */
        public fun defaultFactory(): StreamCoilImageLoaderFactory {
            return DefaultStreamCoilImageLoaderFactory
        }
    }
}

/**
 * Provides a custom image loader that uses the [StreamImageLoaderFactory] to build the default loading settings.
 *
 * Gives support to load GIFs.
 */
internal object DefaultStreamCoilImageLoaderFactory : StreamCoilImageLoaderFactory {

    /**
     * Loads images in the app, with our default settings.
     */
    private var imageLoader: ImageLoader? = null

    /**
     * Provides an [ImageLoader] using our custom [ImageLoaderFactory].
     */
    private var imageLoaderFactory: ImageLoaderFactory? = null

    /**
     * Returns either the currently available [ImageLoader] or builds a new one.
     *
     * @param context - The [Context] to build the [ImageLoader] with.
     * @return [ImageLoader] that loads images in the app.
     */
    override fun imageLoader(context: Context): ImageLoader = imageLoader ?: newImageLoader(context)

    /**
     * Builds a new [ImageLoader] using the given Android [Context]. If the loader already exists, we return it.
     *
     * @param context - The [Context] to build the [ImageLoader] with.
     * @return [ImageLoader] that loads images in the app.
     */
    @Synchronized
    private fun newImageLoader(context: Context): ImageLoader {
        imageLoader?.let { return it }

        val imageLoaderFactory = imageLoaderFactory ?: newImageLoaderFactory(context)
        return imageLoaderFactory.newImageLoader().apply {
            imageLoader = this
        }
    }

    /**
     * Uses Android [Context] to build a new [StreamImageLoaderFactory].
     *
     * @param context - The context to load the factory with.
     * @return [ImageLoaderFactory] that loads all the images in the app.
     */
    private fun newImageLoaderFactory(context: Context): ImageLoaderFactory {
        return StreamImageLoaderFactory(context).apply {
            imageLoaderFactory = this
        }
    }
}
