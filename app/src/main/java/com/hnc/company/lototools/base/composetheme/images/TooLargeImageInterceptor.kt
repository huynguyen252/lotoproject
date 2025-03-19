package com.hnc.company.lototools.base.composetheme.images

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import coil.intercept.Interceptor
import coil.request.ErrorResult
import coil.request.ImageResult
import coil.request.SuccessResult

class TooLargeImageInterceptor : Interceptor {
    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        return when (val result = chain.proceed(chain.request)) {
            is ErrorResult -> result
            is SuccessResult -> {
                val width = result.drawable.intrinsicWidth
                val height = result.drawable.intrinsicHeight
                val sumPixels = width * height

                if (sumPixels > MAX_PIXELS) {
                    try {
                        // Calculate the scale factor
                        val scaleFactor = kotlin.math.sqrt(MAX_PIXELS.toDouble() / sumPixels)
                        val newWidth = (width * scaleFactor).toInt()
                        val newHeight = (height * scaleFactor).toInt()

                        // Resize the bitmap
                        val bitmap = (result.drawable as BitmapDrawable).bitmap
                        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
                        val resizedDrawable = BitmapDrawable(chain.request.context.resources, resizedBitmap)
                        Log.d("longvn", "intercept: ${bitmap.byteCount} -> ${resizedBitmap.byteCount}")
                        return SuccessResult(
                            drawable = resizedDrawable,
                            request = chain.request,
                            dataSource = result.dataSource,
                            isSampled = result.isSampled
                        )
                    }catch (e: Exception){
                        e.printStackTrace()
                        result
                    }

                } else {
                    result
                }
            }
        }
    }

    companion object {
        const val MAX_PIXELS = 1000 * 1000
    }
}
