package com.materiiapps.gloom.utils

import android.os.Build
import android.os.Environment
import androidx.annotation.ChecksSdkIntAtLeast
import com.materiiapps.gloom.shared.BuildConfig
import java.io.File

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
val supportsMonet = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
val isDebug = BuildConfig.DEBUG

val GloomPath = File(Environment.getExternalStorageDirectory(), "Gloom")
val Features = listOf(Feature.DYNAMIC_COLOR, Feature.INSTALL_APKS)