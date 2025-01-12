package dev.materii.gloom.util

expect class Logger() {

    fun error(tag: String, message: String?, throwable: Throwable?)

    fun warn(tag: String, message: String, throwable: Throwable?)

    fun info(tag: String, message: String)

    fun debug(tag: String, message: String)
}