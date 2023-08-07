package com.materiiapps.gloom.ui.widgets.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.domain.manager.ToastManager
import com.materiiapps.gloom.gql.fragment.Contributions
import com.materiiapps.gloom.gql.type.ContributionLevel
import com.materiiapps.gloom.ui.utils.format
import com.materiiapps.gloom.ui.utils.getPluralString
import dev.icerock.moko.resources.compose.stringResource
import org.koin.androidx.compose.get

@Composable
fun ContributionGraph(
    calendar: Contributions.ContributionCalendar,
    spacing: Dp = 5.dp
) {
    val toastManager: ToastManager = get()
    val lazyListState =
        rememberLazyListState(initialFirstVisibleItemIndex = calendar.weeks.lastIndex)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(Res.strings.noun_contributions, calendar.totalContributions),
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
                                        toastManager.showToast(
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
                    text = stringResource(Res.strings.noun_less),
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
                    text = stringResource(Res.strings.noun_more),
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
        ContributionLevel.NONE -> noneColor
        ContributionLevel.FIRST_QUARTILE -> MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            .compositeOver(noneColor)

        ContributionLevel.SECOND_QUARTILE -> MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            .compositeOver(noneColor)

        ContributionLevel.THIRD_QUARTILE -> MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
            .compositeOver(noneColor)

        ContributionLevel.FOURTH_QUARTILE -> MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
            .compositeOver(noneColor)

        else -> MaterialTheme.colorScheme.error
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