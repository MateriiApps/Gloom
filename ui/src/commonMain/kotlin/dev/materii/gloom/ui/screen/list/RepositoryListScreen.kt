package dev.materii.gloom.ui.screen.list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import dev.icerock.moko.resources.StringResource
import dev.materii.gloom.Res
import dev.materii.gloom.api.model.ModelRepo
import dev.materii.gloom.gql.RepoListQuery
import dev.materii.gloom.ui.screen.list.base.BaseListScreen
import dev.materii.gloom.ui.screen.list.viewmodel.RepositoryListViewModel
import dev.materii.gloom.ui.screen.repo.component.RepoItem
import org.koin.core.parameter.parametersOf

class RepositoryListScreen(
    private val username: String,
): BaseListScreen<ModelRepo, RepoListQuery.Data?, RepositoryListViewModel>() {

    override val titleRes: StringResource get() = Res.strings.title_repos

    override val viewModel: RepositoryListViewModel
        @Composable get() = koinScreenModel { parametersOf(username) }

    @Composable
    override fun Item(item: ModelRepo) = RepoItem(repo = item, login = username)

    override val key: ScreenKey
        get() = "${this::class.simpleName}($username)"

}