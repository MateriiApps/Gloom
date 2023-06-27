package com.materiiapps.gloom.ui.widgets.reaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddReaction
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.materiiapps.gloom.gql.fragment.Reaction
import com.materiiapps.gloom.gql.type.ReactionContent
import com.materiiapps.gloom.ui.theme.colors
import com.materiiapps.gloom.utils.Constants

@Composable
fun ReactionRow(
    reactions: List<Reaction>,
    onReactionClick: (ReactionContent) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.Outlined.AddReaction,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .clickable { /* TODO: Open reaction dialog */ }
                .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
                .padding(6.dp)
                .size(20.dp)
        )

        reactions.filter { it.reactors.totalCount > 0 }.forEach { reaction ->
            val (backgroundColor, textColor) =
                if (reaction.viewerHasReacted)
                    MaterialTheme.colors.primaryContainer to MaterialTheme.colors.primary
                else
                    MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp) to MaterialTheme.colors.onSurface

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onReactionClick(reaction.content) }
                    .background(backgroundColor)
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Text(
                    text = Constants.REACTION_EMOJIS[reaction.content]!!,
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