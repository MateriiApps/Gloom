package com.materiiapps.gloom.util

import android.os.Build
import android.os.Environment
import androidx.annotation.ChecksSdkIntAtLeast
import com.materiiapps.gloom.shared.BuildConfig
import java.io.File

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
actual val supportsMonet = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
actual val isDebug = BuildConfig.DEBUG

actual val GloomPath = File(Environment.getExternalStorageDirectory(), "Gloom")
actual val Features = listOf(Feature.DYNAMIC_COLOR, Feature.INSTALL_APKS)


