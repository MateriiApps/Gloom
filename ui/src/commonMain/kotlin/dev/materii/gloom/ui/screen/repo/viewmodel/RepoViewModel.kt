package dev.materii.gloom.ui.screen.repo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.materii.gloom.api.repository.GraphQLRepository
import dev.materii.gloom.api.util.fold
import dev.materii.gloom.gql.fragment.RepoOverview
import dev.materii.gloom.ui.screen.repo.tab.CodeTab
import dev.materii.gloom.ui.screen.repo.tab.DetailsTab
import dev.materii.gloom.ui.screen.repo.tab.IssuesTab
import dev.materii.gloom.ui.screen.repo.tab.PullRequestTab
import dev.materii.gloom.ui.screen.repo.tab.ReleasesTab
import kotlinx.coroutines.launch

class RepoViewModel(
    private val gql: GraphQLRepository,
    private val nameWithOwner: Pair<String, String>
) : ScreenModel {

    val owner = nameWithOwner.first
    val name = nameWithOwner.second

    val tabs = listOf(
        DetailsTab(owner, name),
        CodeTab(owner, name),
        IssuesTab(owner, name),
        PullRequestTab(owner, name),
        ReleasesTab(owner, name)
    )

    val badgeCounts = mutableStateMapOf<Int, Int>()

    var hasError by mutableStateOf(false)

    var repoOverview by mutableStateOf(null as RepoOverview?)
    var repoOverviewLoading by mutableStateOf(false)

    init {
        screenModelScope.launch {
            repoOverviewLoading = true
            gql.getRepoOverview(owner, name).fold(
                onSuccess = {
                    repoOverviewLoading = false
                    repoOverview = it
                    badgeCounts[2] = it?.issues?.totalCount ?: 0
                    badgeCounts[3] = it?.pullRequests?.totalCount ?: 0
                },
                onError = {
                    repoOverviewLoading = false
                    hasError = true
                }
            )
        }
    }

}