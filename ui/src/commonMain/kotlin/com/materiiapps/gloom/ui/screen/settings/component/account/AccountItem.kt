package com.materiiapps.gloom.ui.screen.settings.component.account

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.domain.manager.Account
import com.materiiapps.gloom.ui.component.Avatar
import com.materiiapps.gloom.ui.component.BadgedItem
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun AccountItem(
    account: Account,
    isCurrent: Boolean,
    onClick: () -> Unit,
    signOutButton: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
    isEditMode: Boolean = false
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(onClick = onClick, enabled = !isEditMode && !isCurrent)
            .padding(16.dp)
    ) {
        BadgedItem(
            badge = {
                if (account.notificationCount > 0) {
                    Badge {
                        Text(
                            text = if (account.notificationCount > 99) "99+" else account.notificationCount.toString(),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        ) {
            Avatar(
                url = account.avatarUrl,
                contentDescription = stringResource(
                    Res.strings.noun_users_avatar,
                    account.displayName ?: account.username
                ),
                modifier = Modifier.size(45.dp)
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp),
            modifier = Modifier
                .weight(1f)
        ) {
            val isEnterprise = account.type == Account.Type.ENTERPRISE
            val hasDisplayName = !account.displayName.isNullOrBlank()
            val usernameSubtitle: @Composable (includeParenthesis: Boolean) -> Unit =
                { includeParenthesis ->
                    Text(
                        text = if (includeParenthesis) "(${account.username})" else account.username,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.alpha(0.5f)
                    )
                }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = if (hasDisplayName) account.displayName!! else account.username,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    fontSize = 17.sp
                )
                if (hasDisplayName && isEnterprise) {
                    usernameSubtitle(true)
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (hasDisplayName && !isEnterprise) {
                    usernameSubtitle(false)
                }
                account.baseUrl?.let { baseUrl ->
                    val enterpriseCD = stringResource(Res.strings.cd_enterprise_domain, baseUrl)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .semantics(mergeDescendants = true) {
                                contentDescription = enterpriseCD
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Public,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = baseUrl,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .animateContentSize()
        ) {
            Spacer(Modifier.height(48.dp))

            if (isCurrent) {
                val opacity by animateFloatAsState(
                    targetValue = if (isEditMode) 0.4f else 1f,
                    label = "Current account edit mode"
                )

                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .alpha(opacity)
                )
            }

            signOutButton()
        }
    }
}