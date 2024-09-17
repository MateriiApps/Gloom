package com.materiiapps.gloom.ui.screen.repo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.api.model.ModelRepo
import com.materiiapps.gloom.ui.component.Avatar
import com.materiiapps.gloom.ui.component.LabeledIcon
import com.materiiapps.gloom.ui.icon.Custom
import com.materiiapps.gloom.ui.icon.custom.Fork
import com.materiiapps.gloom.ui.screen.repo.RepoScreen
import com.materiiapps.gloom.ui.theme.gloomColorScheme
import com.materiiapps.gloom.ui.util.NumberFormatter
import com.materiiapps.gloom.ui.util.navigate
import com.materiiapps.gloom.ui.util.parsedColor
import com.materiiapps.gloom.ui.util.thenIf
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun RepoItem(
    repo: ModelRepo,
    login: String? = null,
    card: Boolean = false
) {
    val nav = LocalNavigator.currentOrThrow
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .thenIf(card) {
                this
                    .shadow(1.dp, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp))
            }
            .clickable {
                if (!repo.name.isNullOrBlank() && (!repo.owner?.username.isNullOrBlank() || login != null))
                    nav.navigate(RepoScreen(login ?: repo.owner?.username!!, repo.name!!))
            }
            .padding(16.dp)
            .fillMaxWidth()
            .thenIf(card) { width(260.dp) }
    ) {
        if (repo.owner != null) Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(
                url = repo.owner!!.avatar,
                type = repo.owner!!.type,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = repo.owner!!.username ?: "ghost",
                style = MaterialTheme.typography.labelMedium
            )
        }

        Spacer(Modifier)

        Text(
            text = repo.name ?: stringResource(Res.strings.placeholder_empty_repo),
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 16.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        val desc = if (repo.description == null && card) "" else repo.description
        desc?.let { description ->
            Text(
                text = description,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(0.8f),
                maxLines = if (card) 1 else 5,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (repo.fork == true && !card) {
            LabeledIcon(
                icon = Icons.Custom.Fork,
                label = stringResource(Res.strings.label_forked_from, repo.parent!!.fullName!!),
                iconTint = LocalContentColor.current.copy(0.6f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            LabeledIcon(
                icon = Icons.Outlined.Star,
                label = NumberFormatter.compact(repo.stars ?: 0),
                iconTint = MaterialTheme.gloomColorScheme.star
            )

            repo.language?.let { (name, color) ->
                LabeledIcon(
                    icon = Icons.Filled.Circle,
                    label = name,
                    iconTint = color?.parsedColor ?: Color.Black
                )
            }
        }
    }
}