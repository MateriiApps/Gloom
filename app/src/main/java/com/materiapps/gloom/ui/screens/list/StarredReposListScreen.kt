package com.materiapps.gloom.ui.screens.list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.materiapps.gloom.R
import com.materiapps.gloom.StarredReposQuery
import com.materiapps.gloom.domain.models.ModelRepo
import com.materiapps.gloom.ui.screens.list.base.BaseListScreen
import com.materiapps.gloom.ui.viewmodels.list.StarredReposListViewModel
import com.materiapps.gloom.ui.widgets.repo.RepoItem
import org.koin.core.parameter.parametersOf

class StarredReposListScreen(
    private val username: String,
    override val titleRes: Int = R.string.noun_starred
) : BaseListScreen<ModelRepo, StarredReposQuery.Data?, StarredReposListViewModel>() {

    override val viewModel: StarredReposListViewModel
        @Composable get() = getScreenModel { parametersOf(username) }

    @Composable
    override fun Item(item: ModelRepo) = RepoItem(repo = item)

    override val key: ScreenKey
        get() = "${javaClass.name}($username)"

}