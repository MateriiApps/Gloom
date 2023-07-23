package com.materiiapps.gloom.ui.widgets.release

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materiiapps.gloom.R

@Composable
fun ReleaseInfo(
    tagName: String,
    commit: String?
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            modifier = Modifier
                .clip(CircleShape)
                .clickable { }
                .padding(vertical = 8.dp)
                .weight(1f)
        ) {
            Icon(
                imageVector = Icons.Outlined.LocalOffer,
                contentDescription = stringResource(R.string.noun_tag),
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = tagName,
                style = MaterialTheme.typography.labelLarge,
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (commit?.isNotBlank() == true) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { }
                    .padding(vertical = 8.dp)
                    .weight(1f)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_commit_24),
                    contentDescription = stringResource(R.string.noun_commit),
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = commit,
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}