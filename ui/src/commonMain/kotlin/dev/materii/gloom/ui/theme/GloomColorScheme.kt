package dev.materii.gloom.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

/**
 * Set of theme-able colors specific to us
 */
@Immutable
class GloomColorScheme(
    statusGreen: Color,
    statusPurple: Color,
    statusRed: Color,
    statusGrey: Color,
    statusYellow: Color,
    star: Color,
    warning: Color,
    onWarning: Color,
    warningContainer: Color,
    onWarningContainer: Color,
    alertNote: Color,
    alertTip: Color,
    alertImportant: Color,
    alertWarning: Color,
    alertCaution: Color,
) {

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

    var star by mutableStateOf(star, structuralEqualityPolicy())
        internal set

    var warning by mutableStateOf(warning, structuralEqualityPolicy())
        internal set

    var onWarning by mutableStateOf(onWarning, structuralEqualityPolicy())
        internal set

    var warningContainer by mutableStateOf(warningContainer, structuralEqualityPolicy())
        internal set

    var onWarningContainer by mutableStateOf(onWarningContainer, structuralEqualityPolicy())
        internal set

    var alertNote by mutableStateOf(alertNote, structuralEqualityPolicy())
        internal set

    var alertTip by mutableStateOf(alertTip, structuralEqualityPolicy())
        internal set

    var alertImportant by mutableStateOf(alertImportant, structuralEqualityPolicy())
        internal set

    var alertWarning by mutableStateOf(alertWarning, structuralEqualityPolicy())
        internal set

    var alertCaution by mutableStateOf(alertCaution, structuralEqualityPolicy())
        internal set

}

val LocalGloomColorScheme = compositionLocalOf<GloomColorScheme> { error("No colors initialized") }

val MaterialTheme.gloomColorScheme: GloomColorScheme
    @Composable
    @ReadOnlyComposable
    get() = LocalGloomColorScheme.current