package com.materiiapps.gloom.ui.screen.list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.koin.koinScreenModel
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.api.model.ModelUser
import com.materiiapps.gloom.gql.JoinedOrgsQuery
import com.materiiapps.gloom.ui.screen.list.base.BaseListScreen
import com.materiiapps.gloom.ui.screen.list.viewmodel.OrgListViewModel
import com.materiiapps.gloom.ui.widget.user.UserItem
import dev.icerock.moko.resources.StringResource
import org.koin.core.parameter.parametersOf

class OrgsListScreen(
    private val username: String,
) : BaseListScreen<ModelUser, JoinedOrgsQuery.Data?, OrgListViewModel>() {

    override val titleRes: StringResource get() = Res.strings.title_orgs

    override val viewModel: OrgListViewModel
        @Composable get() = koinScreenModel { parametersOf(username) }

    @Composable
    override fun Item(item: ModelUser) = UserItem(user = item)

    override val key: ScreenKey
        get() = "${this::class.simpleName}($username)"

}