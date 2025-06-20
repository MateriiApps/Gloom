package dev.materii.gloom.ui.screen.list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import dev.icerock.moko.resources.StringResource
import dev.materii.gloom.Res
import dev.materii.gloom.api.model.ModelUser
import dev.materii.gloom.gql.SponsoringQuery
import dev.materii.gloom.ui.screen.list.base.BaseListScreen
import dev.materii.gloom.ui.screen.list.viewmodel.SponsoringViewModel
import dev.materii.gloom.ui.widget.user.UserItem
import org.koin.core.parameter.parametersOf

class SponsoringScreen(
    private val username: String,
): BaseListScreen<ModelUser, SponsoringQuery.Data?, SponsoringViewModel>() {

    override val key: ScreenKey
        get() = "${this::class.simpleName}($username)"

    override val titleRes: StringResource get() = Res.strings.title_sponsoring

    override val viewModel: SponsoringViewModel
        @Composable get() = koinScreenModel { parametersOf(username) }

    @Composable
    override fun Item(item: ModelUser) = UserItem(user = item)

}