package com.materiiapps.gloom.ui.screen.list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.api.model.ModelUser
import com.materiiapps.gloom.gql.SponsoringQuery
import com.materiiapps.gloom.ui.screen.list.base.BaseListScreen
import com.materiiapps.gloom.ui.screen.list.viewmodel.SponsoringViewModel
import com.materiiapps.gloom.ui.widget.user.UserItem
import dev.icerock.moko.resources.StringResource
import org.koin.core.parameter.parametersOf

class SponsoringScreen(
    private val username: String,
) : BaseListScreen<ModelUser, SponsoringQuery.Data?, SponsoringViewModel>() {

    override val titleRes: StringResource get() = Res.strings.noun_sponsoring

    override val viewModel: SponsoringViewModel
        @Composable get() = getScreenModel { parametersOf(username) }

    @Composable
    override fun Item(item: ModelUser) = UserItem(user = item)

    override val key: ScreenKey
        get() = "${this::class.simpleName}($username)"

}