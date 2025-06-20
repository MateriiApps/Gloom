package dev.materii.gloom.ui.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.gql.fragment.FollowRecommendationFeedItemFragment

@Composable
fun RecommendedFollowUserItem(
    item: FollowRecommendationFeedItemFragment,
    modifier: Modifier = Modifier,
    followData: Pair<Boolean, Int>? = null,
    onFollowClick: (String) -> Unit = {},
    onUnfollowClick: (String) -> Unit = {},
) {
    val user = item.followee
    val userId = user.feedUser?.id ?: user.feedOrg?.id!!

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        FeedActor(
            iconVector = Icons.Outlined.PeopleAlt,
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface.copy(0.7f))) {
                    append(stringResource(Res.strings.recommended))
                }
            },
            badgeIcon = Icons.Filled.AutoAwesome
        )

        FeedUserCard(
            user.feedUser to user.feedOrg,
            followData = followData,
            onFollowClick = { onFollowClick(userId) },
            onUnfollowClick = { onUnfollowClick(userId) }
        )
    }
}