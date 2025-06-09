package dev.materii.gloom.ui.screen.repo

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import dev.icerock.moko.resources.StringResource
import dev.materii.gloom.Res
import dev.materii.gloom.gql.RepoCommitsQuery
import dev.materii.gloom.gql.fragment.CommitDetails
import dev.materii.gloom.ui.screen.list.base.BaseListScreen
import dev.materii.gloom.ui.screen.repo.component.CommitItem
import dev.materii.gloom.ui.screen.repo.viewmodel.RepoCommitsViewModel
import org.koin.core.parameter.parametersOf

class CommitsScreen(
    private val id: String,
    private val branch: String
) : BaseListScreen<CommitDetails, RepoCommitsQuery.Data?, RepoCommitsViewModel>() {

    override val titleRes: StringResource get() = Res.strings.title_commits

    override val key: ScreenKey
        get() = "${this::class.simpleName}($id, $branch)"

    override val viewModel: RepoCommitsViewModel
        @Composable get() = koinScreenModel { parametersOf(id, branch) }

    @Composable
    override fun Item(item: CommitDetails) {
        CommitItem(item)
    }
}