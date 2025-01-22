package dev.materii.gloom.ui.util.markdown

import androidx.compose.material3.ColorScheme
import dev.materii.gloom.ui.theme.CodeTheme
import dev.materii.gloom.ui.theme.GloomColorScheme
import dev.materii.gloom.ui.util.hexCode

object MarkdownUtil {

    /**
     * Replaces the color variables in the markdown template with the runtime theme colors
     *
     * @see `shared/src/commonMain/moko-resources/assets/markdown/markdown.html`
     *
     * @param markdownTemplate The template text where the color variables are, should be lazy loaded at runtime.
     * @param isLight Whether or not the theme is designed to be light.
     * @param colorScheme The Material3 color scheme used to theme markdown elements
     */
    fun injectAppTheme(
        markdownTemplate: String,
        isLight: Boolean,
        colorScheme: ColorScheme,
        gloomColorScheme: GloomColorScheme,
        codeTheme: CodeTheme
    ): String {
        return markdownTemplate

            // Material 3 =====================================================================================
            .replace("\$primary$", "#" + colorScheme.primary.hexCode)
            .replace("\$onPrimary$", "#" + colorScheme.onPrimary.hexCode)
            .replace("\$primaryContainer$", "#" + colorScheme.primaryContainer.hexCode)
            .replace("\$onPrimaryContainer$", "#" + colorScheme.onPrimaryContainer.hexCode)
            .replace("\$inversePrimary$", "#" + colorScheme.inversePrimary.hexCode)

            .replace("\$secondary$", "#" + colorScheme.secondary.hexCode)
            .replace("\$onSecondary$", "#" + colorScheme.onSecondary.hexCode)
            .replace("\$secondaryContainer$", "#" + colorScheme.secondaryContainer.hexCode)
            .replace("\$onSecondaryContainer$", "#" + colorScheme.onSecondaryContainer.hexCode)

            .replace("\$tertiary$", "#" + colorScheme.tertiary.hexCode)
            .replace("\$onTertiary$", "#" + colorScheme.onTertiary.hexCode)
            .replace("\$tertiaryContainer$", "#" + colorScheme.tertiaryContainer.hexCode)
            .replace("\$onTertiaryContainer$", "#" + colorScheme.onTertiaryContainer.hexCode)

            .replace("\$background$", "#" + colorScheme.background.hexCode)
            .replace("\$onBackground$", "#" + colorScheme.onBackground.hexCode)

            .replace("\$surface$", "#" + colorScheme.surface.hexCode)
            .replace("\$onSurface$", "#" + colorScheme.onSurface.hexCode)
            .replace("\$surfaceVariant$", "#" + colorScheme.surfaceVariant.hexCode)
            .replace("\$onSurfaceVariant$", "#" + colorScheme.onSurfaceVariant.hexCode)
            .replace("\$surfaceTint$", "#" + colorScheme.surfaceTint.hexCode)
            .replace("\$inverseSurface$", "#" + colorScheme.inverseSurface.hexCode)
            .replace("\$inverseOnSurface$", "#" + colorScheme.inverseOnSurface.hexCode)

            .replace("\$error$", "#" + colorScheme.error.hexCode)
            .replace("\$onError$", "#" + colorScheme.onError.hexCode)
            .replace("\$errorContainer$", "#" + colorScheme.errorContainer.hexCode)
            .replace("\$onErrorContainer$", "#" + colorScheme.onErrorContainer.hexCode)

            .replace("\$outline$", "#" + colorScheme.outline.hexCode)
            .replace("\$outlineVariant$", "#" + colorScheme.outlineVariant.hexCode)
            .replace("\$scrim$", "#" + colorScheme.scrim.hexCode)

            .replace("\$surfaceBright$", "#" + colorScheme.surfaceBright.hexCode)
            .replace("\$surfaceDim$", "#" + colorScheme.surfaceDim.hexCode)
            .replace("\$surfaceContainer$", "#" + colorScheme.surfaceContainer.hexCode)
            .replace("\$surfaceContainerHigh$", "#" + colorScheme.surfaceContainerHigh.hexCode)
            .replace("\$surfaceContainerHighest$", "#" + colorScheme.surfaceContainerHighest.hexCode)
            .replace("\$surfaceContainerLow$", "#" + colorScheme.surfaceContainerLow.hexCode)
            .replace("\$surfaceContainerLowest$", "#" + colorScheme.surfaceContainerLowest.hexCode)
            // ================================================================================================


            // Gloom specific colors ==========================================================================
            .replace("\$statusGreen$", "#" + gloomColorScheme.statusGreen.hexCode)
            .replace("\$statusPurple$", "#" + gloomColorScheme.statusPurple.hexCode)
            .replace("\$statusRed$", "#" + gloomColorScheme.statusRed.hexCode)
            .replace("\$statusGrey$", "#" + gloomColorScheme.statusGrey.hexCode)
            .replace("\$statusYellow$", "#" + gloomColorScheme.statusYellow.hexCode)
            .replace("\$star$", "#" + gloomColorScheme.star.hexCode)
            .replace("\$warning$", "#" + gloomColorScheme.warning.hexCode)
            .replace("\$onWarning$", "#" + gloomColorScheme.onWarning.hexCode)
            .replace("\$warningContainer$", "#" + gloomColorScheme.warningContainer.hexCode)
            .replace("\$onWarningContainer$", "#" + gloomColorScheme.onWarningContainer.hexCode)
            .replace("\$alertNote$", "#" + gloomColorScheme.alertNote.hexCode)
            .replace("\$alertTip$", "#" + gloomColorScheme.alertTip.hexCode)
            .replace("\$alertImportant$", "#" + gloomColorScheme.alertImportant.hexCode)
            .replace("\$alertWarning$", "#" + gloomColorScheme.alertWarning.hexCode)
            .replace("\$alertCaution$", "#" + gloomColorScheme.alertCaution.hexCode)
            // ================================================================================================


            // Code ===========================================================================================
            .replace("\$code$", "#" + codeTheme.code.hexCode)
            .replace("\$keyword$", "#" + codeTheme.keyword.hexCode)
            .replace("\$string$", "#" + codeTheme.string.hexCode)
            .replace("\$literal$", "#" + codeTheme.literal.hexCode)
            .replace("\$comment$", "#" + codeTheme.comment.hexCode)
            .replace("\$metadata$", "#" + codeTheme.metadata.hexCode)
            .replace("\$multilineComment$", "#" + codeTheme.multilineComment.hexCode)
            .replace("\$punctuation$", "#" + codeTheme.punctuation.hexCode)
            .replace("\$mark$", "#" + codeTheme.mark.hexCode)
            // ================================================================================================

            .replace("\$theme$", if (isLight) "light" else "dark") // Support the old way of theming images
    }

}