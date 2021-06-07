package com.fox.alyxnews.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import com.fox.alyxnews.R


fun loadWithCoil(imageView: ImageView, imageUrl: String, circulate: Boolean = false) {
    imageView.load(imageUrl) {
        placeholder(R.drawable.ic_launcher_background)
        crossfade(true)
        crossfade(1000)
        if (circulate) transformations(CircleCropTransformation())
        listener(
            onError = { _, _ ->
                imageView.scaleType = ImageView.ScaleType.CENTER
                imageView.setImageResource(R.drawable.ic_error_404)
            }
        )
    }
}


fun loadWithCoil(imageView: ImageView, resId: Int, circulate: Boolean = false) {
    imageView.load(resId) {
        placeholder(R.drawable.ic_launcher_background)
        crossfade(true)
        crossfade(1000)
        if (circulate) transformations(CircleCropTransformation())
        listener(
            onError = { _, _ ->
                imageView.scaleType = ImageView.ScaleType.CENTER
                imageView.setImageResource(R.drawable.ic_error_404)
            }
        )
    }
}

fun loadWithCoil(imageView: ImageView, bitmap: Bitmap, circulate: Boolean = false) {
    imageView.load(bitmap) {
        placeholder(R.drawable.ic_launcher_background)
        crossfade(true)
        crossfade(1000)
        if (circulate) transformations(CircleCropTransformation())
        listener(
            onError = { _, _ ->
                imageView.scaleType = ImageView.ScaleType.CENTER
                imageView.setImageResource(R.drawable.ic_error_404)
            }
        )
    }
}

fun loadWithCoil(imageView: ImageView, uri: Uri, circulate: Boolean = false) {
    imageView.load(uri) {
        placeholder(R.drawable.ic_launcher_background)
        crossfade(true)
        crossfade(1000)
        if (circulate) transformations(CircleCropTransformation())
        listener(
            onError = { _, _ ->
                imageView.scaleType = ImageView.ScaleType.CENTER
                imageView.setImageResource(R.drawable.ic_error_404)
            }
        )
    }
}

fun loadWithCoil(imageView: ImageView, drawable: Drawable?, circulate: Boolean = false) {
    imageView.load(drawable) {
        placeholder(R.drawable.ic_launcher_background)
        crossfade(true)
        crossfade(1000)
        if (circulate) transformations(CircleCropTransformation())
        listener(
            onError = { _, _ ->
                imageView.scaleType = ImageView.ScaleType.CENTER
                imageView.setImageResource(R.drawable.ic_error_404)
            }
        )
    }
}