package com.materiiapps.gloom.util

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.PluralFormatted
import dev.icerock.moko.resources.desc.StringDesc

actual fun getString(res: StringResource): String {
    return res.localized()
}

actual fun getString(res: StringResource, vararg args: Any): String {
    return res.localized(args = args)
}

actual fun getPluralString(res: PluralsResource, count: Int, vararg args: Any): String {
    return res.localized(quantity = count, args = args)
}

@Composable
actual fun pluralStringResource(res: PluralsResource, count: Int, vararg args: Any): String {
    return StringDesc.PluralFormatted(res, count, args).localized()
}