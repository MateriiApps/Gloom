package com.materiiapps.gloom.utils

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
actual val supportsMonet = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

actual val Features = listOf(Feature.DYNAMIC_COLOR, Feature.INSTALL_APKS)