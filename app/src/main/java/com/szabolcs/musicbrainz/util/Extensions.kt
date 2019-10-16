package com.szabolcs.musicbrainz.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import com.szabolcs.musicbrainz.R


fun View.showSnackbar(message: String, clickAction: () -> Unit) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        .setAction(R.string.retry) {
            clickAction()
        }.show()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
