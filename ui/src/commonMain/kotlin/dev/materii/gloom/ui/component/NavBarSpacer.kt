package dev.materii.gloom.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.materii.gloom.ui.util.DimenUtils

@Composable
fun NavBarSpacer() = Spacer(Modifier.height(DimenUtils.navBarPadding))