package com.materiapps.gloom.ui.viewmodels.repo

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.materiapps.gloom.R
import com.materiapps.gloom.domain.repository.GraphQLRepository
import com.materiapps.gloom.fragment.RepoDetails
import com.materiapps.gloom.fragment.RepoOverview
import com.materiapps.gloom.rest.utils.fold
import kotlinx.coroutines.launch

class RepoViewModel(
    private val gql: GraphQLRepository,
    private val nameWithOwner: Pair<String, String>
) : ScreenModel {

    val owner = nameWithOwner.first
    val name = nameWithOwner.second

    enum class Tab(
        @StringRes val nameRes: Int
    ) {
        DETAILS(R.string.repo_tab_details),
        CODE(R.string.repo_tab_code),
        ISSUES(R.string.repo_tab_issues),
        PRs(R.string.repo_tab_prs),
        RELEASES(R.string.repo_tab_releases),
    }

    val badgeCounts = mutableStateMapOf<Int, Int>()

    var hasError by mutableStateOf(false)
    var currentTab by mutableStateOf(Tab.DETAILS)

    fun selectTab(tab: Tab) {
        currentTab = tab
    }

    var repoOverview by mutableStateOf(null as RepoOverview?)
    var repoOverviewLoading by mutableStateOf(false)

    init {
        coroutineScope.launch {
            repoOverviewLoading = true
            gql.getRepoOverview(owner, name).fold(
                onSuccess = {
                    repoOverviewLoading = false
                    repoOverview = it
                    badgeCounts[Tab.ISSUES.ordinal] = it?.issues?.totalCount ?: 0
                    badgeCounts[Tab.PRs.ordinal] = it?.pullRequests?.totalCount ?: 0
                },
                onError = {
                    repoOverviewLoading = false
                    hasError = true
                }
            )
        }
    }

    // Details

    var details by mutableStateOf(null as RepoDetails?)
    var detailsLoading by mutableStateOf(false)

    fun loadDetailsTab() {
        coroutineScope.launch {
            detailsLoading = true
            gql.getRepoDetails(owner, name).fold(
                onSuccess = {
                    details = it
                    detailsLoading = false
                },
                onError = {
                    hasError = true
                    detailsLoading = false
                }
            )
        }
    }

}