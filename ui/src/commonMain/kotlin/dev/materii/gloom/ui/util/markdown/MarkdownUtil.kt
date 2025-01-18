package dev.materii.gloom.ui.util.markdown

import androidx.compose.material3.ColorScheme
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
    // TODO: Add all m3 colors
    fun injectAppTheme(
        markdownTemplate: String,
        isLight: Boolean,
        colorScheme: ColorScheme
    ): String {
        return markdownTemplate // Insert theme colors
            .replace("\$primary$", "#" + colorScheme.primary.hexCode)
            .replace("\$onSurface$", "#" + colorScheme.onSurface.hexCode)
            .replace("\$onSurfaceVariant$", "#" + colorScheme.onSurfaceVariant.hexCode)
            .replace("\$surfaceContainer$", "#" + colorScheme.surfaceContainer.hexCode)
            .replace("\$scrim$", "#" + colorScheme.scrim.hexCode)
            .replace("\$outline$", "#" + colorScheme.outline.hexCode)
            .replace("\$theme$", if (isLight) "light" else "dark") // Support the old way of theming images
    }

}