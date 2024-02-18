package com.materiiapps.gloom.ui.widgets.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.domain.manager.AvatarShape
import com.materiiapps.gloom.ui.utils.contentDescription
import com.materiiapps.gloom.ui.utils.getShapeForPref
import dev.icerock.moko.resources.compose.stringResource
import java.text.DecimalFormat
import kotlin.math.roundToInt

/**
 * Ui for picking an avatar shape, should be a row of shapes with a slider that only appears for the rounded square
 *
 * @param currentShape The currently selected avatar shape
 * @param onShapeUpdate Callback for when a shape is selected
 * @param cornerRadius The corner radius for the rounded square shape option
 * @param onCornerRadiusUpdate Callback for when the user changes the radius
 * @param modifier The [Modifier] for this component
 */
@Composable
fun AvatarShapeSetting(
    currentShape: AvatarShape,
    onShapeUpdate: (newShape: AvatarShape) -> Unit,
    cornerRadius: Int,
    onCornerRadiusUpdate: (newRadius: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()
        ) {
            items(AvatarShape.entries) { shapePref ->
                val shape = remember(cornerRadius) { getShapeForPref(shapePref, cornerRadius) }
                val shapeLabel = remember {
                    when (shapePref) {
                        AvatarShape.Circle -> Res.strings.appearance_av_shape_circle
                        AvatarShape.RoundedCorner -> Res.strings.appearance_av_shape_roundrect
                        AvatarShape.Squircle -> Res.strings.appearance_av_shape_squircle
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(60.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer, shape)
                        .border(BorderStroke(2.dp, MaterialTheme.colorScheme.secondary), shape)
                        .clip(shape)
                        .contentDescription(shapeLabel)
                        .selectable(
                            selected = currentShape == shapePref,
                            role = Role.RadioButton,
                            onClick = {
                                onShapeUpdate(shapePref)
                            }
                        )
                ) {
                    if (currentShape == shapePref) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }

        AnimatedVisibility(currentShape == AvatarShape.RoundedCorner) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = stringResource(Res.strings.appearance_av_radius),
                    style = MaterialTheme.typography.labelMedium
                )

                Slider(
                    value = cornerRadius.toFloat(),
                    onValueChange = { onCornerRadiusUpdate(it.roundToInt()) },
                    valueRange = 0f..50f,
                    modifier = Modifier
                        .weight(1f)
                )

                Text(
                    text = DecimalFormat("#%").format(cornerRadius/100f),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}