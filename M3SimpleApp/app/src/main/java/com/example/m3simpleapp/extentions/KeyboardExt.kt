package com.example.m3simpleapp.extentions

import android.app.Activity
import android.view.inputmethod.InputMethodManager

internal fun Activity.hideKeyboard() {
    this.currentFocus?.let {
        val inputMethodManager =
            this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}