package dev.materii.gloom.util

import java.io.File

enum class OS(vararg keys: String) {
    MacOS("mac", "darwin"),
    Windows("win"),
    Linux("nux"),
    Other("generic");

    val keys = keys.toList()

    companion object {

        private var detectedOS: OS? = null

        val Current: OS?
            get() {
                if (detectedOS == null) detectedOS = getTypeForKey(
                    System.getProperty(
                        "os.name",
                        Other.keys[0]
                    ).lowercase()
                )
                return detectedOS
            }

        private fun getTypeForKey(osKey: String): OS {
            for (osType in entries) {
                if (
                    osType.keys.any { osKey.contains(it) }
                ) return osType
            }
            return Other
        }

    }

}

val OSBaseDir = when (OS.Current) {
    OS.Windows -> File(System.getenv("APPDATA"))
    OS.MacOS -> File(System.getProperty("user.home"), "Library/Application Support")
    OS.Linux -> File(System.getenv("XDG_CONFIG_DIR") ?: "${System.getProperty("user.home")}/.config")
    else -> File(System.getProperty("user.home"), ".config")
}

actual val supportsMonet = false
actual val isDebug = false
actual val GloomPath = File(OSBaseDir, "Gloom").also { it.mkdir() }
actual val Features = emptyList<Feature>()
