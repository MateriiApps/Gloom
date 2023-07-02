package com.materiiapps.gloom.utils

import android.content.Context
import com.materiiapps.gloom.R
import kotlinx.datetime.Instant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

object TimeUtils {
    private const val SECOND_MILLIS = 1000L
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS
    private const val MONTH_MILLIS = 30 * DAY_MILLIS
    private const val YEAR_MILLIS = 12 * MONTH_MILLIS

    private const val DATE_FORMAT = "MMM d"

    fun Context.getTimeSince(date: Instant): String {
        val now = System.currentTimeMillis()
        val diff = now - date.toEpochMilliseconds()

        return when {
            diff < SECOND_MILLIS -> getString(R.string.time_now)
            diff < MINUTE_MILLIS -> getString(
                R.string.time_since_second,
                (diff.toFloat() / SECOND_MILLIS).roundToInt()
            )

            diff < HOUR_MILLIS -> getString(
                R.string.time_since_minute,
                (diff.toFloat() / MINUTE_MILLIS).roundToInt()
            )

            diff < DAY_MILLIS -> getString(
                R.string.time_since_hour,
                (diff.toFloat() / HOUR_MILLIS).roundToInt()
            )

            diff < MONTH_MILLIS -> getString(
                R.string.time_since_day,
                (diff.toFloat() / DAY_MILLIS).roundToInt()
            )

            diff < YEAR_MILLIS -> getString(
                R.string.time_since_month,
                (diff.toFloat() / MONTH_MILLIS).roundToInt()
            )

            diff >= YEAR_MILLIS -> getString(
                R.string.time_since_year,
                (diff.toFloat() / YEAR_MILLIS).roundToInt()
            )

            else -> "??"
        }
    }

    fun formatDate(instant: Instant): String =
        SimpleDateFormat(
            DATE_FORMAT,
            Locale.getDefault()
        ).format(Date(instant.toEpochMilliseconds()))
}