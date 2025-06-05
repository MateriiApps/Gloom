package dev.materii.gloom.ui.screen.repo.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
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
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.benasher44.uuid.uuid4
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.ui.component.LargeSegmentedButton
import dev.materii.gloom.ui.component.LargeSegmentedButtonRow
import dev.materii.gloom.ui.component.ThinDivider
import dev.materii.gloom.ui.icon.Custom
import dev.materii.gloom.ui.icon.custom.Balance
import dev.materii.gloom.ui.icon.custom.Fork
import dev.materii.gloom.ui.screen.list.ForksScreen
import dev.materii.gloom.ui.screen.repo.LicenseScreen
import dev.materii.gloom.ui.screen.repo.RepoScreen
import dev.materii.gloom.ui.screen.repo.component.ContributorsRow
import dev.materii.gloom.ui.screen.repo.component.LanguageMakeup
import dev.materii.gloom.ui.screen.repo.viewmodel.RepoDetailsViewModel
import dev.materii.gloom.ui.util.navigate
import dev.materii.gloom.ui.widget.markdown.Markdown
import dev.materii.gloom.util.NumberFormatter
import dev.materii.gloom.util.pluralStringResource
import org.koin.core.parameter.parametersOf

class DetailsTab(
    private val owner: String,
    private val name: String
) : Tab {

    override val key = "$owner/$name-${uuid4()}"
    override val options: TabOptions
        @Composable get() = TabOptions(1u, stringResource(Res.strings.repo_tab_details))

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Content() {
        val viewModel: RepoDetailsViewModel = koinScreenModel { parametersOf(owner to name) }
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
                                    NumberFormatter.compact(repoDetails.stargazerCount)
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
                                    NumberFormatter.compact(repoDetails.forkCount)
                                ),
                                onClick = { nav.navigate(ForksScreen(owner, name)) }
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
                            html = details.readme!!.contentHTML.toString(),
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