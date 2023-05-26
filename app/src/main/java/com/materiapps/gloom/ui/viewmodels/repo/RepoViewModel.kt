package com.materiapps.gloom.ui.viewmodels.repo

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
<<<<<<< HEAD
<<<<<<< HEAD
import cafe.adriel.voyager.navigator.tab.Tab
=======
>>>>>>> 430f7f6 (Setup and details tab)
=======
import cafe.adriel.voyager.navigator.tab.Tab
>>>>>>> 40743ab ([WIP] Code tab)
import com.materiapps.gloom.R
import com.materiapps.gloom.domain.repository.GraphQLRepository
import com.materiapps.gloom.fragment.RepoDetails
import com.materiapps.gloom.fragment.RepoOverview
import com.materiapps.gloom.rest.utils.fold
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 40743ab ([WIP] Code tab)
import com.materiapps.gloom.ui.screens.repo.tab.CodeTab
import com.materiapps.gloom.ui.screens.repo.tab.DetailsTab
import com.materiapps.gloom.ui.screens.repo.tab.IssuesTab
import com.materiapps.gloom.ui.screens.repo.tab.PullRequestTab
import com.materiapps.gloom.ui.screens.repo.tab.ReleasesTab
<<<<<<< HEAD
=======
>>>>>>> 430f7f6 (Setup and details tab)
=======
>>>>>>> 40743ab ([WIP] Code tab)
import kotlinx.coroutines.launch

class RepoViewModel(
    private val gql: GraphQLRepository,
    private val nameWithOwner: Pair<String, String>
) : ScreenModel {

    val owner = nameWithOwner.first
    val name = nameWithOwner.second

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 40743ab ([WIP] Code tab)
    val tabs = listOf(
        DetailsTab(owner, name),
        CodeTab(owner, name),
        IssuesTab(),
        PullRequestTab(),
        ReleasesTab()
    )
<<<<<<< HEAD
=======
    enum class Tab(
        @StringRes val nameRes: Int
    ) {
        DETAILS(R.string.repo_tab_details),
        CODE(R.string.repo_tab_code),
        ISSUES(R.string.repo_tab_issues),
        PRs(R.string.repo_tab_prs),
        RELEASES(R.string.repo_tab_releases),
    }
>>>>>>> 430f7f6 (Setup and details tab)
=======
>>>>>>> 40743ab ([WIP] Code tab)

    val badgeCounts = mutableStateMapOf<Int, Int>()

    var hasError by mutableStateOf(false)
<<<<<<< HEAD
<<<<<<< HEAD
=======
    var currentTab by mutableStateOf(Tab.DETAILS)

    fun selectTab(tab: Tab) {
        currentTab = tab
    }
>>>>>>> 430f7f6 (Setup and details tab)
=======
>>>>>>> 40743ab ([WIP] Code tab)

    var repoOverview by mutableStateOf(null as RepoOverview?)
    var repoOverviewLoading by mutableStateOf(false)

    init {
        coroutineScope.launch {
            repoOverviewLoading = true
            gql.getRepoOverview(owner, name).fold(
                onSuccess = {
                    repoOverviewLoading = false
                    repoOverview = it
<<<<<<< HEAD
<<<<<<< HEAD
                    badgeCounts[2] = it?.issues?.totalCount ?: 0
                    badgeCounts[3] = it?.pullRequests?.totalCount ?: 0
=======
                    badgeCounts[Tab.ISSUES.ordinal] = it?.issues?.totalCount ?: 0
                    badgeCounts[Tab.PRs.ordinal] = it?.pullRequests?.totalCount ?: 0
>>>>>>> 430f7f6 (Setup and details tab)
=======
                    badgeCounts[2] = it?.issues?.totalCount ?: 0
                    badgeCounts[3] = it?.pullRequests?.totalCount ?: 0
>>>>>>> 40743ab ([WIP] Code tab)
                },
                onError = {
                    repoOverviewLoading = false
                    hasError = true
                }
            )
        }
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
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

>>>>>>> 430f7f6 (Setup and details tab)
=======
>>>>>>> 40743ab ([WIP] Code tab)
}