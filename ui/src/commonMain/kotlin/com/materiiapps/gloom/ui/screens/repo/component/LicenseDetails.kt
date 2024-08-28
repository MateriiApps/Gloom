package com.materiiapps.gloom.ui.screens.repo.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.gql.fragment.RepoLicense
import com.materiiapps.gloom.ui.theme.colors
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun LicenseDetails(
    license: RepoLicense,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            var detailsExpanded by remember {
                mutableStateOf(false)
            }

            Text(
                text = license.name,
                style = MaterialTheme.typography.titleMedium
            )

            license.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = LocalContentColor.current.copy(alpha = 0.5f)
                )
            }

            AnimatedVisibility(visible = detailsExpanded) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    if (license.permissions.isNotEmpty()) {
                        ConditionGroup(
                            label = stringResource(Res.strings.label_permissions),
                            conditions = license.permissions.mapNotNull { it?.label },
                            icon = Icons.Outlined.Check,
                            iconColor = MaterialTheme.colors.statusGreen
                        )
                    }

                    if (license.limitations.isNotEmpty()) {
                        ConditionGroup(
                            label = stringResource(Res.strings.label_limitations),
                            conditions = license.limitations.mapNotNull { it?.label },
                            icon = Icons.Outlined.Close,
                            iconColor = MaterialTheme.colors.statusRed
                        )
                    }

                    if (license.conditions.isNotEmpty()) {
                        ConditionGroup(
                            label = stringResource(Res.strings.label_conditions),
                            conditions = license.conditions.mapNotNull { it?.label },
                            icon = Icons.Outlined.Info,
                            iconColor = MaterialTheme.colors.secondary
                        )
                    }
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .clickable(
                        role = Role.Button,
                        onClickLabel = stringResource(if (detailsExpanded) Res.strings.action_hide_license_details else Res.strings.action_show_license_details)
                    ) {
                        detailsExpanded = !detailsExpanded
                    }
                    .padding(5.dp)
            ) {
                Icon(
                    if (detailsExpanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun RowScope.ConditionGroup(
    label: String,
    conditions: List<String>,
    icon: ImageVector,
    iconColor: Color
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.weight(1f)
    ) {
        Text(
            label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier)

        conditions.forEach { condition ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(vertical = 3.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(15.dp)
                )

                Text(
                    text = condition,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}