package dev.materii.gloom.core.model.language

/**
 * A programming language recognized by GitHub
 *
 * @param name The name of the programming language
 * @param color The color assigned to this programming language
 */
data class Language(
    val name: String,
    val color: String?
)