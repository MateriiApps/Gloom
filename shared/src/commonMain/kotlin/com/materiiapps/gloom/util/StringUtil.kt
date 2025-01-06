package com.materiiapps.gloom.util

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource

expect fun getString(res: StringResource): String

expect fun getString(res: StringResource, vararg args: Any): String

expect fun getPluralString(res: PluralsResource, count: Int, vararg args: Any): String

@Composable
expect fun pluralStringResource(res: PluralsResource, count: Int, vararg args: Any): String