package com.materiiapps.gloom.ui.widgets.accounts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.materiiapps.gloom.Res
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SignOutButton(
    visible: Boolean = true,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(spring(stiffness = Spring.StiffnessHigh))
    ) {
        IconButton(
            onClick = onClick,
            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.error)
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = stringResource(Res.strings.action_sign_out)
            )
        }
    }
}