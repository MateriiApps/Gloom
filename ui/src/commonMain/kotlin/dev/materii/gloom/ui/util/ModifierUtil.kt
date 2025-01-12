package dev.materii.gloom.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource

inline fun Modifier.thenIf(predicate: Boolean, block: Modifier.() -> Modifier) =
    if (predicate) then(Modifier.block()) else this

@Composable
fun Modifier.contentDescription(descRes: StringResource, mergeDescendants: Boolean = false) =
    contentDescription(stringResource(descRes), mergeDescendants)

@Composable
fun Modifier.contentDescription(
    descRes: StringResource,
    vararg args: Any,
    mergeDescendants: Boolean = false
) =
    contentDescription(stringResource(descRes, *args), mergeDescendants)

fun Modifier.contentDescription(desc: String, mergeDescendants: Boolean = false) =
    semantics(mergeDescendants) {
        contentDescription = desc
    }