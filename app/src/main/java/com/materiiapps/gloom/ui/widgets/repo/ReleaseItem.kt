package com.materiiapps.gloom.ui.widgets.repo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materiiapps.gloom.R
import com.materiiapps.gloom.gql.fragment.ReleaseItem
import com.materiiapps.gloom.ui.components.Label
import com.materiiapps.gloom.ui.theme.BadgeGreen
import com.materiiapps.gloom.utils.TimeUtils.getTimeSince
import kotlinx.datetime.toInstant

@Composable
fun ReleaseItem(
    release: ReleaseItem
) {
    val ctx = LocalContext.current
    val createdAt = remember { (release.createdAt as String).toInstant() }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = release.name ?: release.tagName,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = ctx.getTimeSince(createdAt),
                color = LocalContentColor.current.copy(alpha = 0.5f),
                style = MaterialTheme.typography.labelLarge
            )

            if (release.isLatest) {
                Label(
                    text = stringResource(R.string.label_latest),
                    textColor = BadgeGreen
                )
            }

            if (release.isPrerelease) {
                Label(
                    text = stringResource(R.string.label_prerelease),
                    textColor = Color(0xFFFF9800)
                )
            }
        }
    }
}