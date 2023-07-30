package com.materiiapps.gloom.ui.screens.list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.materiiapps.gloom.R
import com.materiiapps.gloom.gql.SponsoringQuery
import com.materiiapps.gloom.api.models.ModelUser
import com.materiiapps.gloom.ui.screens.list.base.BaseListScreen
import com.materiiapps.gloom.ui.viewmodels.list.SponsoringViewModel
import com.materiiapps.gloom.ui.widgets.user.UserItem
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