package com.materiiapps.gloom.ui.screen.explore.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.api.dto.user.User
import com.materiiapps.gloom.domain.manager.TrendingPeriodPreference
import com.materiiapps.gloom.gql.fragment.TrendingRepository
import com.materiiapps.gloom.ui.component.Avatar
import com.materiiapps.gloom.ui.theme.gloomColorScheme
import com.materiiapps.gloom.ui.util.NumberFormatter
import com.materiiapps.gloom.ui.util.parsedColor
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun TrendingRepoItem(
    trendingRepository: TrendingRepository,
    trendingPeriod: TrendingPeriodPreference,
    starToggleEnabled: Boolean,
    onClick: () -> Unit,
    onOwnerClick: () -> Unit,
    onStarClick: () -> Unit,
    onUnstarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        ElevatedCard(
            onClick = onClick
        ) {
            if (trendingRepository.usesCustomOpenGraphImage) {
                Image(
                    painter = rememberImagePainter(trendingRepository.openGraphImageUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(2f)
                        .fillMaxWidth()
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Avatar(
                        url = trendingRepository.owner.avatarUrl,
                        contentDescription = null,
                        type = User.Type.fromTypeName(trendingRepository.owner.__typename),
                        modifier = Modifier
                            .clickable(onClick = onOwnerClick)
                            .size(44.dp)
                    )

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = trendingRepository.owner.login,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.clickable(onClick = onOwnerClick)
                        )

                        Text(
                            text = trendingRepository.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    FilledTonalIconToggleButton(
                        checked = trendingRepository.viewerHasStarred,
                        onCheckedChange = { shouldStar ->
                            if (shouldStar) onStarClick() else onUnstarClick()
                        },
                        colors = IconButtonDefaults.filledTonalIconToggleButtonColors(
                            checkedContentColor = MaterialTheme.gloomColorScheme.star,
                            checkedContainerColor = MaterialTheme.gloomColorScheme.star.copy(alpha = 0.2f)
                        ),
                        enabled = starToggleEnabled
                    ) {
                        Icon(
                            imageVector = if (trendingRepository.viewerHasStarred) Icons.Filled.Star else Icons.Outlined.StarOutline,
                            contentDescription = stringResource(if (trendingRepository.viewerHasStarred) Res.strings.action_unstar else Res.strings.action_star),
                        )
                    }
                }

                trendingRepository.description?.let { description ->
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = LocalContentColor.current.copy(alpha = 0.8f)
                    )
                }

                LabeledIcon(
                    icon = Icons.Filled.Star,
                    iconTint = MaterialTheme.gloomColorScheme.statusYellow,
                    label = stringResource(
                        when (trendingPeriod) {
                            TrendingPeriodPreference.MONTHLY -> Res.strings.label_stars_month
                            TrendingPeriodPreference.WEEKLY -> Res.strings.label_stars_week
                            TrendingPeriodPreference.DAILY -> Res.strings.label_stars_day
                        },
                        NumberFormatter.compact(trendingRepository.starsSince)
                    )
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LabeledIcon(
                        icon = Icons.Outlined.StarBorder,
                        label = NumberFormatter.compact(trendingRepository.stargazerCount)
                    )

                    trendingRepository.primaryLanguage?.let { (color, name) ->
                        LabeledIcon(
                            icon = Icons.Filled.Circle,
                            iconTint = color?.parsedColor ?: Color.Black,
                            label = name
                        )
                    }
                }
            }
        }
    }
}

// TODO: Use everywhere
@Composable
private fun LabeledIcon(
    icon: ImageVector,
    iconTint: Color = LocalContentColor.current,
    label: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = iconTint
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = LocalContentColor.current.copy(alpha = 0.6f)
        )
    }
}