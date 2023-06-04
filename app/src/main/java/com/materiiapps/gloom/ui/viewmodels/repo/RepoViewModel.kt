package com.materiiapps.gloom.ui.viewmodels.repo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.materiiapps.gloom.domain.repository.GraphQLRepository
import com.materiiapps.gloom.gql.fragment.RepoOverview
import com.materiiapps.gloom.rest.utils.fold
import com.materiiapps.gloom.ui.screens.repo.tab.CodeTab
import com.materiiapps.gloom.ui.screens.repo.tab.DetailsTab
import com.materiiapps.gloom.ui.screens.repo.tab.IssuesTab
import com.materiiapps.gloom.ui.screens.repo.tab.PullRequestTab
import com.materiiapps.gloom.ui.screens.repo.tab.ReleasesTab
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
        PullRequestTab(),
        ReleasesTab()
    )

    val badgeCounts = mutableStateMapOf<Int, Int>()

    var hasError by mutableStateOf(false)

    var repoOverview by mutableStateOf(null as RepoOverview?)
    var repoOverviewLoading by mutableStateOf(false)

    init {
        coroutineScope.launch {
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