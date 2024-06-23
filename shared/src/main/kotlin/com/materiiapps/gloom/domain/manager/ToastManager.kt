package com.materiiapps.gloom.domain.manager

import android.content.Context
import android.widget.Toast

class ToastManager(
    private val context: Context
) {

    fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}