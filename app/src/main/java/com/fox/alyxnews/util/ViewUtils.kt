package com.fox.alyxnews.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Context.makeToast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.makeSnackbar(message: String) {
    Snackbar.make(
        (this as Activity).findViewById<View>(android.R.id.content),
        message,
        Snackbar.LENGTH_SHORT
    ).show()
}