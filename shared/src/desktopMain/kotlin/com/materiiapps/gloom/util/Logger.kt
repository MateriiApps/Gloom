package com.materiiapps.gloom.util

actual class Logger {

    actual fun error(tag: String, message: String?, throwable: Throwable?) {
        System.err.println(
            buildString {
                append("[$tag] [E] ")
                if(message != null) {
                    append(message)
                    throwable?.let {
                        appendLine(it.stackTraceToString())
                    }
                } else if (throwable != null) {
                    append(throwable.stackTraceToString())
                }
            }
        )
    }

    actual fun warn(tag: String, message: String, throwable: Throwable?) {
        println(
            buildString {
                append("[$tag] [W] ")
                append(message)
                throwable?.let {
                    appendLine(it.stackTraceToString())
                }
            }
        )
    }

    actual fun info(tag: String, message: String) {
        println(
            buildString {
                append("[$tag] [I] ")
                append(message)
            }
        )
    }

    actual fun debug(tag: String, message: String) {
        println(
            buildString {
                append("[$tag] [D] ")
                append(message)
            }
        )
    }

}