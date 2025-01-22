package dev.materii.gloom.ui.theme

import androidx.compose.ui.graphics.Color

fun darkGloomColorScheme() = GloomColorScheme(
    statusGreen = DarkGreen,
    statusPurple = LightPurple,
    statusRed = PinkRed,
    statusGrey = Grey,
    statusYellow = Yellow,
    star = Yellow,
    warning = YellowAlt1,
    onWarning = DarkBrown,
    warningContainer = DarkBronze,
    onWarningContainer = Shandy,
    alertNote = AlertNoteDark,
    alertTip = AlertTipDark,
    alertImportant = AlertImportantDark,
    alertWarning = AlertWarningDark,
    alertCaution = AlertCautionDark
)

fun lightGloomColorScheme() = GloomColorScheme(
    statusGreen = LightGreen,
    statusPurple = DarkPurple,
    statusRed = Red,
    statusGrey = LightGrey,
    statusYellow = Gold,
    star = Gold,
    warning = BronzeYellow,
    onWarning = Color.White,
    warningContainer = Shandy,
    onWarningContainer = DarkerBrown,
    alertNote = AlertNoteLight,
    alertTip = AlertTipLight,
    alertImportant = AlertImportantLight,
    alertWarning = AlertWarningLight,
    alertCaution = AlertCautionLight
)