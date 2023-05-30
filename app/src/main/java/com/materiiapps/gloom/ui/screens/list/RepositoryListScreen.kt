package com.materiiapps.gloom.ui.screens.list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.materiiapps.gloom.R
import com.materiiapps.gloom.gql.RepoListQuery
import com.materiiapps.gloom.domain.models.ModelRepo
import com.materiiapps.gloom.ui.screens.list.base.BaseListScreen
import com.materiiapps.gloom.ui.viewmodels.list.RepositoryListViewModel
import com.materiiapps.gloom.ui.widgets.repo.RepoItem
import org.koin.core.parameter.parametersOf

class RepositoryListScreen(
    private val username: String,
    override val titleRes: Int = R.string.noun_repos
) : BaseListScreen<ModelRepo, RepoListQuery.Data?, RepositoryListViewModel>() {

    override val viewModel: RepositoryListViewModel
        @Composable get() = getScreenModel { parametersOf(username) }

    @Composable
    override fun Item(item: ModelRepo) = RepoItem(repo = item)

    override val key: ScreenKey
        get() = "${javaClass.name}($username)"

}