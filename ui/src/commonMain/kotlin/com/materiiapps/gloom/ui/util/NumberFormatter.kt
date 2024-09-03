package com.materiiapps.gloom.ui.util

expect object NumberFormatter {

    /**
     * Formats the given [count] into a localized compact format
     *
     * Ex.
     *
     * 1102 -> 1.1K
     *
     * 12352210 -> 12.4M
     *
     * @param count The number to make compact
     * @return A compact version of a number
     */
    fun compact(count: Int): String

}