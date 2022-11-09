package com.materiapps.gloom.ui.widgets.repo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materiapps.gloom.R
import com.materiapps.gloom.domain.models.ModelRepo

@Composable
fun RepoCard(repo: ModelRepo) {
    Column(
        Modifier
            .clickable { }
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = repo.name ?: stringResource(R.string.noun_empty_repo),
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 16.sp
            )
        )
        repo.description?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(0.8f),
                )
            )
        }
        if (repo.fork == true) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 5.dp)
            ) {
                Icon(
                    painterResource(R.drawable.ic_fork_24),
                    contentDescription = stringResource(R.string.noun_forked_repo),
                    modifier = Modifier.size(15.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                )
                Text(
                    text = stringResource(R.string.forked_from, repo.parent!!.fullName!!),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                    )
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.labelLarge) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Star,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color(0xFFF1E05A)
                    )
                    Text(text = repo.stars.toString())
                }


                if (repo.language != null) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.Circle,
                            contentDescription = repo.language.name,
                            modifier = Modifier.size(15.dp),
                            tint = repo.language.color
                                ?: MaterialTheme.colorScheme.surfaceVariant
                        )
                        Text(text = repo.language.name)
                    }
                }
            }
        }
    }
}