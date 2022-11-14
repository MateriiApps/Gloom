package com.materiapps.gloom.ui.screens.list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.materiapps.gloom.R
import com.materiapps.gloom.SponsoringQuery
import com.materiapps.gloom.domain.models.ModelUser
import com.materiapps.gloom.ui.screens.list.base.BaseListScreen
import com.materiapps.gloom.ui.viewmodels.list.SponsoringViewModel
import com.materiapps.gloom.ui.widgets.user.UserItem
import org.koin.core.parameter.parametersOf

class SponsoringScreen(
    private val username: String,
    override val titleRes: Int = R.string.noun_sponsoring
) : BaseListScreen<ModelUser, SponsoringQuery.Data?, SponsoringViewModel>() {

    override val viewModel: SponsoringViewModel
        @Composable get() = getScreenModel { parametersOf(username) }

    @Composable
    override fun Item(item: ModelUser) = UserItem(user = item)

    override val key: ScreenKey
        get() = "${javaClass.name}($username)"

}