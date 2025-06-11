package dev.materii.gloom.ui.screen.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.gql.fragment.Contributions
import dev.materii.gloom.gql.type.ContributionLevel
import dev.materii.gloom.ui.widget.alert.LocalAlertController
import dev.materii.gloom.util.format
import dev.materii.gloom.util.getPluralString

@Composable
fun ContributionGraph(
    calendar: Contributions.ContributionCalendar,
    spacing: Dp = 5.dp
) {
    val alertController = LocalAlertController.current
    val lazyListState =
        rememberLazyListState(initialFirstVisibleItemIndex = calendar.weeks.lastIndex)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(Res.strings.section_title_contributions, calendar.totalContributions),
            style = MaterialTheme.typography.labelLarge,
            fontSize = 15.sp
        )

        ElevatedCard {
            Spacer(modifier = Modifier.height(24.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(spacing),
                state = lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(calendar.weeks.size) {
                    calendar.weeks[it].let { week ->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(spacing, Alignment.Top)
                        ) {
                            week.contributionDays.forEach { day ->
                                DayTile(
                                    level = day.contributionLevel,
                                    onClick = {
                                        alertController.showText(
                                            getPluralString(
                                                Res.plurals.contributions_toast,
                                                day.contributionCount,
                                                day.date.format("MMM d, YYYY"),
                                                day.contributionCount
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(Res.strings.label_less),
                    style = MaterialTheme.typography.labelMedium,
                    color = LocalContentColor.current.copy(0.5f)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    DayTile(ContributionLevel.NONE)
                    DayTile(ContributionLevel.FIRST_QUARTILE)
                    DayTile(ContributionLevel.SECOND_QUARTILE)
                    DayTile(ContributionLevel.THIRD_QUARTILE)
                    DayTile(ContributionLevel.FOURTH_QUARTILE)
                }

                Text(
                    text = stringResource(Res.strings.label_more),
                    style = MaterialTheme.typography.labelMedium,
                    color = LocalContentColor.current.copy(0.5f)
                )
            }
        }
    }
}

@Composable
private fun DayTile(
    level: ContributionLevel,
    onClick: (() -> Unit)? = null
) {
    val noneColor = MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp)
    val color = when (level) {
        ContributionLevel.NONE            -> noneColor
        ContributionLevel.FIRST_QUARTILE  -> MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            .compositeOver(noneColor)

        ContributionLevel.SECOND_QUARTILE -> MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            .compositeOver(noneColor)

        ContributionLevel.THIRD_QUARTILE  -> MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
            .compositeOver(noneColor)

        ContributionLevel.FOURTH_QUARTILE -> MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
            .compositeOver(noneColor)

        else                              -> MaterialTheme.colorScheme.error
    }

    Box(
        modifier = Modifier
            .run {
                if (onClick != null)
                    clickable(onClick = onClick)
                else
                    Modifier
            }
            .size(12.dp)
            .clip(RoundedCornerShape(12))
            .background(color)
    )
}