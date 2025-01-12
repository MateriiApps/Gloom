package dev.materii.gloom.util

import android.icu.number.Notation
import android.icu.number.NumberFormatter
import android.icu.text.CompactDecimalFormat
import android.os.Build
import java.text.DecimalFormat
import java.util.Locale

actual object NumberFormatter {

    actual fun compact(count: Int): String {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                NumberFormatter.withLocale(Locale.getDefault())
                    .notation(Notation.compactShort())
                    .format(count)
                    .toString()
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                CompactDecimalFormat.getInstance(
                    Locale.getDefault(),
                    CompactDecimalFormat.CompactStyle.SHORT
                )
                    .format(count)
            }

            else -> DecimalFormat().format(count)
        }
    }

}