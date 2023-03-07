package com.example.pxh612_viewmodel_practice

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import timber.log.Timber

class KeyboardVisibility(context : Context) {

    var context : Context = context
    fun hideKeyboard(view: View) {
        Timber.v("hiding keyboard...")

        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

        Timber.v("hiding keyboard!")

    }


}
