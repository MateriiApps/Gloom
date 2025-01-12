package dev.materii.gloom.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.materii.gloom.ui.util.thenIf

@Composable
fun LoadingButton(
    loading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    loadingIndicatorSize: Dp = 24.dp,
    loadingIndicatorStrokeWidth: Dp = 3.dp,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = colors,
        elevation = elevation,
        enabled = !loading,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(ButtonDefaults.IconSpacing),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.thenIf(loading) { alpha(0f) }
            ) {
                content()
            }
            if (loading) {
                CircularProgressIndicator(
                    strokeWidth = loadingIndicatorStrokeWidth,
                    modifier = Modifier.size(loadingIndicatorSize)
                )
            }
        }
    }
}
