package com.materiiapps.gloom.ui.theme

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme

@RequiresApi(Build.VERSION_CODES.S)
fun dynamicDarkTheme(context: Context) = GloomColorScheme(
    dynamicDarkColorScheme(context),
    statusGreen = DarkGreen,
    statusPurple = LightPurple,
    statusRed = PinkRed,
    statusGrey = Grey,
    statusYellow = Yellow
)

@RequiresApi(Build.VERSION_CODES.S)
fun dynamicLightTheme(context: Context) = GloomColorScheme(
    dynamicLightColorScheme(context),
    statusGreen = LightGreen,
    statusPurple = DarkPurple,
    statusRed = Red,
    statusGrey = LightGrey,
    statusYellow = Gold
)

fun darkTheme() = GloomColorScheme(
    darkColorScheme(),
    statusGreen = DarkGreen,
    statusPurple = LightPurple,
    statusRed = PinkRed,
    statusGrey = Grey,
    statusYellow = Yellow
)

fun lightTheme() = GloomColorScheme(
    lightColorScheme(),
    statusGreen = LightGreen,
    statusPurple = DarkPurple,
    statusRed = Red,
    statusGrey = LightGrey,
    statusYellow = Gold
)