package com.materiiapps.gloom.ui.screen.repo.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.benasher44.uuid.uuid4
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.ui.component.LargeSegmentedButton
import com.materiiapps.gloom.ui.component.LargeSegmentedButtonRow
import com.materiiapps.gloom.ui.component.ThinDivider
import com.materiiapps.gloom.ui.icon.custom.Balance
import com.materiiapps.gloom.ui.icon.Custom
import com.materiiapps.gloom.ui.icon.custom.Fork
import com.materiiapps.gloom.ui.screen.repo.LicenseScreen
import com.materiiapps.gloom.ui.screen.repo.RepoScreen
import com.materiiapps.gloom.ui.util.navigate
import com.materiiapps.gloom.ui.util.pluralStringResource
import com.materiiapps.gloom.ui.screen.repo.viewmodel.RepoDetailsViewModel
import com.materiiapps.gloom.ui.widget.Markdown
import com.materiiapps.gloom.ui.screen.repo.component.ContributorsRow
import com.materiiapps.gloom.ui.screen.repo.component.LanguageMakeup
import dev.icerock.moko.resources.compose.stringResource
import org.koin.core.parameter.parametersOf

class DetailsTab(
    private val owner: String,
    private val name: String
) : Tab {

    override val key = "$owner/$name-${uuid4()}"
    override val options: TabOptions
        @Composable get() = TabOptions(1u, stringResource(Res.strings.repo_tab_details))

    @Composable
    override fun Content() = Screen()

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun Screen(
        viewModel: RepoDetailsViewModel = getScreenModel { parametersOf(owner to name) }
    ) {
        val nav = LocalNavigator.currentOrThrow
        val repoDetails = viewModel.details

        PullToRefreshBox(
            isRefreshing = viewModel.detailsLoading,
            onRefresh = { viewModel.loadDetails() },
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                repoDetails?.let { details ->
                    val hasDescription = !details.description.isNullOrBlank()
                    val isFork = details.parent != null
                    if (hasDescription || isFork) {
                        val paddingBetween = if (hasDescription && isFork) 8.dp else 16.dp
                        if (hasDescription) {
                            Text(
                                text = details.description!!,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(
                                    top = 16.dp,
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = paddingBetween
                                )
                            )
                        }

                        details.parent?.let { (nameWithOwner) ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier
                                    .clickable {
                                        val (owner, name) = nameWithOwner.split("/")
                                        nav.navigate(RepoScreen(owner, name))
                                    }
                                    .fillMaxWidth()
                                    .padding(
                                        bottom = 16.dp,
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = paddingBetween
                                    )
                            ) {
                                CompositionLocalProvider(
                                    LocalContentColor provides LocalContentColor.current.copy(alpha = 0.5f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Custom.Fork,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = stringResource(
                                            Res.strings.label_forked_from,
                                            nameWithOwner
                                        ),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }

                        ThinDivider()
                    }

                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        LargeSegmentedButtonRow {
                            LargeSegmentedButton(
                                icon = if (repoDetails.viewerHasStarred) Icons.Filled.Star else Icons.Outlined.StarBorder,
                                text = pluralStringResource(
                                    res = Res.plurals.stars,
                                    count = repoDetails.stargazerCount,
                                    repoDetails.stargazerCount
                                ),
                                onClick = viewModel::toggleStar,
                                enabled = !viewModel.isStarLoading
                            )

                            repoDetails.licenseInfo?.let {
                                LargeSegmentedButton(
                                    icon = Icons.Custom.Balance,
                                    text = it.nickname ?: it.key.uppercase(),
                                    onClick = { nav.navigate(LicenseScreen(owner, name)) }
                                )
                            }

                            LargeSegmentedButton(
                                icon = Icons.Custom.Fork,
                                text = pluralStringResource(
                                    res = Res.plurals.forks,
                                    count = repoDetails.forkCount,
                                    repoDetails.forkCount
                                ),
                                onClick = { /* TODO */ }
                            )
                        }
                    }

                    ThinDivider()

                    if (!(details.readme?.contentHTML).isNullOrBlank()) {
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
                        if (it.nodes?.isNotEmpty() == true) ThinDivider()

                        ContributorsRow(contributors = it)
                    }

                    repoDetails.languages?.languages?.let {
                        if (it.edges?.isNotEmpty() == true) ThinDivider()
                        LanguageMakeup(it)
                    }
                }
            }
        }
    }

}