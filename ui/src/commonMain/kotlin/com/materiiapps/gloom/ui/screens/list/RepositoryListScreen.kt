package com.materiiapps.gloom.ui.screens.list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.api.models.ModelRepo
import com.materiiapps.gloom.gql.RepoListQuery
import com.materiiapps.gloom.ui.screens.list.base.BaseListScreen
import com.materiiapps.gloom.ui.viewmodels.list.RepositoryListViewModel
import com.materiiapps.gloom.ui.widgets.repo.RepoItem
import dev.icerock.moko.resources.StringResource
import org.koin.core.parameter.parametersOf

class RepositoryListScreen(
    private val username: String,
) : BaseListScreen<ModelRepo, RepoListQuery.Data?, RepositoryListViewModel>() {

    override val titleRes: StringResource get() = Res.strings.noun_repos

    override val viewModel: RepositoryListViewModel
        @Composable get() = getScreenModel { parametersOf(username) }

    @Composable
    override fun Item(item: ModelRepo) = RepoItem(repo = item, login = username)

    override val key: ScreenKey
        get() = "${this::class.simpleName}($username)"

}