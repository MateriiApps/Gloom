package com.materiapps.gloom.ui.screens.repo.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.materiapps.gloom.R
import com.materiapps.gloom.ui.components.RefreshIndicator
import com.materiapps.gloom.ui.viewmodels.repo.RepoViewModel
import com.materiapps.gloom.ui.widgets.Markdown
import com.materiapps.gloom.ui.widgets.repo.ContributorsRow
import com.materiapps.gloom.ui.widgets.repo.LanguageMakeup
import com.materiapps.gloom.ui.widgets.repo.StatItem

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun DetailsTab(
    viewModel: RepoViewModel
) {
    val refreshState = rememberPullRefreshState(
        refreshing = viewModel.detailsLoading,
        onRefresh = { viewModel.loadDetailsTab() })
    val repoDetails = viewModel.details
    if (repoDetails == null && !viewModel.detailsLoading) viewModel.loadDetailsTab()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(refreshState)
            .clipToBounds()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            repoDetails?.let { details ->
                if (!details.description.isNullOrBlank()) {
                    Text(
                        text = details.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                    Divider(
                        color = MaterialTheme.colorScheme.onSurface.copy(0.1f),
                        thickness = 0.5.dp,
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.clip(RoundedCornerShape(16.dp))
                    ) {
                        StatItem(
                            icon = if (repoDetails.viewerHasStarred) Icons.Filled.Star else Icons.Outlined.StarBorder,
                            text = pluralStringResource(
                                id = R.plurals.stars,
                                count = repoDetails.stargazerCount,
                                repoDetails.stargazerCount
                            )
                        )
                        repoDetails.licenseInfo?.let {
                            StatItem(
                                icon = painterResource(R.drawable.ic_balance_24),
                                text = it.nickname ?: it.key.uppercase()
                            )
                        }
                        StatItem(
                            icon = painterResource(R.drawable.ic_fork_24),
                            text = pluralStringResource(
                                id = R.plurals.forks,
                                count = repoDetails.forkCount,
                                repoDetails.forkCount
                            )
                        )
                    }
                }

                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(0.1f),
                    thickness = 0.5.dp,
                )

                if (!(details.readme?.contentHTML as String?).isNullOrBlank()) {
                    Text(
                        buildAnnotatedString {
                            append("README")
                            withStyle(
                                SpanStyle(MaterialTheme.colorScheme.onBackground.copy(0.5f))
                            ) {
                                append(".md")
                            }
                        },
                        style = MaterialTheme.typography.labelLarge,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(16.dp)
                    )
                    Markdown(
                        text = details.readme!!.contentHTML.toString(),
                        Modifier.padding(horizontal = 16.dp)
                    )
                }

                repoDetails.contributors.let {
                    if (it.nodes?.isNotEmpty() == true) Divider(
                        color = MaterialTheme.colorScheme.onSurface.copy(0.1f),
                        thickness = 0.5.dp,
                    )

                    ContributorsRow(contributors = it)
                }

                repoDetails.languages?.languages?.let {
                    if (it.edges?.isNotEmpty() == true) Divider(
                        color = MaterialTheme.colorScheme.onSurface.copy(0.1f),
                        thickness = 0.5.dp,
                    )
                    LanguageMakeup(it)
                }
            }
        }
        RefreshIndicator(state = refreshState, isRefreshing = viewModel.detailsLoading)
    }
}