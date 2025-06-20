package dev.materii.gloom.ui.screen.settings.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.domain.manager.enums.AvatarShape
import dev.materii.gloom.ui.util.contentDescription
import dev.materii.gloom.ui.util.getShapeForPref
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvatarShapeSetting(
    text: @Composable () -> Unit,
    currentShape: AvatarShape,
    onShapeUpdate: (newShape: AvatarShape) -> Unit,
    cornerRadius: Int,
    onCornerRadiusUpdate: (newRadius: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val _currentShape = getShapeForPref(currentShape, cornerRadius)
    var isChoosing by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        SettingsItem(
            text = text,
            onClick = { isChoosing = !isChoosing }
        ) {
            SelectableShape(
                shape = _currentShape,
                selected = false,
                onSelect = { isChoosing = !isChoosing },
                modifier = Modifier
                    .size(40.dp)
                    .contentDescription(getShapeLabel(currentShape))
            )

            IconButton(onClick = { isChoosing = !isChoosing }) {
                Icon(
                    imageVector = if (!isChoosing) Icons.Outlined.KeyboardArrowDown else Icons.Outlined.KeyboardArrowUp,
                    contentDescription = stringResource(
                        if (isChoosing) Res.strings.action_expand_av_shape else Res.strings.action_collapse_av_shape
                    )
                )
            }
        }

        AnimatedVisibility(
            visible = isChoosing,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .clip(RoundedCornerShape(10.dp, 10.dp, 24.dp, 24.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    .padding(vertical = 16.dp)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup()
                ) {
                    items(AvatarShape.entries) { shapePref ->
                        val shape = remember(cornerRadius) { getShapeForPref(shapePref, cornerRadius) }

                        SelectableShape(
                            shape = shape,
                            selected = currentShape == shapePref,
                            onSelect = {
                                onShapeUpdate(shapePref)
                            },
                            modifier = Modifier
                                .size(55.dp)
                                .contentDescription(stringResource(getShapeLabel(shapePref)))
                        )
                    }
                }

                AnimatedVisibility(
                    visible = currentShape == AvatarShape.RoundedCorner,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(0.dp),
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        val sliderInteractionSource = remember { MutableInteractionSource() }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(Res.strings.appearance_av_radius),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.weight(1f)
                            )

                            Text(
                                text = DecimalFormat("#%").format(cornerRadius / 100f),
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }

                        Slider(
                            value = cornerRadius.toFloat(),
                            onValueChange = { onCornerRadiusUpdate(it.roundToInt()) },
                            valueRange = 0f..50f,
                            interactionSource = sliderInteractionSource,
                            thumb = {
                                Label(
                                    label = {
                                        PlainTooltip {
                                            Text(
                                                text = DecimalFormat("#%").format(cornerRadius / 100f)
                                            )
                                        }
                                    },
                                    interactionSource = sliderInteractionSource
                                ) {
                                    SliderDefaults.Thumb(
                                        interactionSource = sliderInteractionSource,
                                        thumbSize = DpSize(width = 4.dp, height = 32.dp)
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectableShape(
    shape: Shape,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondaryContainer, shape)
            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.secondary), shape)
            .clip(shape)
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = {
                    onSelect()
                }
            )
    ) {
        if (selected) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

private fun getShapeLabel(avatarShape: AvatarShape): StringResource {
    return when (avatarShape) {
        AvatarShape.Circle -> Res.strings.appearance_av_shape_circle
        AvatarShape.RoundedCorner -> Res.strings.appearance_av_shape_roundrect
        AvatarShape.Squircle -> Res.strings.appearance_av_shape_squircle
    }
}