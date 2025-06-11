package dev.materii.gloom.ui.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.gql.fragment.RecommendedRepositoryFeedItemFragment

@Composable
fun RecommendedRepoItem(
    item: RecommendedRepositoryFeedItemFragment,
    starData: Pair<Boolean, Int>? = null,
    onStarPressed: (String) -> Unit = {},
    onUnstarPressed: (String) -> Unit = {},
) {
    val repo = item.repository.feedRepository

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        FeedActor(
            iconVector = Icons.Outlined.Book,
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface.copy(0.7f))) {
                    append(stringResource(Res.strings.recommended))
                }
            },
            badgeIcon = Icons.Filled.AutoAwesome
        )

        FeedRepoCard(
            repo = repo,
            starData = starData,
            onStarPressed = {
                onStarPressed(repo.id)
            },
            onUnstarPressed = {
                onUnstarPressed(repo.id)
            }
        )
    }
}