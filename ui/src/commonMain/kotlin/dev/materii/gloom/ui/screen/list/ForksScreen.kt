package dev.materii.gloom.ui.screen.list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import dev.icerock.moko.resources.StringResource
import dev.materii.gloom.Res
import dev.materii.gloom.api.model.ModelRepo
import dev.materii.gloom.gql.RepoForksQuery
import dev.materii.gloom.ui.screen.list.base.BaseListScreen
import dev.materii.gloom.ui.screen.list.viewmodel.ForksViewModel
import dev.materii.gloom.ui.screen.repo.component.RepoItem
import org.koin.core.parameter.parametersOf

class ForksScreen(
    private val username: String,
    private val repository: String
): BaseListScreen<ModelRepo, RepoForksQuery.Data?, ForksViewModel>() {

    override val titleRes: StringResource get() = Res.strings.title_forks

    override val viewModel: ForksViewModel
        @Composable
        get() = koinScreenModel { parametersOf(username, repository) }

    @Composable
    override fun Item(item: ModelRepo) = RepoItem(repo = item)

    override val key: ScreenKey
        get() = "${this::class.simpleName}($username, $repository)"

}