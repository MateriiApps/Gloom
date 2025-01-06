package com.materiiapps.gloom.util

import java.text.NumberFormat

actual object NumberFormatter {

    actual fun compact(count: Int): String {
        return NumberFormat.getCompactNumberInstance().format(count)
    }

}