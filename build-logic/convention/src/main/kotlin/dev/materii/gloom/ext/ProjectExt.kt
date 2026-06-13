package dev.materii.gloom.ext

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

val VersionCatalog.androidSdk
    get() = findVersion("android-sdk").get().displayName.toInt()