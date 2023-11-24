package com.materiiapps.gloom.ui.utils

import androidx.compose.runtime.Immutable

@Immutable
class UnsafeImmutableList<T>(private val list: List<T>) : List<T> by list

fun <T> List<T>.toImmutableList() = UnsafeImmutableList(this)