package com.materiiapps.gloom.ui.util

import java.text.NumberFormat

actual object NumberFormatter {

    actual fun compact(count: Int): String {
        return NumberFormat.getCompactNumberInstance().format(count)
    }

}