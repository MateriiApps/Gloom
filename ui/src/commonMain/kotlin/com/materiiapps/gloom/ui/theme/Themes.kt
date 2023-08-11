package com.materiiapps.gloom.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme


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