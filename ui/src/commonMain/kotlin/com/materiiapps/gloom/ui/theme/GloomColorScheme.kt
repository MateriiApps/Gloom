package com.materiiapps.gloom.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

@Immutable
class GloomColorScheme(
    primary: Color,
    onPrimary: Color,
    primaryContainer: Color,
    onPrimaryContainer: Color,
    inversePrimary: Color,
    secondary: Color,
    onSecondary: Color,
    secondaryContainer: Color,
    onSecondaryContainer: Color,
    tertiary: Color,
    onTertiary: Color,
    tertiaryContainer: Color,
    onTertiaryContainer: Color,
    background: Color,
    onBackground: Color,
    surface: Color,
    onSurface: Color,
    surfaceVariant: Color,
    onSurfaceVariant: Color,
    surfaceTint: Color,
    inverseSurface: Color,
    inverseOnSurface: Color,
    error: Color,
    onError: Color,
    errorContainer: Color,
    onErrorContainer: Color,
    outline: Color,
    outlineVariant: Color,
    scrim: Color,
    surfaceBright: Color,
    surfaceDim: Color,
    surfaceContainer: Color,
    surfaceContainerHigh: Color,
    surfaceContainerHighest: Color,
    surfaceContainerLow: Color,
    surfaceContainerLowest: Color,

    //Custom colors
    statusGreen: Color,
    statusPurple: Color,
    statusRed: Color,
    statusGrey: Color,
    statusYellow: Color,
    warning: Color,
    onWarning: Color,
    warningContainer: Color,
    onWarningContainer: Color
) {

    constructor(
        colorScheme: ColorScheme,
        statusGreen: Color,
        statusPurple: Color,
        statusRed: Color,
        statusGrey: Color,
        statusYellow: Color,
        warning: Color,
        onWarning: Color,
        warningContainer: Color,
        onWarningContainer: Color
    ) : this(
        colorScheme.primary,
        colorScheme.onPrimary,
        colorScheme.primaryContainer,
        colorScheme.onPrimaryContainer,
        colorScheme.inversePrimary,
        colorScheme.secondary,
        colorScheme.onSecondary,
        colorScheme.secondaryContainer,
        colorScheme.onSecondaryContainer,
        colorScheme.tertiary,
        colorScheme.onTertiary,
        colorScheme.tertiaryContainer,
        colorScheme.onTertiaryContainer,
        colorScheme.background,
        colorScheme.onBackground,
        colorScheme.surface,
        colorScheme.onSurface,
        colorScheme.surfaceVariant,
        colorScheme.onSurfaceVariant,
        colorScheme.surfaceTint,
        colorScheme.inverseSurface,
        colorScheme.inverseOnSurface,
        colorScheme.error,
        colorScheme.onError,
        colorScheme.errorContainer,
        colorScheme.onErrorContainer,
        colorScheme.outline,
        colorScheme.outlineVariant,
        colorScheme.scrim,
        colorScheme.surfaceBright,
        colorScheme.surfaceDim,
        colorScheme.surfaceContainer,
        colorScheme.surfaceContainerHigh,
        colorScheme.surfaceContainerHighest,
        colorScheme.surfaceContainerLow,
        colorScheme.surfaceContainerLowest,
        statusGreen,
        statusPurple,
        statusRed,
        statusGrey,
        statusYellow,
        warning,
        onWarning,
        warningContainer,
        onWarningContainer
    )

    var primary by mutableStateOf(primary, structuralEqualityPolicy())
        internal set
    var onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
        internal set
    var primaryContainer by mutableStateOf(primaryContainer, structuralEqualityPolicy())
        internal set
    var onPrimaryContainer by mutableStateOf(onPrimaryContainer, structuralEqualityPolicy())
        internal set
    var inversePrimary by mutableStateOf(inversePrimary, structuralEqualityPolicy())
        internal set
    var secondary by mutableStateOf(secondary, structuralEqualityPolicy())
        internal set
    var onSecondary by mutableStateOf(onSecondary, structuralEqualityPolicy())
        internal set
    var secondaryContainer by mutableStateOf(secondaryContainer, structuralEqualityPolicy())
        internal set
    var onSecondaryContainer by mutableStateOf(onSecondaryContainer, structuralEqualityPolicy())
        internal set
    var tertiary by mutableStateOf(tertiary, structuralEqualityPolicy())
        internal set
    var onTertiary by mutableStateOf(onTertiary, structuralEqualityPolicy())
        internal set
    var tertiaryContainer by mutableStateOf(tertiaryContainer, structuralEqualityPolicy())
        internal set
    var onTertiaryContainer by mutableStateOf(onTertiaryContainer, structuralEqualityPolicy())
        internal set
    var background by mutableStateOf(background, structuralEqualityPolicy())
        internal set
    var onBackground by mutableStateOf(onBackground, structuralEqualityPolicy())
        internal set
    var surface by mutableStateOf(surface, structuralEqualityPolicy())
        internal set
    var onSurface by mutableStateOf(onSurface, structuralEqualityPolicy())
        internal set
    var surfaceVariant by mutableStateOf(surfaceVariant, structuralEqualityPolicy())
        internal set
    var onSurfaceVariant by mutableStateOf(onSurfaceVariant, structuralEqualityPolicy())
        internal set
    var surfaceTint by mutableStateOf(surfaceTint, structuralEqualityPolicy())
        internal set
    var inverseSurface by mutableStateOf(inverseSurface, structuralEqualityPolicy())
        internal set
    var inverseOnSurface by mutableStateOf(inverseOnSurface, structuralEqualityPolicy())
        internal set
    var error by mutableStateOf(error, structuralEqualityPolicy())
        internal set
    var onError by mutableStateOf(onError, structuralEqualityPolicy())
        internal set
    var errorContainer by mutableStateOf(errorContainer, structuralEqualityPolicy())
        internal set
    var onErrorContainer by mutableStateOf(onErrorContainer, structuralEqualityPolicy())
        internal set
    var outline by mutableStateOf(outline, structuralEqualityPolicy())
        internal set
    var outlineVariant by mutableStateOf(outlineVariant, structuralEqualityPolicy())
        internal set
    var scrim by mutableStateOf(scrim, structuralEqualityPolicy())
        internal set
    var surfaceBright by mutableStateOf(surfaceBright, structuralEqualityPolicy())
        internal set
    var surfaceDim by mutableStateOf(surfaceDim, structuralEqualityPolicy())
        internal set
    var surfaceContainer by mutableStateOf(surfaceContainer, structuralEqualityPolicy())
        internal set
    var surfaceContainerHigh by mutableStateOf(surfaceContainerHigh, structuralEqualityPolicy())
        internal set
    var surfaceContainerHighest by mutableStateOf(surfaceContainerHighest, structuralEqualityPolicy())
        internal set
    var surfaceContainerLow by mutableStateOf(surfaceContainerLow, structuralEqualityPolicy())
        internal set
    var surfaceContainerLowest by mutableStateOf(surfaceContainerLowest, structuralEqualityPolicy())
        internal set

    var statusGreen by mutableStateOf(statusGreen, structuralEqualityPolicy())
        internal set

    var statusPurple by mutableStateOf(statusPurple, structuralEqualityPolicy())
        internal set

    var statusRed by mutableStateOf(statusRed, structuralEqualityPolicy())
        internal set

    var statusGrey by mutableStateOf(statusGrey, structuralEqualityPolicy())
        internal set

    var statusYellow by mutableStateOf(statusYellow, structuralEqualityPolicy())
        internal set

    var warning by mutableStateOf(warning, structuralEqualityPolicy())
        internal set

    var onWarning by mutableStateOf(onWarning, structuralEqualityPolicy())
        internal set

    var warningContainer by mutableStateOf(warningContainer, structuralEqualityPolicy())
        internal set

    var onWarningContainer by mutableStateOf(onWarningContainer, structuralEqualityPolicy())
        internal set

    fun toColorScheme() = with(this) {
        ColorScheme(
            primary,
            onPrimary,
            primaryContainer,
            onPrimaryContainer,
            inversePrimary,
            secondary,
            onSecondary,
            secondaryContainer,
            onSecondaryContainer,
            tertiary,
            onTertiary,
            tertiaryContainer,
            onTertiaryContainer,
            background,
            onBackground,
            surface,
            onSurface,
            surfaceVariant,
            onSurfaceVariant,
            surfaceTint,
            inverseSurface,
            inverseOnSurface,
            error,
            onError,
            errorContainer,
            onErrorContainer,
            outline,
            outlineVariant,
            scrim,
            surfaceBright,
            surfaceDim,
            surfaceContainer,
            surfaceContainerHigh,
            surfaceContainerHighest,
            surfaceContainerLow,
            surfaceContainerLowest
        )
    }
}

val LocalColors = compositionLocalOf<GloomColorScheme> { error("No colors initialized") }

val MaterialTheme.colors: GloomColorScheme
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current