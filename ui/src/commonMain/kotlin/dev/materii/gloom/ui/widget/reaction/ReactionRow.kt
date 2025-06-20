package dev.materii.gloom.ui.widget.reaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddReaction
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.materii.gloom.api.REACTION_EMOJIS
import dev.materii.gloom.gql.fragment.Reaction
import dev.materii.gloom.gql.type.ReactionContent
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ReactionRow(
    reactions: ImmutableList<Reaction>,
    onReactionClick: (reaction: ReactionContent, unreact: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    forRelease: Boolean = false
) {
    val _reactions = remember { mutableStateListOf<Reaction>() }
    var opened by remember { mutableStateOf(false) }

    LaunchedEffect(reactions) {
        _reactions.clear()
        _reactions.addAll(reactions.filter { it.reactors.totalCount > 0 })
    }

    fun react(reaction: ReactionContent) {
        val fullReaction = _reactions.firstOrNull { it.content == reaction }
        opened = false

        when {
            _reactions.isEmpty() || fullReaction == null -> {
                onReactionClick(reaction, false)
                _reactions.add(
                    Reaction(reaction, true, Reaction.Reactors(1))
                )
            }

            fullReaction.viewerHasReacted                -> {
                val i = _reactions.indexOf(fullReaction)
                val newCount = fullReaction.reactors.totalCount - 1
                onReactionClick(reaction, true)

                if (newCount == 0)
                    _reactions.remove(fullReaction)
                else
                    _reactions[i] = fullReaction.copy(
                        viewerHasReacted = false,
                        reactors = fullReaction.reactors.copy(
                            totalCount = newCount
                        )
                    )
            }

            !fullReaction.viewerHasReacted               -> {
                val i = _reactions.indexOf(fullReaction)
                onReactionClick(reaction, false)

                _reactions[i] = fullReaction.copy(
                    viewerHasReacted = true,
                    reactors = fullReaction.reactors.copy(
                        totalCount = fullReaction.reactors.totalCount + 1
                    )
                )
            }
        }
    }

    if (opened) {
        ReactionSheet(
            forRelease = forRelease,
            onReactionClick = ::react,
            onClose = { opened = false }
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .horizontalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.Outlined.AddReaction,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .clickable { opened = true }
                .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
                .padding(6.dp)
                .size(20.dp)
        )

        _reactions.forEach { reaction ->
            val (backgroundColor, textColor) =
                if (reaction.viewerHasReacted)
                    MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp) to MaterialTheme.colorScheme.onSurface

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { react(reaction.content) }
                    .background(backgroundColor)
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Text(
                    text = REACTION_EMOJIS[reaction.content]!!,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = reaction.reactors.totalCount.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    color = textColor
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))
    }
}